package com.syntaxphoenix.syntaxapi.utils.java;

public class Numbers {

	public static Number toPlus(Number input) {
		if (isNegative(input)) {
			return changeSign(input);
		}
		return input;
	}

	public static Number changeSign(Number input) {
		return input.doubleValue() * (-1);
	}

	public static boolean isOddNumber(Number input) {
		String number = input.toString();
		if (number.endsWith("0") || number.endsWith("2") || number.endsWith("4") || number.endsWith("6") || number.endsWith("8")) {
			return false;
		}
		return true;
	}

	public static boolean isNegative(Number input) {
		if (input.toString().startsWith("-")) {
			return true;
		} else {
			return false;
		}
	}

	public static double parseDouble(String string) {
		try {
			return Double.parseDouble(string);
		} catch(NumberFormatException e) {
			return 0.0;
		}
	}

}
