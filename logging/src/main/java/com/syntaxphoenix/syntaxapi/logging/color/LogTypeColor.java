package com.syntaxphoenix.syntaxapi.logging.color;

import java.awt.Color;

public class LogTypeColor extends LogType {
	
	public static final LogTypeColor DEFAULT = new LogTypeColor("info", "Info", ColorTools.hex2rgb("#474747"));
	
	public static final ColorProcessor PROCESSOR = (flag, type) -> type.asColorString(flag);
	
	/*
	 * 
	 */
	
	private Color color;
	
	/*
	 * 
	 * 
	 * 
	 */

	public LogTypeColor(String id) {
		this(id, Color.BLACK);
	}

	public LogTypeColor(String id, Color color) {
		super(id);
		this.color = color;
	}

	public LogTypeColor(String id, String name, Color color) {
		super(id, name);
		this.color = color;
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public void setColor(Color color) {
		this.color = color == null ? this.color : color;
	}
	
	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public ColorProcessor getColorProcessor() {
		return PROCESSOR;
	}
	
	@Override
	public Color asColor() {
		return color;
	}

	@Override
	public String asColorString() {
		return ColorTools.toAnsiColor(asColor());
	}

	@Override
	public String asColorString(boolean stream) {
		return stream ? asColorString() : "";
	}
	
}
