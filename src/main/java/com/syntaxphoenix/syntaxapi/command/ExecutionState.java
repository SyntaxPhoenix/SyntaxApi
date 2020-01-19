package com.syntaxphoenix.syntaxapi.command;

public enum ExecutionState {
	
	SUCCESS, READY, FAILED, NOT_EXISTENT, NO_COMMAND;
	
	public boolean isRunnable() {
		return this == READY;
	}

}
