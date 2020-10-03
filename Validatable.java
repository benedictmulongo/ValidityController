package com.company;

public interface Validatable<T>
{
    public int priority = 0 ;
    Object dataToValidate = null;
    String validatorName = "";
    boolean isValid = false;
    String message = "";

    public void validate(T data);
    public int getPriority();
    public void setPriority(int p);

}

