package com.syntaxphoenix.syntaxapi.logging;

import java.util.function.BiConsumer;

import com.syntaxphoenix.syntaxapi.logging.color.LogType;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;

public interface ILogger {
	
	public ILogger close();
	
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
	
	public ILogger log(String message);
	
	public ILogger log(LogTypeId type, String message);
	
	public ILogger log(String typeId, String message);
	
	public ILogger log(String... messages);
	
	public ILogger log(LogTypeId type, String... messages);
	
	public ILogger log(String typeId, String... messages);
	
	public ILogger log(Throwable throwable);
	
	public ILogger log(LogTypeId type, Throwable throwable);
	
	public ILogger log(String typeId, Throwable throwable);
	
}
