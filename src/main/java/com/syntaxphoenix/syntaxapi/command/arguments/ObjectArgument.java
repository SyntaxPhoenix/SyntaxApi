package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

public class ObjectArgument<E> extends BaseArgument {
	
	private E value;
	
	public ObjectArgument(E value) {
		this.value = value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.CUSTOM;
	}

	@Override
	public Object asObject() {
		return value;
	}
	
	public E getValue() {
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
