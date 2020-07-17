package com.syntaxphoenix.syntaxapi.utils.java.tools;

@FunctionalInterface
public interface Compare<A, B> {
	
	public boolean compare(A var1, B var2);
	
}
