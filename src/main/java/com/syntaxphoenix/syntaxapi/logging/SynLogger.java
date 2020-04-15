package com.syntaxphoenix.syntaxapi.logging;

import java.awt.Color;
import java.io.PrintStream;
import java.util.Optional;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.logging.color.ColorMap;
import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;
import com.syntaxphoenix.syntaxapi.logging.color.LogColorType;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Times;

public final class SynLogger implements ILogger {

	public static final String DEFAULT_FORMAT = "[%date% / %time%][%thread% => %type%] %message%";

	/*
	 * 
	 */

	private ColorMap colorMap = new ColorMap();
	private boolean colored;
	private String format;

	private Consumer<String> console;
	private PrintStream file;

	private LoggerState state = LoggerState.FILE;
	private String overrideThread;

	public SynLogger() {
		this(System.out);
	}

	public SynLogger(LoggerState state) {
		this(System.out, state);
	}

	public SynLogger(String format) {
		this(System.out, format);
	}

	public SynLogger(PrintStream file) {
		this(file, DEFAULT_FORMAT);
	}

	public SynLogger(String format, LoggerState state) {
		this(System.out, format, state);
	}

	public SynLogger(PrintStream file, LoggerState state) {
		this(file, DEFAULT_FORMAT, state);
	}

	public SynLogger(PrintStream file, String format) {
		setColored(true);
		setFormat(format);
		setStream(file);
		setDefaultColors();
	}

	public SynLogger(PrintStream file, String format, LoggerState state) {
		setColored(true);
		setFormat(format);
		setStream(file);
		setState(state);
		setDefaultColors();
	}

	/*
	 * 
	 */

	public void setThreadName(String name) {
		this.overrideThread = name;
	}

	public void setState(LoggerState state) {
		this.state = state;
	}

	public void setColored(boolean colored) {
		this.colored = colored;
	}

	public void setStream(PrintStream stream) {
		this.file = stream;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isColored() {
		return colored;
	}

	@Override
	public String getThreadName() {
		if (overrideThread == null)
			return Thread.currentThread().getName();
		String name = overrideThread;
		overrideThread = null;
		return name;
	}

	@Override
	public LoggerState getState() {
		return state;
	}

	public PrintStream getStream() {
		return file;
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

	@Override
	public void log(String message) {
		log(LogType.INFO, message);
	}

	@Override
	public void log(LogType type, String message) {
		log(type.id(), message);
	}

	@Override
	public void log(String typeId, String message) {
		log(getType(typeId), message);
	}

	public void log(LogColorType type, String message) {
		println(type.toAnsiColor(), format.replace("%date%", Times.getDate(".")).replace("%time%", Times.getTime(":"))
				.replace("%thread%", getThreadName()).replace("%type%", type.getName()).replace("%message%", message));
	}

	/*
	 * 
	 */

	@Override
	public void log(String... messages) {
		log(LogType.INFO, messages);
	}

	@Override
	public void log(LogType type, String... messages) {
		log(type.id(), messages);
	}

	@Override
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

	@Override
	public void log(Throwable throwable) {
		log(LogType.ERROR, throwable);
	}

	@Override
	public void log(LogType type, Throwable throwable) {
		log(type.id(), throwable);
	}

	@Override
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
		println((colored ? color : "") + message);
	}

	public void println(String message) {
		if (state.logConsole())
			if (console != null)
				console.accept(message);
		if (state.logFile())
			file.println(message);
	}

	public void print(String color, String message) {
		print((colored ? color : "") + message);
	}

	public void print(String message) {
		if (state.logConsole())
			if (console != null)
				console.accept(message);
		if (state.logFile())
			file.print(message);
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
