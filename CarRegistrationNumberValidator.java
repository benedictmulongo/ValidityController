package com.company;

import java.util.ArrayList;

/**
 * SocialSecurityNumberValidator is a class that can be used for checking whether
 * an user input/InputData is a valid Car Registration Number / Car number plate.
 * CarRegistrationNumberValidator implements the interface Validatable.
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 * @param <InputData> - generic parameter
 */
public class CarRegistrationNumberValidator<InputData> implements Validatable<String>
{
    public int priority;
    InputData dataToValidate;
    private final String validatorName = this.getClass().getName();
    //private final String validatorName = CarRegistrationNumberValidator.class.getName();
    private boolean isValid;
    private String message;
    private ArrayList<String> stopWords = null;
    CarRegistrationNumberValidator(){this.priority = Integer.MAX_VALUE; }

    CarRegistrationNumberValidator(int p) {this.priority = p;}
    CarRegistrationNumberValidator(InputData data) {this.dataToValidate = data;}
    CarRegistrationNumberValidator( ArrayList<String> forbidenWords)
    {
        this.stopWords = forbidenWords;
        this.priority = Integer.MAX_VALUE;
    }
    CarRegistrationNumberValidator( int p, ArrayList<String> forbidenWords){
        this.priority = p;
        this.stopWords = forbidenWords;
    }
    CarRegistrationNumberValidator(InputData data, int p, ArrayList<String> forbidenWords)
    {
        this.dataToValidate = data;
        this.priority = p;
        this.stopWords = forbidenWords;
    }


    /**
     * Add forbidden words that should not be present in the car number plate
     * Ex. [SEG, SEX, SJU, SOS, SPY, SUG, SUP, SUR,SEG, SEX, SJU, SOS, SPY, SUG, SUP, SUR, ... ]
     * @param word a forbidden word
     */
    public void addStopWords(String word) {
        if(this.stopWords == null)
            this.stopWords = new ArrayList<>();
        this.stopWords.add(word);
    }

    /**
     * Validate if the input data represents a valid swedish car number plate based on the rules specified below.
     * Let L = letter, D = digit
     * The valid formats for a swedish car number plate are :
     * case 1 : LLL-DDD, LLLDDD, LLL DDD,
     * case 2 : LLL-DDL, LLLDDL, LLL DDL,
     * Additional rules :
     *  A car number plate should not contain :
     *     - Forbidden words ([SEG, SEX, SJU, SOS, SPY, SUG, SUP, SUR,SEG, SEX, SJU, SOS, SPY, ...])
     *     - the 'o' should not be used as the last letter for case 2
     *     - any letters as [ö, ä, å, i, q, v]
     * The rules are implemented with regular expressions !!
     * No consideration is paid towards personalized/customized car number plate as those follow unspecified rules
     * The rules are been based on https://sv.wikipedia.org/wiki/Registreringsskyltar_i_Sverige (consulted 2020-10-02)
     * @param data - data to be evaluate
     * @return boolean - true | false depending of the validity of the data as a swedish Car Number plate
     */
    @Override
    public boolean validate(String data)
    {

        int length = data.length();
        data = data.trim().toLowerCase();
        String pattern1 = "[a-z&&[^äåöiqv]]{3}[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String pattern2 = "[a-z&&[^äåöiqv]]{3}([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String pattenToSplit = "[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])|([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String wordCombination = data.replaceAll(pattenToSplit, "").trim();
        String [] forbidenLetters = new String[]{"ä", "å", "ö", "i", "q", "v"};

        if (data.length() == 0)
            return setValidationNotifier(data, "Input is empty", false);
        if(data.charAt(length-1) == 'o')
            return setValidationNotifier(data, "Plate number cannot contain o as the last letter", false);
        for (String t: forbidenLetters) {
            if(data.contains(t))
                return setValidationNotifier(data, "Plate number cannot contain those letters [ä,å,ö,i,q, v]", false);
        }
        if ( !(data.matches(pattern1) || data.matches(pattern2)) )
            return setValidationNotifier(data, "Data format is wrong. It should be (3Digits 3Numbers)|(3Letters 2Digits 1Letter)", false);

        if(this.stopWords == null )
        {
            if(data.matches(pattern1) || data.matches(pattern2) )
                return setValidationNotifier(data, "Valid plate number", true);
        }
        else
        {
            if((data.matches(pattern1) || data.matches(pattern2) ) && (!stopWords.contains(wordCombination)))
                return setValidationNotifier(data, "Valid plate number", true);
            else
                return setValidationNotifier(data, "Plate number contains the forbidden word : " + wordCombination, true);
        }

        return setValidationNotifier(data, "Not a valid car plate number", false);
    }

    /**
     * Set the the data, message and the validity of the data
     * @param dataToValidate - the data to be evaluated
     * @param msg - a message for describing the state of the validation
     * @param validity - boolean for describing whether the validation was successful or not
     * @return true if not-null, false otherwise
     */
    public boolean setValidationNotifier(String dataToValidate, String msg, boolean validity) {
        this.dataToValidate = (InputData) dataToValidate;
        this.message = msg;
        this.isValid = validity;
        return validity;
    }

    /**
     * Return the priority parameter
     * @return Integer
     */
    public int getPriority() { return this.priority; }

    /**
     * Set the priority of the validator.
     * The priority p is used for setting the order by which the validators will be applied to the data
     * @param p
     */
    public void setPriority(int p) { this.priority = p; }

    /**
     * Return the data used/to be used for validation
     * @return String
     */
    public String getDataToValidate() { return (String) dataToValidate; }

    /**
     * Set the data to be validated
     * @param dataToValidate - data intended for evaluation
     */
    public void setDataToValidate(String dataToValidate) { this.dataToValidate = (InputData) dataToValidate; }

    /**
     * Implement string describing the correct format of the data required for passing the validation
     * @return - a string with the correct data format description
     */
    @Override
    public String correctInputFormat() {
        return "The car plate number is composed of : \n" +
                "3 digits followed by 3 letters or \n" +
                " 3 digits followed by 2 letter and 1 final letter. \n"  +
                "Digits and letters may or may not be separate by - ";
    }

    /**
     * Return a unique string name describing for the SocialSecurityNumberValidator.class
     * Useful for logger and debugging
     * @return a string with the validator class name
     */
    public String getValidatorName() {
        return this.validatorName;
    }

    /**
     * Set important message about the state of the validation.
     * For example if the validation was successful or the function crashed
     * @param message - a message for the user caller
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the message about the state of the validation if applicable
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Data representation of the class CarRegistrationNumberValidator
     * Used in console printing and error logging.
     * @return string with the [priority, dataToValidate, dataToValidate, isValid, message]
     */
    @Override
    public String toString() {
        return "CarRegistrationNumberValidator{" +
                "priority=" + priority +
                ", dataToValidate=" + dataToValidate +
                ", validatorName='" + validatorName + '\'' +
                ", isValid=" + isValid +
                ", message='" + message + '\'' +
                '}';
    }
}

