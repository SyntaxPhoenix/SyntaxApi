package com.syntaxphoenix.syntaxapi.command;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.utils.alias.Alias;
import com.syntaxphoenix.syntaxapi.utils.alias.AliasMap;
import com.syntaxphoenix.syntaxapi.utils.java.Arrays;

/**
 * @author Lauriichen
 *
 */
public class CommandManager {

	protected BiFunction<CommandManager, String, ? extends BaseInfo> infoConstructor = ((manager,
		label) -> new DefaultInfo(manager, label));

	protected final AliasMap<BaseCommand> commands = new AliasMap<>();
	protected ArgumentIdentifier validator = ArgumentIdentifier.DEFAULT;
	protected ILogger logger = null;

	protected String splitter = " ";
	protected String prefix = "!";

	/*
	 * Getter
	 */

	public AliasMap<BaseCommand> getClonedMap() {
		synchronized (commands) {
			return commands.clone();
		}
	}

	@SuppressWarnings("unchecked")
	public Entry<Alias, BaseCommand>[] getEntries() {
		synchronized (commands) {
			return commands.entrySet().toArray(new Entry[0]);
		}
	}

	public BaseCommand[] getCommands() {
		synchronized (commands) {
			return commands.values().toArray(new BaseCommand[0]);
		}
	}

	public Alias[] getAliases() {
		synchronized (commands) {
			return commands.keySet().toArray(new Alias[0]);
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSplitter() {
		return splitter;
	}

	public BiFunction<CommandManager, String, ? extends BaseInfo> getInfoConstructor() {
		return infoConstructor;
	}

	public ArgumentIdentifier getValidator() {
		return validator;
	}

	public boolean hasLogger() {
		return logger != null;
	}

	public ILogger getLogger() {
		return logger;
	}

	/*
	 * Setter
	 */

	public CommandManager setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public CommandManager setSplitter(String splitter) {
		this.splitter = splitter;
		return this;
	}

	public CommandManager setInfoConstructor(BiFunction<CommandManager, String, ? extends BaseInfo> infoConstructor) {
		this.infoConstructor = infoConstructor;
		return this;
	}

	public CommandManager setValidator(ArgumentIdentifier validator) {
		this.validator = validator;
		return this;
	}

	public CommandManager setLogger(ILogger logger) {
		this.logger = logger;
		return this;
	}

	/*
	 * Management
	 */

	public CommandManager register(BaseCommand command, String name, String... aliases) {
		return register(command, new Alias(name, aliases));
	}

	public CommandManager register(BaseCommand command, String description, String name, String[] aliases) {
		return register(command, new Alias(name, aliases).setDescription(description));
	}

	public CommandManager register(BaseCommand command, Alias alias) {
		synchronized (commands) {
			if (commands.hasConflict(alias).isEmpty()) {
				commands.put(alias, command);
			}
		}
		return this;
	}

	public CommandManager unregister(BaseCommand command) {
		synchronized (commands) {
			if (!commands.containsValue(command))
				return this;
			Optional<Entry<Alias, BaseCommand>> optional = commands
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue().equals(command))
				.findFirst();
			if (optional.isPresent())
				commands.remove(optional.get().getKey());
		}
		return this;
	}

	public CommandManager unregister(String name) {
		synchronized (commands) {
			if (commands.hasConflict(new Alias(name)).isEmpty())
				return this;
			Optional<Alias> optional = commands.keySet().stream().filter(alias -> alias.isLabel(name)).findFirst();
			if (optional.isPresent())
				commands.remove(optional.get());
		}
		return this;
	}

	public CommandManager unregister(Alias alias) {
		synchronized (commands) {
			String[] conflicts = commands.hasConflict(alias).toArray(new String[0]);
			if (conflicts.length == 0)
				return this;
			Alias[] aliases = commands
				.keySet()
				.stream()
				.filter(current -> hasLabel(conflicts, current))
				.toArray(size -> new Alias[size]);
			for (int index = 0; index < aliases.length; index++)
				commands.remove(aliases[index]);
		}
		return this;
	}

	private boolean hasLabel(String[] source, Alias compare) {
		for (int index = 0; index < source.length; index++)
			if (compare.isLabel(source[index]))
				return true;
		return false;
	}

	public CommandManager unregisterAll() {
		synchronized (commands) {
			commands.clear();
		}
		return this;
	}

	/*
	 * Command Processing
	 */

	public CommandProcess process(String message) {
		CommandProcess process = new CommandProcess(this);
		if (!message.startsWith(prefix) || message.equals(prefix)) {
			return process.lock();
		}
		return process(message.replace(prefix, "").split(splitter));
	}

	public CommandProcess process(String... message) {
		return process(new CommandProcess(this), message);
	}

	private CommandProcess process(CommandProcess process, String... message) {
		String command = message[0].toLowerCase();
		process.setLabel(command);
		if (command.isEmpty()) {
			return process.lock();
		}
		process.setValid(true);
		synchronized (commands) {
			if (!commands.containsKey(command)) {
				return process.lock();
			}
			process.setCommand(commands.get(command));
		}
		process.setArguments(new Arguments(validator.process(Arrays.subArray(size -> new String[size], message, 1))));
		return process.lock();
	}

	/*
	 * Command Execution
	 */

	public ExecutionState execute(String message) {
		return process(message).execute();
	}

	public ExecutionState execute(CommandProcess process) {
		ExecutionState state = process.asState();
		if (!state.isRunnable()) {
			return state;
		}
		return execute(process.getCommand(), process.constructInfo(), process.getArguments());
	}

	public ExecutionState execute(BaseCommand command, BaseInfo info, Arguments arguments) {
		try {
			command.execute(info, arguments);
		} catch (Throwable throwable) {
			if (hasLogger()) {
				logger.log(throwable);
			}
			return ExecutionState.FAILED;
		}
		return ExecutionState.SUCCESS;
	}

}
