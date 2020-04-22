package com.syntaxphoenix.syntaxapi.command.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.BaseInfo;
import com.syntaxphoenix.syntaxapi.utils.java.Lists;

public class CommandBuilder<E extends BaseInfo> {
	
	private final Class<E> infoType;
	private final CommandBuilder<E> parent;
	
	private final ConditionBuilder<E> condition = new ConditionBuilder<>(this);
	
	private final HashMap<String, CommandBuilder<E>> commands = new HashMap<>();
	
	private List<String> aliases = new ArrayList<>();
	private String label;
	
	private BiConsumer<E, Arguments> command;
	
	public CommandBuilder(Class<E> infoType) {
		this.infoType = infoType;
		this.parent = null;
	}
	
	private CommandBuilder(CommandBuilder<E> parent) {
		this.infoType = parent.infoType;
		this.parent = parent;
	}
	
	/*
	 * 
	 */
	
	public CommandBuilder<E> label(String label) {
		this.label = label;
		return this;
	}
	
	public String label() {
		return label;
	}
	
	/*
	 * 
	 */	
	
	public CommandBuilder<E> alias(boolean remove, String alias) {
		return remove ? removeAlias(alias) : addAlias(alias);
	}
	
	public CommandBuilder<E> addAlias(String alias) {
		if(!aliases.contains((alias = alias.toLowerCase())))
				aliases.add(alias);
		return this;
	}
	
	public CommandBuilder<E> removeAlias(String alias) {
		aliases.remove(alias.toLowerCase());
		return this;
	}
	
	public String alias(int index) {
		return aliases.get(index);
	}
	
	/*
	 * 
	 */
	
	public CommandBuilder<E> aliases(String... aliases) {
		return aliases(Lists.asList(aliases));
	}
	
	public CommandBuilder<E> aliases(List<String> aliases) {
		this.aliases = aliases;
		return this;
	}
	
	public List<String> aliases() {
		return aliases;
	}
	
	/*
	 * 
	 */
	
	public CommandBuilder<E> add(String label) {
		if(!commands.containsKey((label = label.toLowerCase())))
			commands.put(label, new CommandBuilder<>(this).label(label));
		return this;
	}
	
	public CommandBuilder<E> remove(String label) {
		commands.remove(label);
		return this;
	}
	
	public CommandBuilder<E> get(String label) {
		return commands.get(label);
	}
	
	/*
	 * 
	 */
	
	public CommandBuilder<E> command(BiConsumer<E, Arguments> command) {
		this.command = command;
		return this;
	}
	
	public BiConsumer<E, Arguments> command() {
		return command;
	}
	
	/*
	 * 
	 */

	public Class<E> infoType() {
		return infoType;
	}
	
	public CommandBuilder<E> clone() {
		return new CommandBuilder<>(infoType).label(label).aliases(aliases).command(command);
	}
	
	public CommandBuilder<E> parent() {
		return parent;
	}
	
	public ConditionBuilder<E> condition() {
		return condition;
	}
	
	public BuiltCommand<E> build() {
		return new BuiltCommand<>(this);
	}
	
	
}
