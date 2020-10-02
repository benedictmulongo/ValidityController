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
        ArrayList<Validatable<String>> g = new ArrayList<>();
        g.add(new NotNullValidator<>(1));
        g.add(new NotNullValidator<>(2));
        g.add(new NotNullValidator<>(3));
        System.out.println("************************ Test CRazy ******************************");
        String personNumberT = "199403110050";
        int temp = -1;
        double tempPower = Math.pow(temp, 2);
        for(int i = 0; i < 10; i++)
        {
            double t = Math.pow(temp, i) + 1;
            t = (t / 2) + 1;
            int t_bac = (int) t;
            System.out.println("[index : " + i + " , " + t_bac + " ]");
            //System.out.println("[index : " + i + " , " + Math.pow(temp, i) + " ]");
        }

        System.out.println("***************************************************************");
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
}
