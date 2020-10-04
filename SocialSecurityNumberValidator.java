import java.util.*;

/**
 * SocialSecurityNumberValidator is a class that can be used for validating whether
 * an user input is a valid swedish social security number.
 * SocialSecurityNumberValidator implements the interface Validatable.
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 * @param <InputData>
 */
public class SocialSecurityNumberValidator<InputData> implements Validatable<String>
{
    public int priority;
    InputData dataToValidate;
    // Add static int to track #objects created and the spec. object failed -MAYBE
    private final String validatorName = this.getClass().getName();
    private boolean isValid;
    private String message;

    SocialSecurityNumberValidator(){this.priority = Integer.MAX_VALUE; }
    SocialSecurityNumberValidator(int p) {this.priority = p;}
    SocialSecurityNumberValidator(InputData data) {
        this.dataToValidate = data;
        this.priority = Integer.MAX_VALUE;
    }
    SocialSecurityNumberValidator(InputData data, int p)
    {
        this.dataToValidate = data;
        this.priority = p;
    }

    /**
     * Validate if the input data represents a valid swedish social sec. number based on the rules specified
     * The acceptable for are :
     * YYMMDD-XXXX, , YYYYMMDD-XXXX,
     * YYMMDDXXXX, YYYYMMDDXXXX
     * YYMMDD XXXX, YYYYMMDD XXXX
     * @param dataInput - data to be evaluate
     * @return boolean - true | false depending of the validity of the data as a swedish social sec. number
     */
    @Override
    public boolean validate(String dataInput)
    {
        String data = sanityStep(dataInput);
        int length = data.length();

        if (dataInput.length() == 0)
            return setValidationNotifier(dataInput, "Input is empty", false);
        if(!data.matches("\\d{" + length + "}")) // Contains letters - regex
            return setValidationNotifier(dataInput, "Social security number should contain only digits", false);
        if (data.length() != 10)
            return setValidationNotifier(dataInput, "Social security number must have exactly 10 or 12 digits", false);

        if(isSocialSecurityNumber(data))
            return setValidationNotifier(dataInput, "Valid social security number !", true);

        return setValidationNotifier(dataInput, "Input is not a valid social security number.", false);
    }

    /**
     * Remove (-) and white space (\\s) from input data
     * @param data
     * @return a string without - and/or whit space and of length 10 if the data input length is 12
     */
    public String sanityStep(String data)
    {
        String s = data.trim().replaceAll("[-|\\s]", "");
        if(s.length() == 12)
            s = s.substring(2);
        return s;
    }

    /**
     *  Implement the algorithm for determining whether a strign represents a valid swedish social sec. number
     *  The following shows the rules/algorithm for determining the validity of a swedish social sec. number
     *  let R : Result of precedent operation
     *  M:multiplication , A : addition, MOD:modulo operation
     *  S : sum row
     *  Let Pnr : 197802022389
     * 19 |    7   |    8   |    0   |    2   |    0   |    2    |    2   |    3   |    8   |   9   |
     * M  |   *2   |   *1   |   *2   |   *1   |   *2   |   *1    |   *2   |   *1   |   *2   |       |
     * R  |   14   |   8    |   0    |    2   |    0   |    2    |    4   |    3   |    16  |       |
     * A  |  1+4   |   8    |   0    |    2   |    0   |    2    |    4   |    3   |   1+6  |       |
     * R  |   5    |   8    |   0    |    2   |    0   |    2    |    4   |    3   |    7   |       |
     * S  |   5+   |   8+   |   0+   |    2+  |    0+  |    2+   |    4+  |    3+  |    7   |       |
     * R  |        |        |        |        |        |         |        |        |   31   |       |
     * MOD|        |        |        |        |        |         |        |        | mod 10 |       |
     * R  |        |        |        |        |        |         |        |        |   1    |       |
     *10-R|        |        |        |        |        |         |        |        |  10-1  |       |
     * R  |        |        |        |        |        |         |        |        |   9    |       |
     * MOD|        |        |        |        |        |         |        |        | mod 10 |       |
     * R  |        |        |        |        |        |         |        |        |   9    |   9   |
     * Check|      |        |        |        |        |         |        |        |  9==9 -> Valid |
     * @param personNumber - a string for person number validation
     * @return True if valid person number false otherwise
     */
    private boolean isSocialSecurityNumber(String personNumber)
    {
        String personNumberT = personNumber.substring(0,personNumber.length()-1);
        int personNumberCheck = Integer.parseInt(String.valueOf(personNumber.charAt(personNumber.length()-1)));
        int [] valueHolder = new int [personNumberT.length()];  // hold operations

        for(int i = 0; i < personNumberT.length() ; i++)
        {
            char character = personNumberT.charAt(i);
            int temp = Integer.parseInt(String.valueOf(character));
            int mask = getMask(i);
            temp = temp * mask;
            int splitSum = splitInteger(temp);
            valueHolder[i] = splitSum;
        }
        int checkSum = getSum(valueHolder);
        checkSum = 10 - modulo(checkSum, 10) ;
        checkSum = modulo(checkSum, 10);

        return checkSum == personNumberCheck;
    }

    /**
     * return  2 or 1 based on the mathematical formula :
     *  mask = [((-1)^index + 1) / 2 ] + 1 where index is the variable  input
     * @param index - a integer - not to big as the exponent is computed
     * @return 2|1 depending of index
     */
    public int getMask(int index)
    {
        final int TEMP = -1;
        double t = Math.pow(TEMP, index) + 1;
        t = (t / 2) + 1;
        return (int) t;
    }

    /**
     * Splits a number to its individual digits and sum all the digits
     * Ex. 123 --> [1,2,3]  --> 1 + 2 + 3 --> 6
     * @param number a integer to be split into indiviual digits
     * @return integer the sum of individual digits of input number
     */
    public int splitInteger(int number)
    {
        String tempNumber = String.valueOf(number);
        int length = tempNumber.length();
        int [] array = new int [length];
        for(int i = 0; i < length; i++)
        {
            char character = tempNumber.charAt(i);
            int temp = Integer.parseInt(String.valueOf(character));
            array[i] = temp;
        }

        return getSum(array);
    }

    /**
     * Sum all the elements in an array s = [a,b,c]
     * @param array - an integer array
     * @return integer the sum of the array's elements (a + b + c)
     */
    public int getSum(int[] array) { return Arrays.stream(array).sum(); }

    /**
     * Return the modulo operation of numerator and denominator
     * @param numerator
     * @param denominator
     * @return integer mod(numerator,denominator )
     */
    public int modulo(int numerator, int denominator ) { return numerator % denominator ; }

    /**
     * Return the data used/to be used for validation
     * @return String
     */
    public String getDataToValidate() { return (String) dataToValidate; }

    /**
     * Set the data to be validated
     * @param dataToValidate - data intended for evaluation
     */
    public void setDataToValidate(String dataToValidate) { this.dataToValidate = (InputData) dataToValidate; }

    /**
     * Return the priority parameter
     * @return Integer
     */
    @Override
    public int getPriority() { return priority; }

    /**
     * Set the priority of the SocialSecurityNumberValidator.
     * The priority p is used for setting the order by which the validators will be applied to the data
     * @param priority
     */
    @Override
    public void setPriority(int priority) { this.priority = priority; }

    /**
     * Set the the data, message and the validity of the data
     * @param dataToValidate - the data to be evaluated
     * @param msg - a message for describing the state of the validation
     * @param validity - boolean for describing whether the validation was successful or not
     * @return true if dataToValidate is a valid SocialSecurityNumber, false otherwise
     */
    @Override
    public boolean setValidationNotifier(String dataToValidate, String msg, boolean validity) {
        this.dataToValidate = (InputData) dataToValidate;
        this.message = msg;
        this.isValid = validity;
        return validity;
    }

    /**
     * Return a unique string name describing for the SocialSecurityNumberValidator.class
     * Useful for logger and debugging
     * @return a string with the validator class name
     */
    @Override
    public String getValidatorName() {
        return this.validatorName;
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
        return "The social security number is composed of 10 or 12 digits" +
                " witch may or may not be separate by - ";
    }

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
     * Data representation of the class SocialSecurityNumberValidator
     * Used in console printing and error logging.
     * @return string with the [priority, dataToValidate, dataToValidate, isValid, message]
     */
    @Override
    public String toString() {
        return "SocialSecurityNumberValidator{" +
                "priority=" + priority +
                ", dataToValidate=" + dataToValidate +
                ", validatorName='" + validatorName + '\'' +
                ", isValid=" + isValid +
                ", message='" + message + '\'' +
                '}';
    }

}

