package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CarRegistrationNumberValidator<InputData> implements Validatable<String>
{
    public int priority;
    InputData dataToValidate;
    private final String validatorName = NotNullValidator.class.getName();
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


    public void addStopWords(String word) {
        if(this.stopWords == null)
            this.stopWords = new ArrayList<>();
        this.stopWords.add(word);
    }

    @Override
    public boolean validate(String data)
    {
        int length = data.length();
        data = data.toLowerCase();
        String pattern1 = "[a-z&&[^äåöiqv]]{3}[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String pattern2 = "[a-z&&[^äåöiqv]]{3}([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String pattenToSplit = "[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])|([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String wordCombination = data.replaceAll(pattenToSplit, "").trim();
        String [] forbidenLetters = new String[]{"ä", "å", "ö", "i", "q", "v"};

        if (data.length() == 0)
            return setValidationNotifier(data, "Input is empty", false);
        if(data.charAt(length-1) == 'o')
            return setValidationNotifier(data, "Plate number cannot contains o as the last letter", false);
        for (String t: forbidenLetters) {
            if(data.contains(t))
                return setValidationNotifier(data, "Plate number cannot contains [ä,å,ö,i,q, v]", false);
        }
        if ( !(data.matches(pattern1) || data.matches(pattern2)) )
            return setValidationNotifier(data, "Data format is wrong, should be (3Letters3Numbers)|(3Letters2Numbers1letters)", false);

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
                return setValidationNotifier(data, "Plate number contains forbidden word : " + wordCombination, true);
        }

        return setValidationNotifier(data, "Problem with data", false);
    }

    public boolean setValidationNotifier(String dataToValidate, String msg, boolean validity) {
        this.dataToValidate = (InputData) dataToValidate;
        this.message = msg;
        this.isValid = validity;
        return validity;
    }
    public int getPriority() { return this.priority; }
    public void setPriority(int p) { this.priority = p; }
    public String getDataToValidate() { return (String) dataToValidate; }
    public void setDataToValidate(String dataToValidate) { this.dataToValidate = (InputData) dataToValidate; }

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

    public String getValidatorName() {
        return this.validatorName;
    }
}

