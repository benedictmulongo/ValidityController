package com.company;

import java.io.IOException;
import java.util.logging.*;

/**
 * Test Logger
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 */
public class TestLogger {

    private  final static int log = 100;
    private final static Logger logr = Logger.getLogger("Benlog");
    private final static  Logger logrT = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static void setupLogger(){
        // write your code he
        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL); // Set all level on

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE); // Show only Severe messages
        logr.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("myLogger.log");
            fh.setLevel(Level.FINE); // Log all messages
            logr.addHandler(fh);
        }
        catch (IOException e)
        {
            logr.log(Level.SEVERE, "File logger is not working" , e);
        }
    }

    public static void main(String[] args)  {

        // SETUP logger
        TestLogger.setupLogger();

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

class Test
{
    private final static  Logger logr = Logger.getLogger("Benlog");
    static void test()
    {
        logr.log(Level.SEVERE, "I am from TEst ! You are CRAZY");
    }
}
