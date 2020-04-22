package com.syntaxphoenix.syntaxapi.command.builder;

import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.command.BaseInfo;

public class ConditionBuilder<E extends BaseInfo> {

	private final CommandBuilder<E> command;

	private final HashMap<Condition<E>, CommandAction<E>> conditions = new HashMap<>();

	private Condition<E> condition;
//	private <> action; ??? --> BuiltCommand?? // more??

	public ConditionBuilder(CommandBuilder<E> command) {
		this.command = command;
	}

	/*
	 * 
	 */

	public CommandBuilder<E> command() {
		return command;
	}

	/*
	 * 
	 */

	public Condition<E> condition() {
		return condition = new Condition<>();
	}

	public CommandAction<E> action() {
		return action = new CommandAction<>();
	}

	public ConditionBuilder<E> add() {
		if(action == null)
			return this;
		conditions.put(condition != null ? condition : new Condition<>(true), action);
		condition = null;
		action = null;
		return this;
	}

}
