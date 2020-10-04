/**
 * NotNullValidator is a class that can be used for validating whether
 * the InputData is null or not.
 * NotNullValidator implements the interface Validatable.
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 * @param <InputData> - generic parameter
 */
public class NotNullValidator<InputData> implements Validatable<InputData>
{
    public int priority;
    InputData dataToValidate;
    private final String validatorName = this.getClass().getName();
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

    /**
     * Validate if the input data is a null or not
     * @param data - data to be evaluate
     * @return boolean - true | false depending of the validity of the data as a swedish social sec. number
     */
    @Override
    public boolean validate(InputData data)
    {
        if(data == null )
            return setValidationNotifier(data, "The data is null!", false);
        return setValidationNotifier(data, "Data is not null", true);
    }



    /**
     * Set the priority of the validator.
     * The priority p is used for setting the order by which the validators will be applied to the data
     * @param p
     */
    @Override
    public void setPriority(int p) { this.priority = p; }

    /**
     * Return the priority parameter
     * @return Integer
     */
    @Override
    public int getPriority() { return this.priority; }

    /**
     * @param dataToValidate - data intended for evaluation
     */
    public void setDataToValidate(InputData dataToValidate) { this.dataToValidate = dataToValidate; }

    /**
     * Return the data used/to be used for validation
     * @return String
     */
    @Override
    public InputData getDataToValidate() { return dataToValidate; }




    /**
     * Set important message about the state of the validation.
     * For example if the validation was successful or the function crashed
     * @param message - a message for the user caller
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the message about the state of the validation if applicable
     * @return message
     */
    @Override
    public String getMessage() {
        return message;
    }


    /**
     * Implement string describing the correct format of the data required for passing the validation
     * @return - a string with the correct data format description
     */
    @Override
    public String correctInputFormat() {
        return "Data should never be Empty ! Retry with a non-empty input";
    }

    /**
     * Set the the data, message and the validity of the data
     * @param dataToValidate - the data to be evaluated
     * @param msg - a message for describing the state of the validation
     * @param validity - boolean for describing whether the validation was successful or not
     * @return true if not-null, false otherwise
     */
    @Override
    public boolean setValidationNotifier(InputData dataToValidate, String msg, boolean validity) {
        this.dataToValidate = dataToValidate;
        this.message = msg;
        this.isValid = validity;
        return validity;
    }


    /**
     * Return a unique string name describing for the NotNullValidator.class
     * Useful for logger and debugging
     * @return a string with the validator class name
     */
    @Override
    public String getValidatorName() {
        return this.validatorName;
    }


    /**
     * Data representation of the class SocialSecurityNumberValidator
     * Used in console printing and error logging.
     * @return string with the [priority, dataToValidate, dataToValidate, isValid, message]
     */
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
