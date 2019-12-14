package com.syntaxphoenix.syntaxapi.utils.java;



import java.security.SecureRandom;

public class Keys {
	
	private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int lengthInt = ((Integer.MAX_VALUE+"").length() - 1);
	
	public static String generateKey(Integer lenght) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lenght; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
	}
	
	public static Integer generateInt() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lengthInt; ++i) {
            sb.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }
        return Integer.valueOf(sb.toString());
	}

}
