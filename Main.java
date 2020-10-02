package com.company;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.io.*;
import java.util.logging.*;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

public class Main {

    private  final static int log = 100;
    private final static  Logger logr = Logger.getLogger("Benlog");
    private final static  Logger logrT = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static void setupLogger(){
        // write your code he
        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL); // Set all level on

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logr.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("myLogger.log");
            fh.setLevel(Level.FINE);
            logr.addHandler(fh);
        }
        catch (IOException e)
        {
            logr.log(Level.SEVERE, "File logger is not working" , e);
        }

    }

    public static void main(String[] args)  {

        // SETUP logger
        Main.setupLogger();

        Logger root = Logger.getLogger(""); // This the Root Looger :
        System.out.println("Ben Makamba");
        System.out.println(log);
        logr.log(Level.INFO, "AAAAAAAAAAAAAAA");
        logr.log(Level.SEVERE, "BBBBBBBBBBBBBBB");

        Test.test();

    }

    public int getId(int element){
        return element;
    }

}

class Test{
    private final static  Logger logr = Logger.getLogger("Benlog");
    static void test()
    {
        logr.log(Level.SEVERE, "I am from TEst ! You are CRAZY");
    }
}
