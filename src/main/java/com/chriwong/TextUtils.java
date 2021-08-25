package com.chriwong;

public class TextUtils {

    public static boolean isLowercaseLetter(char c) {
        return c > 96 && c < 123;
    }

    public static boolean isLetter(char c) {
        return (c > 64 && c < 91) || (c > 96 && c < 123);
    }

    public static boolean isValidSQLName(String name) {
        // Actual RDBMS naming restrictions could go here
        return !name.isEmpty() && isLetter(name.charAt(0));
    }
}
