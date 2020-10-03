package com.company;

import java.util.*;

public class SocialSecurityNumberValidator<InputData> implements Validatable<String>
{
    public int priority;
    InputData dataToValidate;
    // Add static int to track #objects created and the spec. object failed
    private final String validatorName = SocialSecurityNumberValidator.class.getName();
    private boolean isValid;
    private String message;
    SocialSecurityNumberValidator(){this.priority = Integer.MAX_VALUE; }
    SocialSecurityNumberValidator(int p) {this.priority = p;}
    SocialSecurityNumberValidator(InputData data) {
        this.dataToValidate = data;
        this.priority = Integer.MAX_VALUE;
    }
    SocialSecurityNumberValidator(InputData data, int p)
    {
        this.dataToValidate = data;
        this.priority = p;
    }

    @Override
    public boolean validate(String dataInput)
    {
        String data = sanityStep(dataInput);
        int length = data.length();
        if (data.length() == 0)
            return setValidationNotifier(dataInput, "Input is empty", false);
        if(!data.matches("\\d{" + length + "}"))
            return setValidationNotifier(dataInput, "Social number should contain only numbers", false);
        if (data.length() != 10)
            return setValidationNotifier(dataInput, "Social number must have exactly 10 or 12 numbers", false);

        // Check that all input are number : -> Social number must be only numbers
        if(isSocialSecurityNumber(data))
            return setValidationNotifier(dataInput, "Valid social security number", true);

        return setValidationNotifier(dataInput, "Data is not a social security number", false);
    }

    public String sanityStep(String data)
    {
        String s = data.replaceAll("[-]", "");
        if(s.length() == 12)
            s = s.substring(2);
        return s;
    }

    private boolean isSocialSecurityNumber(String personNumber)
    {
        String personNumberT = personNumber.substring(0,personNumber.length()-1);
        int personNumberCheck = Integer.parseInt(String.valueOf(personNumber.charAt(personNumber.length()-1)));
        int [] valueHolder = new int [personNumberT.length()];  // hold operations

        for(int i = 0; i < personNumberT.length() ; i++)
        {
            char character = personNumberT.charAt(i);
            int temp = Integer.parseInt(String.valueOf(character));
            int mask = getMask(i);
            temp = temp * mask;
            int splitSum = splitInteger(temp);
            valueHolder[i] = splitSum;
        }
        int checkSum = getSum(valueHolder);
        checkSum = 10 - modulo(checkSum, 10) ;
        checkSum = modulo(checkSum, 10);

        return checkSum == personNumberCheck;
    }

    public int getMask(int index)
    {
        final int TEMP = -1;
        double t = Math.pow(TEMP, index) + 1;
        t = (t / 2) + 1;
        return (int) t;
    }

    public int splitInteger(int number)
    {
        String tempNumber = String.valueOf(number);
        int length = tempNumber.length();
        int [] array = new int [length];
        for(int i = 0; i < length; i++)
        {
            char character = tempNumber.charAt(i);
            int temp = Integer.parseInt(String.valueOf(character));
            array[i] = temp;
        }

        return getSum(array);
    }

    public int getSum(int[] array) { return Arrays.stream(array).sum(); }
    public int modulo(int numerator, int denominator ) { return numerator % denominator ; }
    public String getDataToValidate() { return (String) dataToValidate; }
    public void setDataToValidate(String dataToValidate) { this.dataToValidate = (InputData) dataToValidate; }

    @Override
    public int getPriority() { return priority; }
    @Override
    public void setPriority(int priority) { this.priority = priority; }

    @Override
    public boolean setValidationNotifier(String dataToValidate, String msg, boolean validity) {
        this.dataToValidate = (InputData) dataToValidate;
        this.message = msg;
        this.isValid = validity;
        return validity;
    }

    @Override
    public String getValidatorName() {
        return this.validatorName;
    }

    @Override
    public String toString() {
        return "SocialSecurityNumberValidator{" +
                "priority=" + priority +
                ", dataToValidate=" + dataToValidate +
                ", validatorName='" + validatorName + '\'' +
                ", isValid=" + isValid +
                ", message='" + message + '\'' +
                '}';
    }

}

