package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.syntaxphoenix.syntaxapi.utils.java.lang.StringBuilder;

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
	
	public static String[] stackTraceToStringArray(Throwable throwable) {
		return stackTraceToString(throwable).split("\n");
	}
	
	public static String stackTraceToString(Throwable throwable) {
		
		StringBuilder builder = new StringBuilder();
		stackTraceToBuilder(throwable, builder, false);
		return builder.toString();
				
	}
	
	private static void stackTraceToBuilder(Throwable throwable, StringBuilder builder, boolean cause) {

		StringBuilder stack = new StringBuilder();
		
		if(cause) {
			stack.append('\n');
			stack.append("Caused by: ");
		}
		stack.append(throwable.getClass().getName());
		stack.append(": ");
		stack.append(throwable.getLocalizedMessage());
		builder.append(stack.toStringClear());

		StackTraceElement[] stackTrace = throwable.getStackTrace();
		 
		for(StackTraceElement element : stackTrace) {
			
			String fileName = element.getFileName();
			stack.append("\n");
			stack.append('	');
			stack.append("at ");

			stack.append(element.getClassName());
			stack.append('.');
			stack.append(element.getMethodName());
			stack.append('(');
			if(fileName == null) {
				stack.append("Unknown Source");
			} else {
				stack.append(fileName);
				stack.append(':');
				stack.append(Integer.toString(element.getLineNumber()));
			}
			stack.append(')');
			builder.append(stack.toStringClear());
		}
		
		Throwable caused = throwable.getCause();
		if(caused != null) {
			stackTraceToBuilder(caused, builder, true);
		}
		
	}

}
