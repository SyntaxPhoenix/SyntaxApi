package com.syntaxphoenix.syntaxapi.logging;

public enum LogType {
	
	INFO, WARNING, ERROR, DEBUG;
	
	public String id() {
		return name().toLowerCase();
	}

}
