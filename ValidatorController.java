package com.company;

import java.util.ArrayList;

public abstract class ValidatorController<T,V>
{
    static enum ValidationOrder
    {
        BYINSERTIONORDER,
        BYPRIORITYORDER
    };
    private int numberOfSuccessfullValidations = 0;
    V data;
    ArrayList<T> validators = null;
    ValidationOrder sortOrder = ValidationOrder.BYINSERTIONORDER;
    String noticationMessage;

    // protecded -> used ONLY by subclasss
    protected ValidatorController() { this.sortOrder = ValidationOrder.BYINSERTIONORDER;  }
    protected ValidatorController(ArrayList<T> validityChecks, V data){
        this.validators = validityChecks;
        this.data = data;
    }
    protected ValidatorController(ArrayList<T> validityChecks, V data, ValidationOrder order){
        this.validators = validityChecks;
        this.data = data;
        this.sortOrder = order;
    }

    public void setSortOrder(ValidationOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ValidationOrder getSortOrder() {
        return sortOrder;
    }

    public void setValidators(ArrayList<T> validators) {
        this.validators = validators;
    }

    public ArrayList<T> getValidators() {
        return validators;
    }

    public void addValidators(T validator) {
        if(this.validators == null)
            this.validators = new ArrayList<>();
        this.validators.add(validator);
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public String getNoticationMessage() {
        return noticationMessage;
    }

    public void setNoticationMessage(String noticationMessage) {
        this.noticationMessage = noticationMessage;
    }

    public int getNumberOfSuccessfullValidations() {
        return numberOfSuccessfullValidations;
    }

    public void setNumberOfSuccessfullValidations(int numberOfSuccessfullValidations) {
        this.numberOfSuccessfullValidations = numberOfSuccessfullValidations;
    }

    public void incrementNumberOfSuccessfullValidations() {
        this.numberOfSuccessfullValidations++;
    }

    public abstract void applyValidators();

}
