import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>
 *  The <code>Validator</code> class allows an application component or client to
 *    provide data, and determine if the data is valid for the requested type.
 * </p>
 */
public class Validator {
    /** The instances of this class for use (singleton design pattern) */
    private static Map instances = null;
    /** The URL of the XML Schema for this <code>Validator</code> */
    private URL schemaURL;
    /** The constraints for this XML Schema */
    private Map constraints;
    /**
     * <p>
     *  This constructor is private so this the class cannot be instantiated
     *    directly, but instead only through <code>{@link #getInstance()}</code>.
     * </p>
     */
    private Validator(URL schemaURL) {
        this.schemaURL = schemaURL;
        constraints = new HashMap();
        // parse the XML Schema and create the constraints
    }
    /**
     * <p>
     *  This will return the instance for the specific XML Schema URL. If a schema
     *    exists, it is returned (as parsing will already be done); otherwise,
     *    a new instance is created, and then returned.
     * </p>
     *
     * @param schemaURL <code>URL</code> of schema to validate against.
     * @return <code>Validator</code> - the instance, ready to use.
     */
    public static Validator getInstance(URL schemaURL) {
        if (instances != null) {
            if (instances.containsKey(schemaURL.toString())) {
                return (Validator)instances.get(schemaURL.toString());
            } else {
                Validator validator = new Validator(schemaURL);
                instances.put(schemaURL.toString(), validator);
                return validator;
            }
        } else {
            instances = new HashMap();
            Validator validator = new Validator(schemaURL);
            instances.put(schemaURL.toString(), validator);
            return validator;
        }
    }
	
	
/**
     *
     *  This will validate a data value (in String format) against a
     *    specific constraint and throw an exception if there is a problem.
     *
     *
     * @param constraintName the identifier in the constraints to validate
this data against.
     * @param data String data to validate.
     */
    public void checkValidity(String constraintName, String data) throws InvalidDataException {
        // Validate against the correct constraint
        Object o = constraints.get(constraintName);
        // If no constraint, then everything is valid
        if (o == null) {
            return;
        }
        Constraint constraint = (Constraint)o;
        // Validate data type
        if (!correctDataType(data, constraint.getDataType())) {
            throw new InvalidDataException("Incorrect data type");
        }
        // Validate against allowed values
        if (constraint.hasAllowedValues()) {
            List allowedValues = constraint.getAllowedValues();
            if (!allowedValues.contains(data)) {
                throw new InvalidDataException("Disallowed value");
            }
        }
        // Validate against range specifications
        try {
            double doubleValue = new Double(data).doubleValue();
            if (constraint.hasMinExclusive()) {
                if (doubleValue <= constraint.getMinExclusive()) {
                    throw new InvalidDataException("Value is not large
enough");
                }
            }
            if (constraint.hasMinInclusive()) {
                if (doubleValue < constraint.getMinInclusive()) {
                    throw new InvalidDataException("Value is not large
enough");
                }
            }
            if (constraint.hasMaxExclusive()) {
                if (doubleValue >= constraint.getMaxExclusive()) {
                    throw new InvalidDataException("Value is not small
enough");
                }
            }
            if (constraint.hasMaxInclusive()) {
                if (doubleValue > constraint.getMaxInclusive()) {
                    throw new InvalidDataException("Value is not small
enough");
                }
            }
        } catch (NumberFormatException e) {
            // If it couldn't be converted to a number, the data type isn't
            //   numeric anyway, as it would have already failed,
            //   so this can be ignored.
        }
        // If we got here, all tests were passed
        // No return value needed
    }
}



import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public interface ApplePredicate
{
	boolean test (Apple apple);
}

public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) 
{
	List<Apple> result = new ArrayList<>();
	for (Apple apple: inventory) 
	{
		if ( apple.getColor().equals(color)) 
		{
			result.add(apple);
		}
	}
	return result;
}


public interface Predicate<T>
{
	boolean test(T t);
}

public static <T> List<T> filter(List<T> list, Predicate<T> p)
{ 
	List<T> result = new ArrayList<>();
	for(T e: list) 
	{
		if(p.test(e)) 
		{
			result.add(e);
		}
	}
	return result;
}

List<Apple> inventory
List<Apple> redApples = filter(inventory, (Apple apple) -> RED.equals(apple.getColor())); 
List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);


@Service
@AllArgsConstructor
public class DefaultSignUpValidationService implements SignUpValidationService {

    private final UserRepository userRepository;

    @Override
    public ValidationResult validate(SignUpCommand command) {
        return new CommandConstraintsValidationStep()
                .linkWith(new UsernameDuplicationValidationStep(userRepository))
                .linkWith(new EmailDuplicationValidationStep(userRepository))
                .verify(command);
    }

    private static class CommandConstraintsValidationStep extends ValidationStep<SignUpCommand> {

        @Override
        public ValidationResult verify(SignUpCommand command) {
            try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<SignUpCommand>> constraintsViolations = validator.validate(command);

                if (!constraintsViolations.isEmpty()) {
                    return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
                }
            }
            return checkNext(command);
        }
    }

    @AllArgsConstructor
    private static class UsernameDuplicationValidationStep extends ValidationStep<SignUpCommand> {

        private final UserRepository userRepository;

        @Override
        public ValidationResult verify(SignUpCommand command) {
            if (userRepository.findByUsername(command.getUsername()).isPresent()) {
                return ValidationResult.invalid(String.format("Username [%s] is already taken", command.getUsername()));
            }
            return checkNext(command);
        }
    }

    @AllArgsConstructor
    private static class EmailDuplicationValidationStep extends ValidationStep<SignUpCommand> {

        private final UserRepository userRepository;

        @Override
        public ValidationResult verify(SignUpCommand command) {
            if (userRepository.findByEmail(command.getEmail()).isPresent()) {
                return ValidationResult.invalid(String.format("Email [%s] is already taken", command.getEmail()));
            }
            return checkNext(command);
        }
    }
}
