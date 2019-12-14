package com.syntaxphoenix.syntaxapi.logging;

import java.io.PrintStream;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.logging.color.ColorMap;
import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;
import com.syntaxphoenix.syntaxapi.logging.color.LogColorType;
import com.syntaxphoenix.syntaxapi.utils.java.Times;

public class SynLogger {

	public static final String DEFAULT_FORMAT = "[%date% / %time%][%type% - %thread%] %message%";

	/*
	 * 
	 */

	private final PrintStream output;
	private final String format;
	private ColorMap colorMap = new ColorMap();

	public SynLogger() {
		this(System.out);
	}

	public SynLogger(String format) {
		this(System.out, format);
	}

	public SynLogger(PrintStream stream) {
		this(stream, DEFAULT_FORMAT);
	}

	public SynLogger(PrintStream stream, String format) {
		this.format = format;
		this.output = stream;
		setDefaultColors();
	}

	public void setDefaultColors() {
		colorMap.override(new LogColorType("debug", ColorTools.hex2rgb("#F000FF")));
		colorMap.override(new LogColorType("info", ColorTools.hex2rgb("#2FE4E7")));
		colorMap.override(new LogColorType("warning", ColorTools.hex2rgb("#E89102")));
		colorMap.override(new LogColorType("error", ColorTools.hex2rgb("#FF0000")));
	}

	/*
	 * 
	 * 
	 * 
	 */
	
	public void log(String message, String typeId) {
		log(message, getType(typeId));
	}

	public void log(String message, LogColorType type) {
		println(type.toAnsiColor() + format.replace("%date%", Times.getDate(".")).replace("%time%", Times.getTime(":"))
				.replace("%thread%", Thread.currentThread().getName()).replace("%type%", type.getName())
				.replace("%message%", message));
	}

	public void println(String message, String color) {
		println(color + message);
	}

	public void println(String message) {
		output.println(message);
	}

	public void print(String message, String color) {
		print(getType(color).toAnsiColor() + message);
	}

	public void print(String message) {
		output.print(message);
	}

	/*
	 * 
	 * 
	 * 
	 */

	public ColorMap getColorMap() {
		return colorMap;
	}
	
	public LogColorType getType(String typeId) {
		Optional<LogColorType> option = colorMap.tryGetById(typeId);
		return option.isPresent() ? option.get() : LogColorType.DEFAULT;
	}

}
