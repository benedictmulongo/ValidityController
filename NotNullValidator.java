package com.company;
import java.io.*;
import java.util.*;

public class NotNullValidator<InputData> implements Validatable<InputData>
{
    public int priority;
    InputData dataToValidate;
    private final String validatorName = NotNullValidator.class.getName();
    private boolean isValid;
    private String message;
    NotNullValidator(){this.priority = Integer.MAX_VALUE; }
    NotNullValidator(int p) {this.priority = p;}
    NotNullValidator(InputData data) {this.dataToValidate = data;}
    NotNullValidator(InputData data, int p)
    {
        this.dataToValidate = data;
        this.priority = p;
    }

    public void validate(InputData data)
    {
        this.dataToValidate = data;
        if(data == null )
        {
            throw new NullPointerException("The data is null! ");
        }
        else
        {
            System.out.println( data + " - Data is valid !");
        }
    }
    public int getPriority() { return this.priority; }
    public void setPriority(int p) { this.priority = p; }
    public InputData getDataToValidate() { return dataToValidate; }
    public void setDataToValidate(InputData dataToValidate) { this.dataToValidate = dataToValidate; }

    @Override
    public String toString() {
        return "NotNullValidator{" +
                "priority=" + priority +
                ", dataToValidate=" + dataToValidate +
                ", validatorName='" + validatorName + '\'' +
                ", isValid=" + isValid +
                ", message='" + message + '\'' +
                '}';
    }
}
