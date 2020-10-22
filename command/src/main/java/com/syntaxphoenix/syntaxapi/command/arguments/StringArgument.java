package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class StringArgument extends BaseArgument {

	private String value;

	public StringArgument() {
		this.value = "";
	}

	public StringArgument(String value) {
		this.value = value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.STRING;
	}

	@Override
	public Object asObject() {
		return value;
	}

	public String getValue() {
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
