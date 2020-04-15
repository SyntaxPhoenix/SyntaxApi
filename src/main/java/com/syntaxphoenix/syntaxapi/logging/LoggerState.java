package com.syntaxphoenix.syntaxapi.logging;

public enum LoggerState {
	
	NONE(),
	FILE(false, true, false),
	EXTENDED_FILE(true, true, false),
	CONSOLE(false, false, true),
	EXTENDED_CONSOLE(true, false, true),
	FILE_CONSOLE(false, true, true),
	EXTENDED_FILE_CONSOLE(true, true, true);
	
	private final boolean extended;
	private final boolean file;
	private final boolean console;
	private final boolean logging;
	
	private LoggerState(boolean extended, boolean file, boolean console) {
		this.extended = extended;
		this.file = file;
		this.console = console;
		this.logging = true;
	}
	
	private LoggerState() {
		this.extended = false;
		this.file = false;
		this.console = false;
		this.logging = false;
	}
	
	public boolean logConsole() {
		return console;
	}
	
	public boolean logFile() {
		return file;
	}
	
	public boolean extendedInfo() {
		return extended;
	}
	
	public boolean noLogging() {
		return !logging;
	}
	
	public static LoggerState byName(String name) {
		for(LoggerState state : values()) {
			if(state.name().equalsIgnoreCase(name)) {
				return state;
			}
		}
		return LoggerState.NONE;
	}
	
	public static LoggerState byOptions(boolean extended, boolean file, boolean console) {
		if(extended) {
			if(file) {
				if(console) {
					return LoggerState.EXTENDED_FILE_CONSOLE;
				} else {
					return LoggerState.EXTENDED_FILE;
				}
			} else {
				if(console) {
					return LoggerState.EXTENDED_CONSOLE;
				} else {
					return LoggerState.NONE;
				}
			}
		} else {
			if(file) {
				if(console) {
					return LoggerState.FILE_CONSOLE;
				} else {
					return LoggerState.FILE;
				}
			} else {
				if(console) {
					return LoggerState.CONSOLE;
				} else {
					return LoggerState.NONE;
				}
			}
		}
	}

}
