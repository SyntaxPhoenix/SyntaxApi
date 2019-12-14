package com.syntaxphoenix.syntaxapi.logging.color;

import java.awt.Color;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class LogColorType {
	
	public static final LogColorType DEFAULT = new LogColorType("default", "Info", ColorTools.hex2rgb("#474747"));
	
	/*
	 * 
	 */
	
	private final String id;
	private String name;
	private Color color;
	
	/*
	 * 
	 * 
	 * 
	 */

	public LogColorType(String id) {
		this(id, Color.BLACK);
	}

	public LogColorType(String id, Color color) {
		this(id, Strings.upFirstLetter(id), color);
	}

	public LogColorType(String id, String name, Color color) {
		this.id = id.toLowerCase();
		this.name = name;
		this.color = color;
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public void setName(String name) {
		this.name = (name == null || name.isEmpty()) ? this.name : name;
	}
	
	public void setColor(Color color) {
		this.color = color == null ? this.color : color;
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
	
	public Color getColor() {
		return color;
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public String toAnsiColor() {
		return ColorTools.toAnsiColor(getColor());
	}
	
}
