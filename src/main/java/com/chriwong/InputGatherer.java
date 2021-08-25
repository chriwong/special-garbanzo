package com.chriwong;

import java.io.InputStream;
import java.util.*;

public class InputGatherer {

    private final Scanner scanner;
    private final List<String> COMMANDS = Arrays.asList("SELECT", "INSERT");
    private final String BAD_INPUT = "Input was invalid, try again.";

    InputGatherer(InputStream stream) {
        this.scanner = new Scanner(stream);
    }


    /**
     * Read from input until a positive integer is entered.
     */
    public int getPositiveInt() {
        while (true) {
            try {
                int x = scanner.nextInt();
                if (x >= 0) {
                    scanner.nextLine();
                    return x;
                } else {
                    System.out.println("Input is negative, try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input not in integer format, try again.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Read from input until a valid SQL variable token is entered.
     */
    public String getSQLVariable() {
        while (true) {
            try {
                String input = scanner.nextLine();
                if (TextUtils.isValidSQLName(input)) {
                    return input;
                } else {
                    System.out.println("Input is not a valid database name, try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println(BAD_INPUT);
            }
        }
    }

    /**
     * Read from input until one of the following is entered:
     * ["INSERT", "SELECT"]
     */
    public String getSQLCommand() {
        while (true) {
            try {
                String input = scanner.nextLine();
                if (COMMANDS.contains(input)) {
                    return input;
                } else {
                    System.out.println("Input is not one of 'SELECT' or 'INSERT', try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println(BAD_INPUT);
            }
        }
    }

}
