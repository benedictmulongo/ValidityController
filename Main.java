package com.company;

import java.util.*;
import java.io.*;

/**
 * Main function for user inpute testing via command line
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 */
public class Main {

    /**
     * Live user testing via command line
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
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
        System.out.println("********************************** WELCOME ************************************");
        System.out.println("The file \"forbidenLetterCombinations.txt\" is require ! ");
        System.out.println("********************************** WELCOME ************************************");
        Scanner input;
        String nullString = null;
        NotNullValidator<String> notNullValidator =  new NotNullValidator<>(4);

        while(!UserInputData.toLowerCase().equals("stop"))
        {
            /*
             Check if UserInputData is non-Null
             */
            input =  new Scanner(System.in);
            System.out.println("Enter a non-Null input : ");
            UserInputData = input.nextLine();
            String isEmpty = UserInputData.length() == 0 ? "empty" : "not empty ";
            validityCheckerNotNull = new ValidityChecker(new NotNullValidator<>(1) , UserInputData);
            validityCheckerNotNull.applyValidators();
            System.out.println("Data is " + isEmpty);

            /*
             Check if UserInputData is a Social Security Number
             */
            input =  new Scanner(System.in);
            System.out.println("Enter a swedish social security number : ");
            UserInputData = input.nextLine();
            validityChecksPersonNumber = new ArrayList<>(Collections.singletonList(new SocialSecurityNumberValidator<String>(2)));
            validityCheckerPersonNumber = new ValidityChecker(validityChecksPersonNumber, UserInputData);
            validityCheckerPersonNumber.applyValidators();

            /*
             Check if UserInputData is a Car plate Number
             */
            input =  new Scanner(System.in);
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
            input = new Scanner(System.in);
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
