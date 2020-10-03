package com.company;

//public interface Validatable<T> extends Comparable<T>
public interface Validatable<T>
{
    public int priority = 0 ;
    Object dataToValidate = null;
    String validatorName = "";
    boolean isValid = false;
    String message = "";

    public boolean validate(T data);
    //public Validatable<T> validate();
    public int getPriority();
    public void setPriority(int p);
    public T getDataToValidate();
    public void setDataToValidate(T dataToValidate);
    public boolean setValidationNotifier(T dataToValidate, String msg, boolean validity);
    public String getValidatorName();
}

