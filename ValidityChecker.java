import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;
import java.util.logging.*;

/**
 * This  class ValidityChecker extends ValidatorController.
 * It is useful for applying validators of type Validatable to an data
 * Different statistic, data, behavior, log message can be retrieved by this class
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 */
public class ValidityChecker extends ValidatorController<Validatable<String>,String>
{
    // Set Logger name
    private final static Logger ValidityCheckerLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    //ValidityChecker() { super(); }
    //TODO IMPROVEMENT ---Add constructors only with validator
    ValidityChecker(Validatable<String> validityChecks, String data)
    {
        super(validityChecks, data);
    }
    ValidityChecker(ArrayList<Validatable<String>> validityChecks, String data) {
        super(validityChecks, data, new NotNullValidator<>(0));
    }
    ValidityChecker(ArrayList<Validatable<String>> validityChecks, String data, ValidationOrder order) {
        super(validityChecks, data, order, new NotNullValidator<>(0));
        //addValidatorsAtIndex(new NotNullValidator<>(0), 0);
    }

    /**
     * Set up the file logger and the console logger.
     * As this is a specific implementation.
     * There no possibility to change the file path or file name
     * The file log file name is "failedValidationsAndSevereProblems.log"
     * Console logger level is set to Level.SEVERE
     * And file logger level is set to Level.WARNING
     */
    private static void setupLogger(){

        // write your code he
        LogManager.getLogManager().reset();
        ValidityCheckerLogger.setLevel(Level.ALL); // Set all level on

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE); // Log only Severe messages
        ValidityCheckerLogger.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("failedValidationsAndSevereProblems.log", true);
            fh.setLevel(Level.WARNING); // Log Severe and Warning messages
            ValidityCheckerLogger.addHandler(fh);
        }
        catch (IOException e)
        {
            ValidityCheckerLogger.log(Level.SEVERE, "File logger is not working" , e);
        }
    }

    /**
     * Should apply all the validators of type Validatable to the data
     * and set useful messages or set
     */
    @Override
    public void applyValidators()
    {
        System.out.println("Data : "+ this.data);
        // Setup logger handler before any operation
        setupLogger();
        // Check the order of validation
        if(this.sortOrder.equals(ValidationOrder.BYPRIORITYORDER))
            validators.sort(Comparator.comparingInt(Validatable::getPriority));

        // Set into try finally block
        boolean validity = false;
        for (Validatable<String> validator : validators)
        {
            try {
                validity = validator.validate(this.data);
            } catch (Exception e) {
                ValidityCheckerLogger.log(Level.WARNING, "Fatal error in the input ( " + e.getMessage() + ")");
                System.out.println("Fatal error in the input ( " + e.getMessage() + ")");
                break;
            }

            // Last Step
            if(!validity)
            {
                // Failure
                ValidityCheckerLogger.log(Level.WARNING, validator.toString());
                System.out.println(validator.getMessage());
                System.out.println("(" + validator.correctInputFormat() + ")");
                break;
            }
            else{
                //Success
                incrementNumberOfSuccessfullValidations();
                System.out.println(validator.getMessage());
            }
        }
        if(getNumberOfSuccessfullValidations() == validators.size()) // Passed all validations
            setPassed(true);
        //System.out.println("**************  DEBUG  ************** ");
        //debug();
        //System.out.println("**************  DEBUG  **************  ");
    }

    private void debug()
    {
        System.out.println("||| DEBUG ||| ");
        // Debugging
        if(getNumberOfSuccessfullValidations() == validators.size())
        {
            setNoticationMessage("All validations are passed. The data are correct");
            String msg = "The data have passed " +  getNumberOfSuccessfullValidations() + " out of "
                    + validators.size() + ". (Success rate = " + getSuccessRate() + ")";
            System.out.println(getNoticationMessage());
            System.out.println(msg);
        }
        else
        {
            System.out.println("*************************************************************");
            this.successRate = (double)getNumberOfSuccessfullValidations() / validators.size();
            String msg = "The data have passed " +  getNumberOfSuccessfullValidations() + " out of "
                    + validators.size() + ". (Success rate = " + getSuccessRate() + ")";
            StringBuilder str = new StringBuilder();
            str.append("The data have failed validator(s) : [");
            for(int i = getNumberOfSuccessfullValidations(); i < validators.size() - 1; i++)
            {
                str.append(validators.get(i).getValidatorName()).append(", ");
            }
            str.append(validators.get(validators.size() - 1).getValidatorName()).append("]");
            msg = msg + "\r\n"+ str.toString();
            setNoticationMessage(msg);
            System.out.println(getNoticationMessage());
        }
    }



}
