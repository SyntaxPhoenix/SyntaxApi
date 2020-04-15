package com.syntaxphoenix.syntaxapi.logging;

import java.util.function.BiConsumer;

import com.syntaxphoenix.syntaxapi.logging.color.LogType;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;

public interface ILogger {
	
	public LogTypeMap getTypeMap();
	
	public ILogger setCustom(BiConsumer<Boolean, String> custom);
	
	public BiConsumer<Boolean, String> getCustom();
	
	public ILogger setType(LogType type);
	
	public LogType getType(String typeId);
	
	public ILogger setState(LoggerState state);
	
	public LoggerState getState();
	
	public ILogger setThreadName(String name);
	
	public String getThreadName();
	
	public ILogger setColored(boolean color);
	
	public boolean isColored();
	
	public void log(String message);
	
	public void log(LogTypeId type, String message);
	
	public void log(String typeId, String message);
	
	public void log(String... messages);
	
	public void log(LogTypeId type, String... messages);
	
	public void log(String typeId, String... messages);
	
	public void log(Throwable throwable);
	
	public void log(LogTypeId type, Throwable throwable);
	
	public void log(String typeId, Throwable throwable);
	
}
