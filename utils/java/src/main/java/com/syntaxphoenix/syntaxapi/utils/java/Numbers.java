package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.regex.Pattern;

public class Numbers {

    public static final Pattern ODD_NUMBER = Pattern.compile(".*([13579])$");

    public static boolean isOddNumber(Number input) {
        return ODD_NUMBER.matcher(input.toString()).matches();
    }

}
