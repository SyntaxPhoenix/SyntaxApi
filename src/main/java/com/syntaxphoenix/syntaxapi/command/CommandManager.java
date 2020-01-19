package com.syntaxphoenix.syntaxapi.command;

import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.utils.java.Arrays;

/**
 * @author Lauriichen
 *
 */
public class CommandManager {
	
	private final HashMap<String, BaseCommand> commands = new HashMap<>();
	private ArgumentValidator validator = ArgumentValidator.DEFAULT;
	private SynLogger logger = null;
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
	
	public CommandManager setLogger(SynLogger logger) {
		this.logger = logger;
		return this;
	}
	
	public boolean hasLogger() {
		return logger != null;
	}
	
	public SynLogger getLogger() {
		return logger;
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
	
	public CommandProcess process(String message) {
		CommandProcess process = new CommandProcess(this);
		if(!message.startsWith(prefix)) {
			return process.lock();
		}
		process.setValid(true);
		String[] parts = message.replace(prefix, "").split(splitter);
		String command = parts[0].toLowerCase();
		if(!commands.containsKey(command)) {
			return process.lock();
		}
		process.setLabel(command);
		process.setCommand(commands.get(command));
		process.setArguments(new Arguments(validator.process(Arrays.subArray(parts, 1))));
		return process.lock();
	}
	
	public ExecutionState execute(String message) {
		return process(message).execute();
	}
	
	public ExecutionState execute(CommandProcess process) {
		ExecutionState state = process.asState();
		if(!state.isRunnable()) {
			return state;
		}
		return execute(process.getCommand(), process.getArguments());
	}
	
	public ExecutionState execute(BaseCommand command, Arguments arguments) {
		try {
			command.execute(arguments);
		} catch(Throwable throwable) {
			if(hasLogger()) {
				logger.log(throwable);
			}
			return ExecutionState.FAILED;
		}
		return ExecutionState.SUCCESS;
	}
	
}
