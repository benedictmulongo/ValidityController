package com.company;

public interface Validatable<T>
{
    public int priority = 0 ;
    public void validate(T data);
    public int getPriority();
    public void setPriority(int p);
}

