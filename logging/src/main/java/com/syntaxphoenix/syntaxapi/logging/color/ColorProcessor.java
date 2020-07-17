package com.syntaxphoenix.syntaxapi.logging.color;

@FunctionalInterface
public interface ColorProcessor {
	
	public String process(boolean stream, LogType type);
	
}
