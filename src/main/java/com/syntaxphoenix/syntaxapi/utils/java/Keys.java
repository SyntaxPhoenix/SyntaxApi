package com.syntaxphoenix.syntaxapi.utils.java;



import java.security.SecureRandom;

public class Keys {
	
	private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static final int DEFAULT_KEY_LENGTH = ALPHABET.length();
    public static final int DEFAULT_INT_LENGTH = ((Integer.MAX_VALUE+"").length() - 1);
    public static final int DEFAULT_LONG_LENGTH = ((Long.MAX_VALUE+"").length() - 1);
    
    /*
     * 
     * String
     * 
     */
    
    public static String generateKey() {
    	return generateKey(DEFAULT_KEY_LENGTH);
    }
	
	public static String generateKey(Integer length) {
        StringBuilder builder = new StringBuilder();
        for (int current = 0; current < length; ++current) {
            builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return builder.toString();
	}
	
	/*
	 * 
	 * Integer
	 * 
	 */
	
	public static Integer generateInt() {
		return generateInt(DEFAULT_INT_LENGTH);
	}
	
	public static Integer generateInt(Integer length) {
		if(length > DEFAULT_INT_LENGTH) {
			length = DEFAULT_INT_LENGTH;
		}
        StringBuilder builder = new StringBuilder();
        for (int current = 0; current < length; ++current) {
            builder.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }
        return Integer.valueOf(builder.toString());
	}
	
	/*
	 * 
	 * Long
	 * 
	 */
	
	public static Long generateLong() {
		return generateLong(DEFAULT_LONG_LENGTH);
	}
	
	public static Long generateLong(Integer length) {
		if(length > DEFAULT_LONG_LENGTH) {
			length = DEFAULT_LONG_LENGTH;
		}
        StringBuilder builder = new StringBuilder();
        for (int current = 0; current < length; ++current) {
            builder.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }
        return Long.valueOf(builder.toString());
	}

}

