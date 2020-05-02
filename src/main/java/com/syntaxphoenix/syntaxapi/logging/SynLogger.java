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
	public SynLogger close() {
		if(stream != null) {
			stream.close();
			stream = null;
		}
		return this;
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

	public SynLogger setStream(PrintStream stream) {
		this.stream = stream;
		return this;
	}

	public PrintStream getStream() {
		return stream;
	}

	public SynLogger setFormat(String format) {
		this.format = format;
		return this;
	}

	public String getFormat() {
		return format;
	}

	/*
	 * 
	 */

	public SynLogger setDefaultTypes() {
		setType("debug", "#F000FF");
		setType("info", "#2FE4E7");
		setType("warning", "#E89102");
		setType("error", "#FF0000");
		return this;
	}

	public SynLogger setType(String name, String hex) {
		return setType(name, ColorTools.hex2rgb(hex));
	}

	public SynLogger setType(String name, Color color) {
		return setType(new LogTypeColor(name, color));
	}

	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public SynLogger log(String message) {
		return log(LogTypeId.INFO, message);
	}

	@Override
	public SynLogger log(LogTypeId type, String message) {
		return log(type.id(), message);
	}

	@Override
	public SynLogger log(String typeId, String message) {
		return log(getType(typeId), message);
	}

	public SynLogger log(LogType type, String message) {
		return println(type, format.replace("%date%", Times.getDate(".")).replace("%time%", Times.getTime(":"))
				.replace("%thread%", getThreadName()).replace("%type%", type.getName()).replace("%message%", message));
	}

	/*
	 * 
	 */

	@Override
	public SynLogger log(String... messages) {
		return log(LogTypeId.INFO, messages);
	}

	@Override
	public SynLogger log(LogTypeId type, String... messages) {
		return log(type.id(), messages);
	}

	@Override
	public SynLogger log(String typeId, String... messages) {
		return log(getType(typeId), messages);
	}

	public SynLogger log(LogType type, String... messages) {
		if (messages == null || messages.length == 0) {
			return this;
		}
		for (String message : messages) {
			log(type, message);
		}
		return this;
	}

	/*
	 * 
	 */

	@Override
	public SynLogger log(Throwable throwable) {
		return log(LogTypeId.ERROR, throwable);
	}

	@Override
	public SynLogger log(LogTypeId type, Throwable throwable) {
		return log(type.id(), throwable);
	}

	@Override
	public SynLogger log(String typeId, Throwable throwable) {
		return log(getType(typeId), throwable);
	}

	public SynLogger log(LogType type, Throwable throwable) {
		return log(type, Exceptions.stackTraceToStringArray(throwable));
	}

	/*
	 * 
	 */

	public SynLogger println(LogType type, String message) {
		if(!colored) {
			return println(message);
		}
		if (state.useCustom())
			if (custom != null)
				custom.accept(true, type.asColorString(false) + message);
		if (state.useStream())
			if(stream != null)
				stream.println(type.asColorString(true) + message);
		return this;
	}

	public SynLogger println(String message) {
		if (state.useCustom())
			if (custom != null)
				custom.accept(true, message);
		if (state.useStream())
			if(stream != null)
				stream.println(message);
		return this;
	}

	public SynLogger print(LogType type, String message) {
		if(!colored) {
			return print(message);
		}
		if (state.useCustom())
			if (custom != null)
				custom.accept(false, type.asColorString(false) + message);
		if (state.useStream())
			if(stream != null)
				stream.print(type.asColorString(true) + message);
		return this;
	}

	public SynLogger print(String message) {
		if (state.useCustom())
			if (custom != null)
				custom.accept(false, message);
		if (state.useStream())
			if(stream != null)
				stream.print(message);
		return this;
	}

}
