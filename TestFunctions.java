package com.company;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigInteger;
import java.lang.Math;

public class TestFunctions
{
    public static void main(String[] args)
    {
        String a = "";
        String b = "1";
        System.out.println("a : " + a + " length : " + a.length());
        System.out.println("b : " + b + " length : " + b.length());
        System.out.println("x : ");
        System.out.println("************************+++++++++++******************************");


        System.out.println("***************************************************************");

    }

    public boolean isSocialSecurityNumber(String personNumber)
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
            System.out.print( "[" + temp + ", splitSum : " + splitSum + "]");
        }
        System.out.println("");
        int checkSum = getSum(valueHolder);
        checkSum = 10 - modulo(checkSum, 10) ;
        checkSum = modulo(checkSum, 10);
        System.out.println("[checkSum : " + checkSum + ", personNumberCheck : " + personNumberCheck + "]");
        return checkSum == personNumberCheck;

    }

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

    public static int getMask(int index)
    {
        final int TEMP = -1;
        double t = Math.pow(TEMP, index) + 1;
        t = (t / 2) + 1;
        return (int) t;
    }

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



    public static int getSum(int[] array) {
        return Arrays.stream(array).sum();
    }

    public static int modulo(int numerator, int denominator )
    {
        return numerator % denominator ;
    }


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
