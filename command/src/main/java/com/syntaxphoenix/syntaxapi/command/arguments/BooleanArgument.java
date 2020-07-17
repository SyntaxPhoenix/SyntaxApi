package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

public class BooleanArgument extends BaseArgument {
	
	private Boolean value;
	
	public BooleanArgument() {
		this.value = false;
	}
	
	public BooleanArgument(Boolean value) {
		this.value = value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.BOOLEAN;
	}

	@Override
	public Object asObject() {
		return value;
	}
	
	public Boolean getValue() {
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
