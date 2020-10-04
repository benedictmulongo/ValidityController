import java.util.ArrayList;

/**
 * This abstract class ValidatorController< T,V > specifies all the
 * essential data fields and methods common to all ValidityChecker in order
 * to make it possible to have different kind of validity checkers.
 * Different validity checkers can be compared etc.
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 * @param <T>
 * @param <V>
 */
public abstract class ValidatorController<T,V>
{
    protected boolean passed = false;
    protected double successRate = 0;
    private int numberOfSuccessfullValidations = 0;
    V data;
    ArrayList<T> validators = null;
    ValidationOrder sortOrder = ValidationOrder.BYINSERTIONORDER;
    String noticationMessage;

    // protected -> used ONLY by sub-classs
    protected ValidatorController() {}
    protected ValidatorController(T validityCheck, V data)
    {
        this.validators = new ArrayList<>();
        this.validators.add(validityCheck);
        this.data = data;
    }
    protected ValidatorController(ArrayList<T> validityChecks, V data){
        this.validators = validityChecks;
        this.data = data;
    }

    protected ValidatorController(ArrayList<T> validityChecks, V data, T firstPriorityCheck)
    {
        validityChecks.add(0,firstPriorityCheck);
        this.validators = validityChecks;
        this.data = data;
    }

    protected ValidatorController(ArrayList<T> validityChecks, V data, ValidationOrder order){
        this.validators = validityChecks;
        this.data = data;
        this.sortOrder = order;
    }

    protected ValidatorController(ArrayList<T> validityChecks, V data, ValidationOrder order, T firstPriorityCheck){
        validityChecks.add(0,firstPriorityCheck);
        this.validators = validityChecks;
        this.data = data;
        this.sortOrder = order;
    }


    /**
     * Set  the order by which the validators will be applied to the data
     * @param sortOrder
     */
    public void setSortOrder(ValidationOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Return the order by which the validators will be applied to the data
     * @return ValidationOrder.BYINSERTIONORDER | ValidationOrder.BYPRIORITYORDER
     */
    public ValidationOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * Set the validators that will be applied to the data
     * according to the order specified by the data field sortOrder
     * @param validators
     */
    public void setValidators(ArrayList<T> validators) {
        this.validators = validators;
    }

    /**
     * @return the list of all validators
     */
    public ArrayList<T> getValidators() {
        return validators;
    }

    /**
     * Add a new validator to the existing list of validators if applicable.
     * Otherwise create a new list and add the new validator.
     * @param validator of type T - generic
     */
    public void addValidators(T validator) {
        if(this.validators == null)
            this.validators = new ArrayList<>();
        this.validators.add(validator);
    }

    /**
     * Add a new validator at index 'index' to the existing list of validators if applicable.
     * Otherwise create a new list and add the new validator.
     * @param validator type T :
     * @param index type integer :
     */
    public void addValidatorsAtIndex(T validator, int index) {
        if(this.validators == null)
            this.validators = new ArrayList<>();
        this.validators.add(index, validator);
    }

    /**
     * Return the data used/to be used for validation
     * @return V
     */
    public V getData() {
        return data;
    }

    /**
     * Set the data to be validated
     * @param data - data intended for evaluation
     */
    public void setData(V data) {
        this.data = data;
    }

    /**
     * Return the message about the state of the validators and the validations if applicable
     * @return message
     */
    public String getNoticationMessage() {
        return noticationMessage;
    }

    /**
     * Set important message about the state of the validations.
     * For example if all validations were successful or if something crashed etc.
     * @param noticationMessage - a message for the Validator Controller sub-class
     */
    public void setNoticationMessage(String noticationMessage) {
        this.noticationMessage = noticationMessage;
    }

    /**
     * Return the number of Successful Validations out of all validators
     * @return int NumberOfSuccessfullValidations
     */
    public int getNumberOfSuccessfullValidations() {
        return numberOfSuccessfullValidations;
    }

    /**
     * Set the number of successful validations  with integer numberOfSuccessfullValidations
     * @param numberOfSuccessfullValidations
     */
    public void setNumberOfSuccessfullValidations(int numberOfSuccessfullValidations) {
        this.numberOfSuccessfullValidations = numberOfSuccessfullValidations;
    }

    /**
     * Increment the parameter numberOfSuccessfullValidations
     * Can be used for tracking of the number of successful validations
     */
    public void incrementNumberOfSuccessfullValidations() {
        this.numberOfSuccessfullValidations++;
    }


    /**
     * Set true if the data has passed all the validations specified in the validators
     * @param passed boolean
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    /**
     * Return true if the data has successfully passed ALL the validations specified in the validators
     * @return
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Return the rate of success R
     * R = numberOfFailedValidation / totalNumberOfValidations
     * @return double
     */
    public double getSuccessRate() {
        return successRate;
    }

    /**
     * Should apply all the validators to the data
     * and set useful message or set useful data
     */
    public abstract void applyValidators();



}
