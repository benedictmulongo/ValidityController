import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

/**
 * Main function for user inpute testing via command line
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 */
public class Examples {

    /**
     * Live user testing via command line
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        //programMainExection();
        //example();
        //exampleForbiddenWords();
        //testValidatableIntefaceList
        //testValidatableIntefaceList();
        testValidityChecker();
    }
    public static void testValidityChecker()
    {
        String dataInput = "197802022389";
        ArrayList<Validatable<String>> valPersonNumber = new ArrayList<>();
        valPersonNumber.add(new NotNullValidator<>(4));
        valPersonNumber.add(new SocialSecurityNumberValidator<>(8));

        ValidityChecker validityChecker = new ValidityChecker(valPersonNumber, dataInput);
        validityChecker.applyValidators();
    }

    public static void testValidatableIntefaceList()
    {
        ArrayList<String> forbiddenWords = new ArrayList<>(Arrays.asList("UFO","FUL", "GUD"));
        String data = "FUX459";

        List<Validatable<String>> validators = new ArrayList<>();
        validators.add(new NotNullValidator<>(1));
        validators.add(new SocialSecurityNumberValidator<>());
        validators.add(new CarRegistrationNumberValidator<>(forbiddenWords));
        for (Validatable val: validators) {
            System.out.println("Validity : " + val.validate(data));
        }
    }


    public static void example()
    {
        // Test NotNullValidator
        String nullString = null;
        String notNulString = "testMe";
        // Test SocialSecurityNumberValidator
        String persNumber = "197802022389";
        String notPersNumber = "197802022100";
        // Test CarRegistrationNumberValidator
        String carPlateNumber = "FUL459";
        String notCarPlateNumber = "4BD459";

        Validatable<String> notNullValidator;
        Validatable<String> bilRegNumberValidator;
        Validatable<String> personNumberValidator;

        notNullValidator= new NotNullValidator<>();
        personNumberValidator = new SocialSecurityNumberValidator<>();
        bilRegNumberValidator= new CarRegistrationNumberValidator<>();

        print(nullString, "", notNullValidator.validate(nullString));
        print(notNulString, "", notNullValidator.validate(notNulString));

        print(persNumber, "personNumber", personNumberValidator.validate(persNumber));
        print(notPersNumber, "personNumber", personNumberValidator.validate(notPersNumber));

        print(carPlateNumber, "carNumber", bilRegNumberValidator.validate(carPlateNumber));
        print(notCarPlateNumber, "carNumber", bilRegNumberValidator.validate(notCarPlateNumber));
    }
    public static void print(String s, String n, boolean valid) {
        String temp = "Data[" + s + "] is (a) valid " + n + " ? " + valid;
        System.out.println(temp);
    }


    public static void exampleForbiddenWords()
    {
        ArrayList<String> forbiddenWords = new ArrayList<>(Arrays.asList("UFO","FUL", "GUD"));
        String carNumber = "FUX459";
        String forbiddenCarNumber = "FUL459";
        Validatable<String> bilRegNumberValidator;
        bilRegNumberValidator= new CarRegistrationNumberValidator<>(forbiddenWords);

        print(carNumber, "carNumber", bilRegNumberValidator.validate(carNumber));
        print(forbiddenCarNumber, "carNumber", bilRegNumberValidator.validate(forbiddenCarNumber));
        System.out.println( bilRegNumberValidator.getMessage());
        //
        //ArrayList<Validatable<String>> fullTest1 = new ArrayList<>();
        //fullTest1.add(new CarRegistrationNumberValidator<>( 5, stopWords));
        //fullTest1.add(new SocialSecurityNumberValidator<>(2));

    }

    public static void programMainExection()
    {
        boolean forward = true;
        String forbiddenWordsPath = "forbidenLetterCombinations.txt";
        ArrayList<String> stopWords =  new ArrayList<>();
        try {
            stopWords = getForbiddenWords(forbiddenWordsPath); }
        catch (Exception e) {
            forward = false;
            System.out.println("The required file \"forbidenLetterCombinations.txt\" is not in the folder ! ");
        }


        // ValidityChecker with single validator
        ValidityChecker validityCheckerNotNull;
        // ValidityChecker with multiple validators [PersonNumber | CarNumber]
        ArrayList<Validatable<String>> validityChecksPersonNumber;
        ValidityChecker validityCheckerPersonNumber;
        ArrayList<Validatable<String>> validityChecksCarNumber;
        ValidityChecker validityCheckerCarNumber;

        String UserInputData = "";
        System.out.println("********************************** WELCOME ************************************");
        System.out.println("The file \"forbidenLetterCombinations.txt\" is required ! ");
        System.out.println("********************************** WELCOME ************************************");
        Scanner input;
        String nullString = null;
        while(!UserInputData.toLowerCase().equals("stop"))
        {
            if(!forward)
            {
                System.out.println("The required file \"forbidenLetterCombinations.txt\" is not in the folder ! ");
                break;
            }
            /*
             Check if UserInputData is non-Null
             */
            input =  new Scanner(System.in,  "Cp850");
            System.out.println("Enter a non-Null input : ");
            UserInputData = input.nextLine();
            String isEmpty = UserInputData.length() == 0 ? "empty" : "not empty ";
            validityCheckerNotNull = new ValidityChecker(new NotNullValidator<>(1) , UserInputData);
            validityCheckerNotNull.applyValidators();
            System.out.println("Data is " + isEmpty);

            /*
             Check if UserInputData is a Social Security Number
             */
            input =  new Scanner(System.in, "Cp850");
            System.out.println("Enter a swedish social security number : ");
            UserInputData = input.nextLine();
            validityChecksPersonNumber = new ArrayList<>(Collections.singletonList(new SocialSecurityNumberValidator<String>(2)));
            validityCheckerPersonNumber = new ValidityChecker(validityChecksPersonNumber, UserInputData);
            validityCheckerPersonNumber.applyValidators();

            /*
             Check if UserInputData is a Car plate Number
             */
            input =  new Scanner(System.in, "Cp850");
            System.out.println("Enter a swedish car number plate : ");
            UserInputData = input.nextLine();
            validityChecksCarNumber = new ArrayList<>(Collections.singletonList(new CarRegistrationNumberValidator<>( 2, stopWords)));
            validityCheckerCarNumber = new ValidityChecker(validityChecksCarNumber, UserInputData);
            validityCheckerCarNumber.applyValidators();

            /*
             Check if User want to continue
             */
            System.out.println("Redo a test for [NotNull, SocialSecurityNumber, CarRegistrationNumber]");
            System.out.println("If you want to continue write any word, otherwise write stop !");
            input = new Scanner(System.in, "Cp850");
            UserInputData = input.nextLine();
        }
        System.out.println("Bye ! See you later for more tests ");

    }

    public static ArrayList<String> getForbiddenWords(String path) throws FileNotFoundException {
        // "forbidenLetterCombinations.txt"
        Scanner inFile = new Scanner(new File(path));
        String  forbidenWords = inFile.nextLine().toLowerCase();
        String[] forbidenLetterCombinations = forbidenWords.split("[,][\\s]|[\\s][,]|[,]|[\\s]");
        ArrayList<String> stopWords =  new ArrayList<>(Arrays.asList(forbidenLetterCombinations));
        return stopWords;
    }




}
