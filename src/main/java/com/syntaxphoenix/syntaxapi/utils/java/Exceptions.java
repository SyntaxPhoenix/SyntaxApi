package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exceptions {

	public static String[] toStringArray(Throwable cause) {
		List<String> out = new ArrayList<>();
		for (StackTraceElement stack : cause.getStackTrace()) {
			String err = "Error Type: " + cause.getClass().getSimpleName();
			String msg = "Error Message: " + cause.getLocalizedMessage();
			String clas = "Class: " + stack.getClassName();
			String file = "File: " + stack.getFileName();
			String method = "Method: " + stack.getMethodName();
			String line = "Line: " + stack.getLineNumber();
			String next = "\r\n";
			out.add(next + err + next + msg + next + next + clas + next + file + next + method + next + line);
		}
		Collections.reverse(out);
		return out.toArray(new String[0]);
	}

	public static String getError(Throwable cause) {
		List<String> out = new ArrayList<>();
		for (StackTraceElement stack : cause.getStackTrace()) {
			String err = "Error Type: " + cause.getClass().getSimpleName();
			String msg = "Error Message: " + cause.getLocalizedMessage();
			String clas = "Class: " + stack.getClassName();
			String file = "File: " + stack.getFileName();
			String method = "Method: " + stack.getMethodName();
			String line = "Line: " + stack.getLineNumber();
			String next = "\r\n";
			out.add(next + err + next + msg + next + next + clas + next + file + next + method + next + line);
		}
		return out.get(0);
	}

}
