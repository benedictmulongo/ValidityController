package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class ValidityChecker extends ValidatorController<Validatable<String>,String>
{
    //ValidityChecker() { super(); }
    ValidityChecker(ArrayList<Validatable<String>> validityChecks, String data) { super(validityChecks, data); }
    ValidityChecker(ArrayList<Validatable<String>> validityChecks, String data, ValidationOrder order) {
        super(validityChecks, data, order);
    }

    @Override
    public void applyValidators()
    {

        if(this.sortOrder.equals(ValidationOrder.BYPRIORITYORDER))
            validators.sort(Comparator.comparingInt(Validatable::getPriority));

        System.out.println("--------------------------------------------------------------");

        int numberOfSuccessfullValidations;
        // Set into try finally block
        for (Validatable<String> validator : validators) {
            boolean temp = validator.validate(this.data); // if false Break after this in production  !!!
            if(!temp)
            {
                // Log Severe message validator.getValidatorName()
                break;
            }
            else{
                incrementNumberOfSuccessfullValidations();
                // log Happy message
            }

            if(getNumberOfSuccessfullValidations() == validators.size())
            {
                setNoticationMessage("All validations are passed. The data is correct");
                // log Happy message
            }
            System.out.println(validator);
        }
        System.out.println("--------------------------------------------------------------");
    }
}
