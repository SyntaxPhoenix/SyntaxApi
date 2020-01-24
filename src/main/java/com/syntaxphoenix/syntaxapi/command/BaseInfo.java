package com.syntaxphoenix.syntaxapi.command;

public abstract class BaseInfo {
	
	private final String label;
	
	public BaseInfo(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

}
