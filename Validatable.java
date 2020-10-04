
/**
 * The Interface Validate  describes all the essential properties of a validator.
 * The validator should implements all those properties if required.
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 * @param <T>
 */
public interface Validatable<T>
{
    public int priority = 0 ;
    Object dataToValidate = null;
    String validatorName = "";
    boolean isValid = false;
    String message = "";

    /**
     * Validate the input data based on the rules specified
     * @param data
     * @return boolean - true | false depending on the validity of the data.
     */
    public boolean validate(T data);

    /**
     * Set the priority of the validator.
     * The priority p is used for setting the order by which the validators will be applied to the data
     * @param p
     */
    public void setPriority(int p);

    /**
     * Return the priority parameter
     * @return Integer
     */
    public int getPriority();

    /**
     * Set the data to be validated
     * @param dataToValidate - data intended for evaluation
     */
    public void setDataToValidate(T dataToValidate);

    /**
     * Return the data used for validation
     * @return T
     */
    public T getDataToValidate();

    /**
     * Set important message about the state of the validation.
     * For example if the validation was successful or the function crashed
     * @param message - a message for the user caller
     */
    public void setMessage(String message);

    /**
     * Return the message about the state of the validation if applicable
     * @return message
     */
    public String getMessage();


    /**
     * Implement string describing the correct format of the data required for passing the validation
     * @return - a string with the correct data format description
     */
    public String correctInputFormat();


    /**
     * Set the the data, message and the validity of the data
     * @param dataToValidate - the data to be evaluated
     * @param msg - a message for describing the state of the validation
     * @param validity - boolean for describing whether the validation was successful or not
     * @return a boolean describing whether the validation was successful or not
     */
    public boolean setValidationNotifier(T dataToValidate, String msg, boolean validity);

    /**
     * Return a unique string name describing for the validator.
     * Usefull for logger and debugging
     * @return a string with the validator class name
     */
    public String getValidatorName();
}

/*public interface Validatable<T> extends Comparable<T>
public Validatable<T> validate(); */
