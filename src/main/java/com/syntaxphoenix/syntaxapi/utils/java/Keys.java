package com.syntaxphoenix.syntaxapi.utils.java;

import com.syntaxphoenix.syntaxapi.random.NumberGeneratorType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;
import com.syntaxphoenix.syntaxapi.utils.java.lang.StringBuilder;

public class Keys {

	public static final Keys DEFAULT = new Keys();

	public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERS = "0123456789";

	public static final int DEFAULT_STRING_LENGTH = ALPHABET.length();
	public static final int DEFAULT_INT_LENGTH = ((Integer.MAX_VALUE + "").length() - 1);
	public static final int DEFAULT_LONG_LENGTH = ((Long.MAX_VALUE + "").length() - 1);

	/*
	 * 
	 */

	private final RandomNumberGenerator RANDOM;

	/*
	 * 
	 */

	public Keys() {
		this(System.currentTimeMillis());
	}

	public Keys(long seed) {
		this(NumberGeneratorType.MURMUR.create(seed));
	}

	public Keys(RandomNumberGenerator generator) {
		RANDOM = generator;
	}

	/*
	 * 
	 */

	public final String makeKey() {
		return makeKey(DEFAULT_STRING_LENGTH);
	}

	public String makeKey(int length) {
		StringBuilder builder = new StringBuilder();
		for (int current = 0; current < length; ++current)
			builder.append(ALPHABET.charAt(RANDOM.nextInt(DEFAULT_STRING_LENGTH)));
		return builder.toStringClear();
	}

	/*
	 * 
	 */

	public Integer makeInt() {
		return makeInt(DEFAULT_INT_LENGTH);
	}

	public Integer makeInt(int length) {
		StringBuilder builder = new StringBuilder();
		for (int current = 0; current < length; ++current)
			builder.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
		return Integer.valueOf(builder.toStringClear());
	}

	/*
	 * 
	 */

	public Long makeLong() {
		return makeLong(DEFAULT_LONG_LENGTH);
	}

	public Long makeLong(int length) {
		StringBuilder builder = new StringBuilder();
		for (int current = 0; current < length; ++current)
			builder.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
		return Long.valueOf(builder.toStringClear());
	}

	//
	//
	// Static keys stuff
	//
	// To prevent old methods from breaking.
	//
	//

	public static final String generateKey() {
		return DEFAULT.makeKey();
	}

	public static final String generateKey(int length) {
		return DEFAULT.makeKey(length);
	}

	//
	//
	//

	public static final Integer generateInt() {
		return DEFAULT.makeInt();
	}

	public static final Integer generateInt(int length) {
		return DEFAULT.makeInt(length);
	}

	//
	//
	//

	public static final Long generateLong() {
		return DEFAULT.makeLong();
	}

	public static final Long generateLong(int length) {
		return DEFAULT.makeLong(length);
	}

}
