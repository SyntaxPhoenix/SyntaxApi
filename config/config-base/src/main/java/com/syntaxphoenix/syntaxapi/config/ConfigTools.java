package com.syntaxphoenix.syntaxapi.config;

import java.util.ArrayList;

/**
 * @author Lauriichen
 *
 */
public class ConfigTools {

	public static String[] getKeys(String input) {
		if (!input.contains(".")) {
			return new String[] {
					input
			};
		}
		return input.split("\\.");
	}

	public static String[] getNextKeys(String[] input) {
		if (input.length == 1) {
			return new String[0];
		} else {
			String[] output = new String[input.length - 1];
			for (int x = 1; x < input.length; x++) {
				output[x - 1] = input[x];
			}
			return output;
		}
	}

	public static String getLastKey(String[] input) {
		if (input.length != 0) {
			if (input.length == 1) {
				return input[0];
			}
			return input[input.length - 1];
		}
		return "";
	}

	public static String[] getKeysWithout(String[] input, String denied) {
		ArrayList<String> output = new ArrayList<>();
		for (int x = 0; x < input.length; x++) {
			if (!input[x].equals(denied)) {
				output.add(input[x]);
			}
		}
		return output.toArray(new String[0]);
	}

}
