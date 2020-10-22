package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.NumericArgument;

/**
 * @author Lauriichen
 *
 */
public class IntegerArgument extends NumericArgument {

	private Integer value;

	public IntegerArgument() {
		this.value = 0;
	}

	public IntegerArgument(Integer value) {
		this.value = value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.INTEGER;
	}

	@Override
	public Object asObject() {
		return value;
	}

	@Override
	public Number asNumber() {
		return value;
	}

	public Integer getValue() {
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
