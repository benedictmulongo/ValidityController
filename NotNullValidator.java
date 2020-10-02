package com.company;
import java.io.*;
import java.util.*;

public class NotNullValidator<InputData> implements Validatable<InputData>
{
    public int priority;
    InputData dataToValidate;
    String validatorName = NotNullValidator.class.getName();
    NotNullValidator(){this.priority = Integer.MAX_VALUE; }
    NotNullValidator(int p) {this.priority = p;}

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
    public int getPriority()
    {
        return this.priority;
    }

    public void setPriority(int p)
    {
        this.priority = p;
    }

    @Override
    public String toString() {
        return "NotNullValidator{" +
                "priority=" + priority +
                ", dataToValidate=" + dataToValidate +
                ", validatorName='" + validatorName + '\'' +
                '}';
    }
}
