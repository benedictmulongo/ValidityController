package com.company;
import java.io.*;
import java.util.*;

public class SocialSecurityNumberValidator<InputData> implements Validatable<String>
{
    public int priority;
    InputData dataToValidate;
    String validatorName = SocialSecurityNumberValidator.class.getName();
    SocialSecurityNumberValidator(){this.priority = Integer.MAX_VALUE; }
    SocialSecurityNumberValidator(int p) {this.priority = p;}

    @Override
    public void validate(String data)
    {
        if( data.length() == 10)
        {
            for(int i = 0; i<data.length() - 1; i++)
            {
                char character = data.charAt(i);
                int temp = Integer.parseInt(String.valueOf(character));

            }
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
        return "SocialSecurityNumberValidator{" +
                "priority=" + priority +
                ", dataToValidate=" + dataToValidate +
                ", validatorName='" + validatorName + '\'' +
                '}';
    }
}

