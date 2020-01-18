package com.syntaxphoenix.syntaxapi.command;

import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.utils.java.Arrays;

/**
 * @author Lauriichen
 *
 */
public class CommandManager {
	
	private final HashMap<String, BaseCommand> commands = new HashMap<>();
	private ArgumentValidator validator = ArgumentValidator.DEFAULT;
	private String splitter = " ";
	private String prefix = "!";
	
	public String getPrefix() {
		return prefix;
	}
	
	public CommandManager setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public String getSplitter() {
		return splitter;
	}
	
	public CommandManager setSplitter(String splitter) {
		this.splitter = splitter;
		return this;
	}
	
	public ArgumentValidator getValidator() {
		return validator;
	}
	
	public CommandManager setValidator(ArgumentValidator validator) {
		this.validator = validator;
		return this;
	}
	
	/*
	 * 
	 */
	
	public CommandManager register(BaseCommand command, String... names) {
		if(names == null || names.length == 0) {
			return this;
		}
		for(String name : names) {
			if(!commands.containsKey(name = name.toLowerCase())) {
				commands.put(name, command);
			}
		}
		return this;
	}
	
	public CommandManager execute(String message) {
		if(!message.startsWith(prefix)) {
			return this;
		}
		String[] parts = message.replace(prefix, "").split(splitter);
		String command = parts[0].toLowerCase();
		if(!commands.containsKey(command)) {
			return this;
		}
		commands.get(command).execute(new Arguments(validator.process(Arrays.subArray(parts, 1))));
		return this;
	}
	
}
