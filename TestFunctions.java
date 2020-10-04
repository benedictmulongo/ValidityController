package com.company;

import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.lang.Math;

/**
 * A class containing different tests functions used when developing  and debugging
 * the project software.
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 */
public class TestFunctions
{
    /**
     * Main methods for testing/debugging different functions and features
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        Tests1();
        //Tests2();
        //Tests3();
        //Tests4();
        //Tests5();
        //Tests6();
        //Tests7();
        //Tests8();
    }

    /**
     * Test all the validators
     * Car number plate, Social number, not null
     * @throws FileNotFoundException
     */
    public static void Tests8() throws FileNotFoundException {
        String forbiddenWordsPath = "forbidenLetterCombinations.txt";
        ArrayList<String> stopWords =  getForbiddenWords(forbiddenWordsPath);

        // ValidityChecker with single validator
        ValidityChecker validityCheckerNotNull;
        // ValidityChecker with multiple validators [PersonNumber | CarNumber]
        ArrayList<Validatable<String>> validityChecksPersonNumber;
        ValidityChecker validityCheckerPersonNumber;
        ArrayList<Validatable<String>> validityChecksCarNumber;
        ValidityChecker validityCheckerCarNumber;

        String UserInputData = "";
        System.out.println("*###############################################################*");
        Scanner input;
        String nullString = null;
        NotNullValidator<String> notNullValidator =  new NotNullValidator<>(4);

        int  numberOfCorrect;
        int [] indexNumberOfCorrect;
        while(!UserInputData.toLowerCase().equals("stop"))
        {
            numberOfCorrect = 0;
            indexNumberOfCorrect = new int [3];
            input =  new Scanner(System.in);
            System.out.println("Enter a non-Null input : ");
            UserInputData = input.nextLine();
            String isEmpty = UserInputData.length() == 0 ? "empty" : "not empty ";

            // Check if UserInputData is non-Null
            validityCheckerNotNull = new ValidityChecker(new NotNullValidator<>(1) , UserInputData);
            validityCheckerNotNull.applyValidators();
            System.out.println("Data is " + isEmpty);
            //int hasPassed = validityCheckerNotNull.isPassed() ? 1 : 0;
            //indexNumberOfCorrect[0] = hasPassed;

            // Check if UserInputData is a Social Security Number
            input =  new Scanner(System.in);
            System.out.println("Enter a swedish social security number : ");
            UserInputData = input.nextLine();
            validityChecksPersonNumber = new ArrayList<>(Collections.singletonList(new SocialSecurityNumberValidator<String>(2)));
            validityCheckerPersonNumber = new ValidityChecker(validityChecksPersonNumber, UserInputData);
            validityCheckerPersonNumber.applyValidators();

            // Check if UserInputData is a Car plate Number
            input =  new Scanner(System.in);
            System.out.println("Enter a swedish car number plate : ");
            UserInputData = input.nextLine();
            validityChecksCarNumber = new ArrayList<>(Collections.singletonList(new CarRegistrationNumberValidator<>( 2, stopWords)));
            validityCheckerCarNumber = new ValidityChecker(validityChecksCarNumber, UserInputData);
            validityCheckerCarNumber.applyValidators();

            System.out.println("Redo a test for [NotNull, SocialSecurityNumber, CarRegistrationNumber]");
            System.out.println("If you want to continue write any word, otherwise write stop !");
            input = new Scanner(System.in);
            UserInputData = input.nextLine();
        }
        System.out.println("Bye ! See you later for more tests ");

    }

    /**
     * Test the properties of class ValidityChecker
     * @throws FileNotFoundException
     */
    public static void Tests7() throws FileNotFoundException {
        String valData1 = null;
        String valData2 = "199403110050";
        String valData3 = "ABc45t";
        String valData1_false = "null";
        String valData2_false = "1994v";
        String valData3_false = "ABC-453";
        String forbiddenWordsPath = "forbidenLetterCombinations.txt";
        ArrayList<String> stopWords =  getForbiddenWords(forbiddenWordsPath);

        ArrayList<Validatable<String>> fullTest1 = new ArrayList<>();
        fullTest1.add(new CarRegistrationNumberValidator<>( 5, stopWords));
        fullTest1.add(new SocialSecurityNumberValidator<>(2));

        ArrayList<Validatable<String>> validityChecksCarNumber = new ArrayList<>();
        validityChecksCarNumber.add(new NotNullValidator<>(10));
        validityChecksCarNumber.add(new CarRegistrationNumberValidator<>( 5, stopWords));

        ArrayList<Validatable<String>> validityChecksPersonNumber = new ArrayList<>();
        validityChecksPersonNumber.add(new NotNullValidator<>(4));
        validityChecksPersonNumber.add(new SocialSecurityNumberValidator<>(8));

        String dataInput = valData3_false;
        ValidityChecker validityChecker = new ValidityChecker(fullTest1, dataInput);

        System.out.println("************************+++++++++++******************************");
        validityChecker.applyValidators();
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * Test properties of classes implementing Validatable
     * check the effect of of try-catch and if the validity of each validations can be retrieved
     * @throws FileNotFoundException
     */
    public static void Tests6() throws FileNotFoundException {
        String valData1 = null;
        String valData2 = "199403110050";
        String valData3 = "ABC-456";
        String valData2_false = "199403110059";
        String valData3_false = "ABC-45o";

        System.out.println("************************+++++++++++******************************");
        String forbiddenWordsPath = "forbidenLetterCombinations.txt";
        ArrayList<String> stopWords =  getForbiddenWords(forbiddenWordsPath);

        ArrayList<Validatable<String>> g = new ArrayList<>();
        g.add(new NotNullValidator<>(1));
        g.add(new CarRegistrationNumberValidator<>( 100, stopWords)); // Set forbidden Words
        g.add(new SocialSecurityNumberValidator<>(2));
        g.add(new CarRegistrationNumberValidator<>( 10, stopWords)); // Set forbidden Words

        String valData = valData3;
        System.out.println("Data to be evaluated : " + valData);
        System.out.println("--------------------------------------------------------------");
        for (Validatable<String> validator : g) {
            boolean validity = false;
            try {
                validity = validator.validate(valData); // if false Break after this in production  !!!
            } catch (Exception e) {
                System.out.println("Fatal error in the input ! " + e.getMessage());
            }

            // LAst Step !!!
           if (!validity)
           {
               System.out.println("Failed - Hahahaha");
               break;
           }else {
               System.out.println("Yehehe - Yupi");
               System.out.println(validator);
           }

        }
        //IllegalFormatException
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * Test Validatable and test to apply a set of validators to the data
     * Test sortOrder property and ValidationOrder.BYINSERTIONORDER
     */
    public static void Tests5()
    {
        ValidationOrder valOrder1 = ValidationOrder.BYINSERTIONORDER ;
        ValidationOrder valOrder2 = ValidationOrder.BYINSERTIONORDER ;
        ValidationOrder valOrder3 = ValidationOrder.BYPRIORITYORDER ;
        System.out.println(" : " + valOrder1);
        System.out.println(" : " + valOrder1.equals(valOrder2));
        System.out.println(" : " + valOrder1.equals(valOrder3));
        String valData1 = null;
        String valData2 = "199403110050";
        String valData3 = "ABC-456";
        String valData1_false = "null";
        String valData2_false = "199403110059";
        String valData3_false = "ABC-45o";

        System.out.println("************************+++++++++++******************************");
        ArrayList<Validatable<String>> g = new ArrayList<>();
        g.add(new NotNullValidator<>(4));
        g.add(new SocialSecurityNumberValidator<>(8));
        g.add(new CarRegistrationNumberValidator<>(2)); // SEt forbidden Words
        // CarRegistrationNumberValidator( int p, ArrayList<String> forbidenWords)
        System.out.println(" + " + g);
        // Sort By priority
        /*g.sort(Comparator.comparingInt(Validatable::getPriority));*/
        System.out.println(" - " + g);
        System.out.println("--------------------------------------------------------------");

        for (Validatable<String> validator : g) {
            boolean temp = validator.validate(valData3); // if false Break after this in production  !!!
            System.out.println(validator);
           /* if (!temp)
                break; */
        }
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * Return forbidden words as an ArrayList<String>
     * @param path  "forbidenLetterCombinations.txt" in the same folder as the TestFunctions.java and other .java files
     * @return ArrayList<String> all the forbidden words
     * @throws FileNotFoundException
     */
    public static ArrayList<String> getForbiddenWords(String path) throws FileNotFoundException {
        // "forbidenLetterCombinations.txt"
        Scanner inFile = new Scanner(new File(path));
        String  forbidenWords = inFile.nextLine().toLowerCase();
        String[] forbidenLetterCombinations = forbidenWords.split("[,][\\s]|[\\s][,]|[,]|[\\s]");
        ArrayList<String> stopWords =  new ArrayList<>(Arrays.asList(forbidenLetterCombinations));
        return stopWords;
    }


    /**
     * Test that the correctness of regular expressions for validation Car Plate Number
     * @throws FileNotFoundException
     */
    public static void Tests4() throws FileNotFoundException {
        System.out.println("************************+++++++++++******************************");
        String data = "12463665o";
        System.out.println("Input : " + data + " True? " + (data.charAt(data.length()-1) == 'o'));

        System.out.println("--------------------------------------------------------------");

        Scanner inFile = new Scanner(new File("forbidenLetterCombinations.txt"));
        String  forbidenWords = inFile.nextLine().toLowerCase();
        String[] forbidenLetterCombinations = forbidenWords.split("[,][\\s]|[\\s][,]|[,]|[\\s]");
        //ArrayList<String> stopWords =  new ArrayList<>(Arrays.asList(forbidenLetterCombinations));
        ArrayList<String> stopWords = null;


        System.out.println("*###############################################################*");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Car plate number : ");
        String entered = input.nextLine();
        while(!entered.equals("stop"))
        {
            boolean valid = sanityStepCarPlateNumber(entered.toLowerCase().trim());
            String b = validateCarRegistration(entered, stopWords );
            System.out.println("**** VALIDATE FUNCTION **** :  " + b);
            System.out.println("Car plate number [" + entered + "] is VALID ? <--- " +  valid);
            System.out.println("Enter Car plate number : or Write stop ");
            input = new Scanner(System.in);
            entered = input.nextLine();
        }
    }

    /**
     * Check the validaty of data as a  validation Car Plate Number
     * @param data  - Data to be evaluated
     * @param stopWords - forbidden words
     * @return
     */
    public static String validateCarRegistration(String data, ArrayList<String> stopWords)
    {
        int length = data.length();
        data = data.toLowerCase();
        String pattern1 = "[a-z&&[^äåöiqv]]{3}[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String pattern2 = "[a-z&&[^äåöiqv]]{3}([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String pattenToSplit = "[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])|([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String wordCombination = data.replaceAll(pattenToSplit, "").trim();
        String [] forbidenLetters = new String[]{"ä", "å", "ö", "i", "q", "v"};

        if (data.length() == 0)
            return "Input is empty";
        if(data.charAt(length-1) == 'o')
            return  "Plate number cannot contains o as the last letter";
        for (String t: forbidenLetters) {
            if(data.contains(t))
                return "Plate number cannot contains [ä,å,ö,i,q, v]";
        }
        if ( !(data.matches(pattern1) || data.matches(pattern2)) )
            return "Data format is wrong, should be (3Letters3Numbers)|(3Letters2Numbers1letters)";

        if(stopWords == null )
        {
            if(data.matches(pattern1) || data.matches(pattern2) )
                return "Valid plate number (1) --->";
        }
        else
        {
            if((data.matches(pattern1) || data.matches(pattern2) ) && (!stopWords.contains(wordCombination)))
                return "Valid plate number (2) --->";
            else
                return "Plate number contains forbidden word : " + wordCombination;
        }
        return "Problem with data";
    }

    /**
     * Check Regular expressions for sanityStepCarPlateNumber
     * @param data
     * @return
     * @throws FileNotFoundException
     */
    public static boolean sanityStepCarPlateNumber(String data) throws FileNotFoundException {
        String pattern1 = "[a-z&&[^äåöiqv]]{3}[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String pattern2 = "[a-z&&[^äåöiqv]]{3}([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";

        String pattenToSplit = "[[\\s]|[-]]([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])|([\\d]{3}|[\\d]{2}[a-z&&[^äåöoiqv]])";
        String wordCombination = data.replaceAll(pattenToSplit, "");
        System.out.println("SPLIT---> : [" + wordCombination + "]");

        Scanner inFile = new Scanner(new File("forbidenLetterCombinations.txt"));
        String  forbidenWords = inFile.nextLine().toLowerCase();
        String[] forbidenLetterCombinations = forbidenWords.split("[,][\\s]|[\\s][,]|[,]|[\\s]");
        ArrayList<String> stopWords =  new ArrayList<>(Arrays.asList(forbidenLetterCombinations));
        System.out.println("Contains forbidden words ? : " + stopWords.contains(wordCombination.trim()));

        return (data.matches(pattern1) || data.matches(pattern2) ) && (!stopWords.contains(wordCombination.trim()));
    }

    /**
     * Validate if a data is a valid car plate number
     * @param dataInput
     * @return string
     */
    public static String validate(String dataInput)
    {
        String data = sanityStep(dataInput);
        int length = data.length();
        System.out.println("validate data : " + data);
        if (data.length() == 0)
            return "Input is empty";
        if(!data.matches("\\d{" + length + "}"))
            return "Social number should contain only numbers";
        if (data.length() != 10)
            return "Social number must have exactly 10 or 12 numbers";
        /*
        if(!data.matches("\\d{10}"))
            return "Social number should contain only numbers"; */

        boolean isValid = isSocialSecurityNumber(data);

        return "STATUS: " + isValid;
    }

    /**
     * Remove (-) and white space (\\s) from input data
     * @param data
     * @return a string without - and/or whit space and of length 10 if the data input length is 12
     */
    public static String sanityStep(String data)
    {
        String s = data.replaceAll("[-]", "");
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
    public static boolean isSocialSecurityNumber(String personNumber)
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
            //System.out.print( "[" + temp + ", splitSum : " + splitSum + "]");
        }
        //System.out.println("");
        int checkSum = getSum(valueHolder);
        checkSum = 10 - modulo(checkSum, 10) ;
        checkSum = modulo(checkSum, 10);
        //System.out.println("[checkSum : " + checkSum + ", personNumberCheck : " + personNumberCheck + "]");
        return checkSum == personNumberCheck;

    }

    /**
     * Test the implementation for the social security number validation algorithm
     * for different formats and input
     */
    public static  void Tests3()
    {
        System.out.println("************************+++++++++++******************************");
        String personNumberV1 = "19940311-0050";
        String personNumberV2 = "940311-0050";
        String personNumberV3 = "199403110050";
        String personNumberV4 = "9403110050";
        String personNumberV5 = "9403110050454520";
        String personNumberV6 = "19940311xx0050";
        System.out.println("nbV1 : " + personNumberV1 + " sanityStep : " + sanityStep(personNumberV1));
        System.out.println("nbV2 : " + personNumberV2 + " sanityStep : " + sanityStep(personNumberV2));
        System.out.println("nbV3 : " + personNumberV3 + " sanityStep : " + sanityStep(personNumberV3));
        System.out.println("nbV4 : " + personNumberV4 + " sanityStep : " + sanityStep(personNumberV4));
        System.out.println("nbV3 : " + personNumberV5 + " sanityStep : " + sanityStep(personNumberV5));
        System.out.println("nbV4 : " + personNumberV6 + " sanityStep : " + sanityStep(personNumberV6));
        System.out.println("***************************************************************");
        String input = "9403110050";
        String input1 = "9403110057";
        String validation = validate(input1);
        System.out.println("Validation : " + validation);
        System.out.println("*###############################################################*");
        System.out.println(validate("19820278502022389"));
    }

    /**
     * Test the implementation for the social security number validation algorithm
     * for simple format "9204112380"
     */
    public static void Tests2()
    {
        //String personNumber = "9204112380";
        //String personNumber = "9403110050";
        //String personNumber = "7802022389";
        System.out.println("************************+++++++++++******************************");
        String personNumber = "9204112380";
        //String personNumber = "9403110050";
        //String personNumber = "7802022389";
        String personNumberT = personNumber.substring(0,personNumber.length()-1);
        int personNumberCheck = Integer.parseInt(String.valueOf(personNumber.charAt(personNumber.length()-1)));
        System.out.println("personNumber : " + personNumber);
        System.out.println("personNumberT : " + personNumberT);
        System.out.println("personNumberCheck : " + personNumberCheck);

        System.out.println("************************ Test CRazy ******************************");
        int [] valueHolder = new int [personNumberT.length()];  // hold operations
        System.out.println("Length PersonNummer  : " + personNumberT.length() );
        System.out.println("PersonNummer  : " + personNumberT );
        for(int i = 0; i < personNumberT.length() ; i++)
        {
            char character = personNumberT.charAt(i);
            int temp = Integer.parseInt(String.valueOf(character));
            int mask = getMask(i);
            temp = temp * mask;
            int splitSum = splitInteger(temp);
            valueHolder[i] = splitSum;
            System.out.print( "[" + temp + ", splitSum : " + splitSum + "]");
        }
        System.out.println("");
        int checkSum = getSum(valueHolder);
        System.out.println("checkSum (1) : " + checkSum);
        checkSum = 10 - modulo(checkSum, 10) ;
        System.out.println("checkSum (2) : " + checkSum);
        checkSum = modulo(checkSum, 10);
        System.out.println("checkSum (3): " + checkSum);
        System.out.println("[checkSum : " + checkSum + ", personNumberCheck : " + personNumberCheck + "]");


        System.out.println("***************************************************************");

    }

    /**
     * Test Validatable with the class NotNullValidator
     */
    public static void Tests1()
    {
        ArrayList<Validatable<String>> g = new ArrayList<>();
        g.add(new NotNullValidator<>(1));
        g.add(new NotNullValidator<>(2));
        g.add(new NotNullValidator<>(3));

        String personNumber = "199403110050";
        BigInteger f = new BigInteger("199403110050");

        System.out.println(g);
        g.get(0).validate("benn");

        NotNullValidator<String> validator = new NotNullValidator<>();
        String v = null;
        validator.validate(v);
        System.out.println("Print the validator : ");
        System.out.println(validator);
        System.out.println(validator.priority);
        System.out.println(validator.dataToValidate);
    }

    /**
     * return  2 or 1 based on the mathematical formula :
     *  mask = [((-1)^index + 1) / 2 ] + 1 where index is the variable  input
     * @param index - a integer - not to big as the exponent is computed
     * @return 2|1 depending of index
     */
    public static int getMask(int index)
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
    public static int splitInteger(int number)
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
        /*
        System.out.print("Array : [" );
        for(int i = 0; i < array.length - 1; i++)
            System.out.print(array[i] + ", " );
        System.out.print(array[array.length -1] + "]" );
        System.out.println( ); */

        return getSum(array);
    }

    /**
     * Sum all the elements in an array s = [a,b,c]
     * @param array - an integer array
     * @return integer the sum of the array's elements (a + b + c)
     */
    public static int getSum(int[] array) {
        return Arrays.stream(array).sum();
    }
    /**
     * Return the modulo operation of numerator and denominator
     * @param numerator
     * @param denominator
     * @return integer mod(numerator,denominator )
     */

    public static int modulo(int numerator, int denominator )
    {
        return numerator % denominator ;
    }

    /**
     * LEgacy - similar to splitInteger
     * @param number
     */
    public static void splitIntegerBackUp(int number)
    {
        String tempNumber = String.valueOf(number);
        int length = tempNumber.length();
        String base = "1" + "0".repeat(Math.max(0, length - 1));
        int baseNumber = Integer.parseInt(base);

        int div = number / baseNumber;

        System.out.println("[splitInteger ( number : "+ number + ", base : " +  base +
                ", baseNumber : " + baseNumber + ") ]" );
        System.out.println("[(Div : " + div + ") ]" );
    }
}
