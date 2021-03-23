package com.syntaxphoenix.syntaxapi.test.utils;

import com.syntaxphoenix.syntaxapi.test.SyntaxExecutor;

/**
 * @author Lauriichen
 *
 */
public interface Printer {

    /*
     * 
     */

    public static void spaces() {
        prints("------------------------------------------------------------------");
    }

    public static void prints(Object input) {
        prints(input.toString());
    }

    public static void prints(String input) {
        SyntaxExecutor.LOGGER.log(input);
    }

    public static void prints(Throwable throwable) {
        SyntaxExecutor.LOGGER.log(throwable);
    }

    /*
     * 
     */

    public default void print(Throwable throwable) {
        SyntaxExecutor.LOGGER.log(throwable);
    }

    public default void print(Object input) {
        print(input.toString());
    }

    public default void print(String input) {
        SyntaxExecutor.LOGGER.log(input);
    }

    public default void space() {
        print("------------------------------------------------------------------");
    }

    public default void print(String[] input) {
        print(input, "- ");
    }

    public default void print(String[] input, boolean pretty) {
        print(input, pretty ? "- " : ", ", pretty);
    }

    public default void print(String[] input, String add) {
        print(input, add, true);
    }

    public default void print(String[] input, String add, boolean pretty) {
        if (input.length == 0) {
            print("[]");
            return;
        }
        if (pretty) {
            for (int index = 0; index < input.length; index++) {
                print(add.replace("%index%", index + "") + input[index]);
            }
            return;
        } else {
            String output = "[";
            for (String in : input) {
                output += in + add;
            }
            print(output.substring(0, output.length() - add.length()) + "]");
            return;
        }
    }

}
