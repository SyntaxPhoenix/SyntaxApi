package com.syntaxphoenix.syntaxapi.test.utils;

/**
 * @author Lauriichen
 *
 */
public interface Printer {
	
	public static void prints(Object input) {
		System.out.println(input);
	}
	
	public static void spaces() {
		prints("------------------------------------------------------------------");
	}
	
	public default void print(Object input) {
		System.out.println(input);
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
		if(input.length == 0) {
			print("[]");
			return;
		}
		if(pretty) {
			for(int index = 0; index < input.length; index++) {
				print(add.replace("%index%", index + "") + input[index]);
			}
			return;
		} else {
			String output = "[";
			for(String in : input) {
				output += in + add;
			}
			print(output.substring(0, output.length() - add.length()) + "]");
			return;
		}
	}

}
