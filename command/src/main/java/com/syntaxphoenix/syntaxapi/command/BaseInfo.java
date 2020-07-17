package com.syntaxphoenix.syntaxapi.command;

public abstract class BaseInfo {

	private final CommandManager manager;
	private final String label;
	
	public BaseInfo(CommandManager manager, String label) {
		this.manager = manager;
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public CommandManager getManager() {
		return manager;
	}

}
