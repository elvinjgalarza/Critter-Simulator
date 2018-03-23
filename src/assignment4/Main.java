package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 *
 * Elvin J. Galarza
 * ejg2298
 * 15455
 *
 * Bianca Antonio
 * bla774
 * 15510
 *
 * Slip days used: <0>
 * Spring 2018
 *
 */

import java.util.List;
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        while(true){
            int command = 0;
            int flag = 0;
            /* display the end user prompt */
            System.out.print("critters>");
            /* Create a String array that contains all of the user's input */
            String user_input = kb.nextLine();
            String[] user_input_args = user_input.split("\\s+"); /* strip the whitepsace */

            for(int i = 0; i < user_input_args.length; i++){

                /* QUIT */
                if(user_input_args[i].equals("quit")){
                    flag = 1;
                    break;
                }

                /* SHOW */
                if(user_input_args[i].equals("show")){
                    command = 1;
                    if(user_input_args.length != 1){
                        System.out.println("error processing: " + user_input);
                    }
                    else{
                        Critter.displayWorld();
                    }
                }

                /* STEP */
                if(user_input_args[i].equals("step")){
                    command = 1;
                    int numSteps = 0;
                    /* if count isn't specified */
                    if(user_input_args.length == 1){
                        numSteps = 1;
                    }
                    /* if count is specified */
                    else if(user_input_args.length == 2){
                        try{
                            numSteps = Integer.parseInt(user_input_args[1]);
                        }
                        catch(NumberFormatException nfe){
                            System.out.println("error processing: " + user_input);
                        }
                    }
                    /* if count step is typed with an error */
                    else{
                        System.out.println("error processing: " + user_input);
                    }

                    /* carry out the steps */
                    for(int j = 0; j < numSteps; j++){
                        Critter.worldTimeStep();
                    }
                }


                /* MAKE */
                if(user_input_args[i].equals("make")){
                    command = 1;
                    int numMake = 0;
                    /* if nothing was specified */
                    if(user_input_args.length == 1){
                        System.out.println("error processing: " + user_input);
                    }
                    /* if count isn't specified */
                    if(user_input_args.length == 2){
                        numMake = 1;
                    }
                    /* if count was specified */
                    if(user_input_args.length == 3){
                        try{
                            numMake = Integer.parseInt(user_input_args[2]);
                        }
                        catch(NumberFormatException nfe){
                            System.out.println("error processing: " + user_input);
                        }
                    }
                    if(user_input_args.length > 3){
                        System.out.println("error processing: " + user_input);
                    }

                    /* carry out making the Critter(s) */
                    try{
                        for(int j = 0; j < numMake; j++){
                            Critter.makeCritter(user_input_args[1]);
                        }
                    }
                    catch(InvalidCritterException ice){
                        System.out.println("error processing: " + user_input);
                    }
                }


                /* STATS */
                if(user_input_args[i].equals("stats")){
                    command = 1;
                    if(user_input_args.length == 1){
                        System.out.println("error processing: " + user_input);
                    }
                    else if(user_input_args.length == 2){
                        try{
                            List<Critter> statistics = Critter.getInstances(user_input_args[1]);
                            Class<? extends Critter> stats =
                                    Class.forName(myPackage + "." + user_input_args[1]).asSubclass(Critter.class);
                            stats.getMethod("runStats", List.class).invoke(null, statistics);
                        }
                        catch(Exception e){
                            System.out.println("error processing: " + user_input);
                        }
                    }
                    else{
                        System.out.println("error processing: " + user_input);
                    }
                }

                /* SEED */
                if(user_input_args[i].equals("seed")){
                    command = 1;
                    if(user_input_args.length == 2){
                        try{
                            Critter.setSeed(Integer.parseInt(user_input_args[1]));
                        }
                        catch(NumberFormatException nfe){
                            System.out.println("error processing: " + user_input);
                        }
                    }
                    else{
                        System.out.println("error processing: " + user_input);
                    }
                }

            }
            /* if quit flag was set, terminate program */
            if(flag == 1){
                break;
            }
            if(command == 0){
                System.out.println("invalid command: " + user_input);

            }
        }

        /* Write your code above */
        System.out.flush();

    }
}
