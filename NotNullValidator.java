package com.company;

public class NotNullValidator<InputData> implements Validatable<InputData>
{
    public int priority;
    InputData dataToValidate;
    private final String validatorName = NotNullValidator.class.getName();
    private boolean isValid;
    private String message;
    NotNullValidator(){this.priority = Integer.MAX_VALUE; }
    NotNullValidator(int p) {this.priority = p;}
    NotNullValidator(InputData data) {
        this.dataToValidate = data;
        this.priority = Integer.MAX_VALUE;
    }
    NotNullValidator(InputData data, int p)
    {
        this.dataToValidate = data;
        this.priority = p;
    }

    public boolean validate(InputData data)
    {
        if(data == null )
            return setValidationNotifier(data, "The data is null!", false);
        return setValidationNotifier(data, "Data not null", true);
    }

    @Override
    public boolean setValidationNotifier(InputData dataToValidate, String msg, boolean validity) {
        this.dataToValidate = dataToValidate;
        this.message = msg;
        this.isValid = validity;
        return validity;
    }

    @Override
    public String getValidatorName() {
        return this.validatorName;
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
