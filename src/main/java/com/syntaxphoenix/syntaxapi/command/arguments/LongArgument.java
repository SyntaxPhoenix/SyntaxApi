package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class LongArgument extends BaseArgument {
	
	private Long value;
	
	public LongArgument() {
		this.value = 0L;
	}
	
	public LongArgument(Long value) {
		this.value = value;
	}
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.LONG;
	}

	@Override
	public Object asObject() {
		return value;
	}
	
	public Long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return toString(ArgumentSerializer.DEFAULT);
	}

	@Override
	public String toString(ArgumentSerializer serializer) {
		return serializer.toString(this);
	}

}
