package com.syntaxphoenix.syntaxapi.logging.color;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public abstract class LogType {
	
	private final String id;
	private String name;
	
	/*
	 * 
	 * 
	 * 
	 */

	public LogType(String id) {
		this(id, Strings.firstLetterToUpperCase(id));
	}

	public LogType(String id, String name) {
		this.id = id.toLowerCase();
		this.name = name;
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public void setName(String name) {
		this.name = (name == null || name.isEmpty()) ? this.name : name;
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public abstract ColorProcessor getColorProcessor();
	
	public abstract String asColorString();
	
	public abstract String asColorString(boolean stream);
	
}
