package com.syntaxphoenix.syntaxapi.logging;

import java.awt.Color;
import java.io.PrintStream;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;
import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;
import com.syntaxphoenix.syntaxapi.logging.color.LogType;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeColor;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Times;

public final class SynLogger implements ILogger {

	public static final String DEFAULT_FORMAT = "[%date% / %time%][%thread% => %type%] %message%";

	/*
	 * 
	 */

	private LogTypeMap typeMap = new LogTypeMap();
	private boolean colored;
	private String format;
	
	private BiConsumer<Boolean, String> custom;
	private PrintStream stream;

	private LoggerState state = LoggerState.STREAM;
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

	public SynLogger(PrintStream stream, LoggerState state) {
		this(stream, DEFAULT_FORMAT, state);
	}

	public SynLogger(PrintStream stream, String format) {
		setColored(true);
		setFormat(format);
		setStream(stream);
		setDefaultTypes();
	}

	public SynLogger(PrintStream stream, String format, LoggerState state) {
		setColored(true);
		setFormat(format);
		setStream(stream);
		setState(state);
		setDefaultTypes();
	}

	/*
	 * 
	 */

	@Override
	public SynLogger setThreadName(String name) {
		this.overrideThread = name;
		return this;
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
	public SynLogger setCustom(BiConsumer<Boolean, String> custom) {
		this.custom = custom;
		return this;
	}

	@Override
	public BiConsumer<Boolean, String> getCustom() {
		return custom;
	}

	@Override
	public SynLogger setState(LoggerState state) {
		this.state = state;
		return this;
	}

	@Override
	public LoggerState getState() {
		return state;
	}

	@Override
	public SynLogger setColored(boolean colored) {
		this.colored = colored;
		return this;
	}

	@Override
	public boolean isColored() {
		return colored;
	}

	@Override
	public SynLogger setType(LogType type) {
		typeMap.override(type);
		return this;
	}

	@Override
	public LogType getType(String typeId) {
		Optional<LogType> option = typeMap.tryGetById(typeId);
		return option.isPresent() ? option.get() : LogTypeColor.DEFAULT;
	}

	@Override
	public LogTypeMap getTypeMap() {
		return typeMap;
	}
	
	/*
	 * 
	 */

	public void setStream(PrintStream stream) {
		this.stream = stream;
	}

	public PrintStream getStream() {
		return stream;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	/*
	 * 
	 */

	public void setDefaultTypes() {
		setType("debug", "#F000FF");
		setType("info", "#2FE4E7");
		setType("warning", "#E89102");
		setType("error", "#FF0000");
	}

	public void setType(String name, String hex) {
		setType(name, ColorTools.hex2rgb(hex));
	}

	public void setType(String name, Color color) {
		setType(new LogTypeColor(name, color));
	}

	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public void log(String message) {
		log(LogTypeId.INFO, message);
	}

	@Override
	public void log(LogTypeId type, String message) {
		log(type.id(), message);
	}

	@Override
	public void log(String typeId, String message) {
		log(getType(typeId), message);
	}

	public void log(LogType type, String message) {
		println(type, format.replace("%date%", Times.getDate(".")).replace("%time%", Times.getTime(":"))
				.replace("%thread%", getThreadName()).replace("%type%", type.getName()).replace("%message%", message));
	}

	/*
	 * 
	 */

	@Override
	public void log(String... messages) {
		log(LogTypeId.INFO, messages);
	}

	@Override
	public void log(LogTypeId type, String... messages) {
		log(type.id(), messages);
	}

	@Override
	public void log(String typeId, String... messages) {
		log(getType(typeId), messages);
	}

	public void log(LogType type, String... messages) {
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
		log(LogTypeId.ERROR, throwable);
	}

	@Override
	public void log(LogTypeId type, Throwable throwable) {
		log(type.id(), throwable);
	}

	@Override
	public void log(String typeId, Throwable throwable) {
		log(getType(typeId), throwable);
	}

	public void log(LogType type, Throwable throwable) {
		log(type, Exceptions.stackTraceToStringArray(throwable));
	}

	/*
	 * 
	 */

	public void println(LogType type, String message) {
		if(!colored) {
			print(message);
			return;
		}
		if (state.useCustom())
			if (custom != null)
				custom.accept(true, type.asColorString(false) + message);
		if (state.useStream())
			if(stream != null)
				stream.println(type.asColorString(true) + message);
	}

	public void println(String message) {
		if (state.useCustom())
			if (custom != null)
				custom.accept(true, message);
		if (state.useStream())
			if(stream != null)
				stream.println(message);
	}

	public void print(LogType type, String message) {
		if(!colored) {
			print(message);
			return;
		}
		if (state.useCustom())
			if (custom != null)
				custom.accept(false, type.asColorString(false) + message);
		if (state.useStream())
			if(stream != null)
				stream.print(type.asColorString(true) + message);
	}

	public void print(String message) {
		if (state.useCustom())
			if (custom != null)
				custom.accept(false, message);
		if (state.useStream())
			if(stream != null)
				stream.print(message);
	}

}
