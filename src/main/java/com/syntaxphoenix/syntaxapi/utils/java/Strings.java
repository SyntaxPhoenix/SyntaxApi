package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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
		String st = "";
		for (int v = 0; v < array.length; v++) {
			String s = array[v];
			if ((v + 1) == array.length) {
				st = st + s;
			} else {
				st = st + s + adder;
			}
		}
		return st;
	}

	public static String toString(List<String> list) {
		return toString(list, " ");
	}

	public static String toString(List<String> list, String adder) {
		String s = "";
		for (int v = 0; v < list.size(); v++) {
			if (v + 1 == list.size()) {
				s = s + list.get(v);
			} else {
				s = s + list.get(v) + adder;
			}
		}
		return s;
	}

	public static String changeBool(String inString) {
		if (inString.equalsIgnoreCase("FALSE")) {
			return "true";
		} else if (inString.equalsIgnoreCase("TRUE")) {
			return "false";
		}
		return inString;
	}

	public static UUID toUUID(String id) {
		return UUID.fromString(String.valueOf(id.substring(0, 8)) + "-" + id.substring(8, 12) + "-"
				+ id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
	}

	public static int count(String msg, Character ch) {
		int count = 0;
		char[] chars = msg.toCharArray();
		for (char c : chars) {
			if (ch.equals(c)) {
				count++;
			}
		}
		return count;
	}

	public static boolean isBoolean(String msg) {
		if (msg.equalsIgnoreCase("true") || msg.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}
	
	public static String upFirstLetter(String input) {
		String first = (input = input.toLowerCase()).substring(0, 1);
		return input.replaceFirst(first, first.toUpperCase());
	}

	public static String remFirstMinus(String str) {
		if(str.startsWith("-")) {
			str = str.replaceFirst("-", "");
		}
		return str;
	}

}
