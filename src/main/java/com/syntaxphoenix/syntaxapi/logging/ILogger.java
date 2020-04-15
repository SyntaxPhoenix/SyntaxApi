package com.syntaxphoenix.syntaxapi.logging;

public interface ILogger {
	
	public void setState(LoggerState state);
	
	public LoggerState getState();
	
	public void log(String message);
	
	public void log(LogType type, String message);
	
	public void log(String typeId, String message);
	
	public void log(String... messages);
	
	public void log(LogType type, String... messages);
	
	public void log(String typeId, String... messages);
	
	public void log(Throwable throwable);
	
	public void log(LogType type, Throwable throwable);
	
	public void log(String typeId, Throwable throwable);
	
	public void setThreadName(String name);
	
	public String getThreadName();
	
}
