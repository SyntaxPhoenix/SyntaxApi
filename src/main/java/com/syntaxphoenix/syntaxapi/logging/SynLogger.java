package com.syntaxphoenix.syntaxapi.logging;

import java.awt.Color;
import java.io.PrintStream;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.logging.color.ColorMap;
import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;
import com.syntaxphoenix.syntaxapi.logging.color.LogColorType;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Times;

public class SynLogger {

	public static final String DEFAULT_FORMAT = "[%date% / %time%][%thread% => %type%] %message%";

	/*
	 * 
	 */

	private ColorMap colorMap = new ColorMap();
	private PrintStream stream;
	private boolean colored;
	private String format;

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
		setColored(true);
		setFormat(format);
		setStream(stream);
		setDefaultColors();
	}
	
	/*
	 * 
	 */
	
	public void setColored(boolean colored) {
		this.colored = colored;
	}
	
	public void setStream(PrintStream stream) {
		this.stream = stream;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public boolean isColored() {
		return colored;
	}
	
	public PrintStream getStream() {
		return stream;
	}
	
	public String getFormat() {
		return format;
	}
	
	/*
	 * 
	 */

	public void setDefaultColors() {
		setColor("debug", "#F000FF");
		setColor("info", "#2FE4E7");
		setColor("warning", "#E89102");
		setColor("error", "#FF0000");
	}
	
	public void setColor(String name, String hex) {
		setColor(name, ColorTools.hex2rgb(hex));
	}
	
	public void setColor(String name, Color color) {
		setColor(new LogColorType(name, color));
	}
	
	public void setColor(LogColorType type) {
		colorMap.override(type);
	}

	/*
	 * 
	 * 
	 * 
	 */

	public void log(String message) {
		log(LogType.INFO, message);
	}

	public void log(LogType type, String message) {
		log(type.id(), message);
	}

	public void log(String typeId, String message) {
		log(getType(typeId), message);
	}

	public void log(LogColorType type, String message) {
		println(type.toAnsiColor(), format.replace("%date%", Times.getDate(".")).replace("%time%", Times.getTime(":"))
				.replace("%thread%", Thread.currentThread().getName()).replace("%type%", type.getName())
				.replace("%message%", message));
	}

	/*
	 * 
	 */

	public void log(String... messages) {
		log(LogType.INFO, messages);
	}

	public void log(LogType type, String... messages) {
		log(type.id(), messages);
	}

	public void log(String typeId, String... messages) {
		log(getType(typeId), messages);
	}

	public void log(LogColorType type, String... messages) {
		if (messages == null || messages.length == 0) {
			return;
		}
		for (String message : messages) {
			log(type, message);
		}
	}

	/*
	 * 
	 */

	public void log(Throwable throwable) {
		log(LogType.ERROR, throwable);
	}

	public void log(LogType type, Throwable throwable) {
		log(type.id(), throwable);
	}

	public void log(String typeId, Throwable throwable) {
		log(getType(typeId), throwable);
	}

	public void log(LogColorType type, Throwable throwable) {
		log(type, Exceptions.stackTraceToStringArray(throwable));
	}

	/*
	 * 
	 */

	public void println(String color, String message) {
		println((colored ? color : "") +  message);
	}

	public void println(String message) {
		stream.println(message);
	}

	public void print(String color, String message) {
		print((colored ? color : "") +  message);
	}

	public void print(String message) {
		stream.print(message);
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
