package com.syntaxphoenix.syntaxapi.command.builder;

import java.util.ArrayList;
import java.util.function.BiFunction;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.BaseInfo;

public class Condition<E extends BaseInfo> {
	
	private final ArrayList<BiFunction<E, Arguments, Boolean>> functions = new ArrayList<>();
	private final ConditionBuilder<E> builder;
	private final boolean locked;
	
	public Condition() {
		this.builder = null;
		this.locked = false;
	}
	
	public Condition(boolean locked) {
		this.builder = null;
		this.locked = locked;
	}
	
	public Condition(ConditionBuilder<E> builder) {
		this.builder = builder;
		this.locked = false;
	}
	
	/*
	 * 
	 */
	
	public ConditionBuilder<E> builder() {
		return builder;
	}
	
	public final boolean isLocked() {
		return locked;
	}
	
	/*
	 * 
	 */
	
	public final Condition<E> add(BiFunction<E, Arguments, Boolean> condition) {
		if(!locked)
			functions.add(condition);
		return this;
	}
	
	public boolean isAllowed(E info, Arguments arguments) {
		if(functions.isEmpty())
			return true;
		for(BiFunction<E, Arguments, Boolean> function : functions)
			if(!function.apply(info, arguments))
				return false;
		return true;
	}
	
	/*
	 * 
	 */
	
	public Condition<E> hasType(int index, ArgumentType type) {
		return add((info, arguments) -> {
			if(arguments.count() >= index)
				return false;
			return arguments.get(index).getType() == type;
		});
	}
	
	public Condition<E> isInRange(int index, BaseArgumentRange range) {
		return add((info, arguments) -> {
			if(arguments.count() >= index)
				return false;
			return range.isInRange(arguments.get(index));
		});
	}
	
}
