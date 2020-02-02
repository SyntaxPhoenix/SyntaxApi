package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import com.syntaxphoenix.syntaxapi.utils.java.lang.StringBuilder;

public class Strings {

	public static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d*");
	public static final Pattern DECIMAL_PATTERN = Pattern.compile("-?\\d*\\.\\d*");

	public static boolean isNumeric(String msg) {
		return NUMBER_PATTERN.matcher(msg).matches();
	}
	
	public static boolean isDecimal(String msg) {
		return DECIMAL_PATTERN.matcher(msg).matches();
	}

	public static String toString(Collection<String> collection) {
		return toString(collection.toArray(new String[0]));
	}

	public static String toString(Collection<String> collection, String adder) {
		return toString(collection.toArray(new String[0]), adder);
	}

	public static String toString(String[] array) {
		return toString(array, " ");
	}

	public static String toString(String[] array, String adder) {
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < array.length; index++) {
			builder.append(array[index]);
			if ((index + 1) != array.length) {
				builder.append(adder);
			}
		}
		return builder.toStringClear();
	}

	public static String toString(List<String> list) {
		return toString(list, " ");
	}

	public static String toString(List<String> list, String adder) {
		StringBuilder builder = new StringBuilder();
		int size = list.size();
		for (int index = 0; index < size; index++) {
			builder.append(list.get(index));
			if ((index + 1) != size) {
				builder.append(adder);
			}
		}
		return builder.toStringClear();
	}

	public static String changeBool(String input) {
		if(isBoolean(input)) {
			return input.equalsIgnoreCase("false") ? "true" : "false";
		}
		return input;
	}

	public static UUID toUUID(String id) {
		return UUID.fromString(String.valueOf(id.substring(0, 8)) + "-" + id.substring(8, 12) + "-"
				+ id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
	}

	public static int count(String message, Character toCount) {
		int count = 0;
		char[] chars = message.toCharArray();
		for (char character : chars) {
			if (toCount.equals(character)) {
				count++;
			}
		}
		return count;
	}

	public static boolean isBoolean(String input) {
		if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}
	
	public static String firstLetterToUpperCase(String string) {
		String letter = (string = string.toLowerCase()).substring(0, 1);
		return string.replaceFirst(letter, letter.toUpperCase());
	}

	public static String removeFirstMinus(String string) {
		if(string.startsWith("-")) {
			string = string.replaceFirst("-", "");
		}
		return string;
	}

}
