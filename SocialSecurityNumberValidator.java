package com.company;
import java.util.*;

public class SocialSecurityNumberValidator<InputData> implements Validatable<String>
{
    public int priority;
    InputData dataToValidate;
    private final String validatorName = SocialSecurityNumberValidator.class.getName();
    private boolean isValid;
    private String message;
    SocialSecurityNumberValidator(){this.priority = Integer.MAX_VALUE; }
    SocialSecurityNumberValidator(int p) {this.priority = p;}
    SocialSecurityNumberValidator(InputData data) {this.dataToValidate = data;}
    SocialSecurityNumberValidator(InputData data, int p)
    {
        this.dataToValidate = data;
        this.priority = p;
    }

    @Override
    public void validate(String data)
    {
        if (data.length() == 0)
            throw new NumberFormatException("Empty");
        if (data.length() < 10)
            throw new NumberFormatException("Social number cannot be less than 10");
        if (data.length() == 11 || data.length() > 12)
            throw new NumberFormatException("Social number cannot have 11 numbers or more than 12 numbers");

        // Check that all input are number : -> Social number must be only numbers
        if( data.length() == 10)
        {
            Boolean isValid = isSocialSecurityNumber(data);
        }
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
    public int getPriority() { return this.priority; }
    public void setPriority(int p) { this.priority = p; }
    public InputData getDataToValidate() { return dataToValidate; }
    public void setDataToValidate(InputData dataToValidate) { this.dataToValidate = dataToValidate; }

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

