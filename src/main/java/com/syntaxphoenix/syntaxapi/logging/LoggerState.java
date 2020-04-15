package com.syntaxphoenix.syntaxapi.logging;

public enum LoggerState {
	
	NONE(),
	STREAM(false, true, false),
	EXTENDED_STREAM(true, true, false),
	CUSTOM(false, false, true),
	EXTENDED_CUSTOM(true, false, true),
	STREAM_CUSTOM(false, true, true),
	EXTENDED_STREAM_CUSTOM(true, true, true);
	
	private final boolean extended;
	private final boolean stream;
	private final boolean custom;
	private final boolean logging;
	
	private LoggerState(boolean extended, boolean stream, boolean custom) {
		this.extended = extended;
		this.stream = stream;
		this.custom = custom;
		this.logging = true;
	}
	
	private LoggerState() {
		this.extended = false;
		this.stream = false;
		this.custom = false;
		this.logging = false;
	}
	
	public boolean useCustom() {
		return custom;
	}
	
	public boolean useStream() {
		return stream;
	}
	
	public boolean extendedInfo() {
		return extended;
	}
	
	public boolean isEnabled() {
		return logging;
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
					return LoggerState.EXTENDED_STREAM_CUSTOM;
				} else {
					return LoggerState.EXTENDED_STREAM;
				}
			} else {
				if(console) {
					return LoggerState.EXTENDED_CUSTOM;
				} else {
					return LoggerState.NONE;
				}
			}
		} else {
			if(file) {
				if(console) {
					return LoggerState.STREAM_CUSTOM;
				} else {
					return LoggerState.STREAM;
				}
			} else {
				if(console) {
					return LoggerState.CUSTOM;
				} else {
					return LoggerState.NONE;
				}
			}
		}
	}

}
