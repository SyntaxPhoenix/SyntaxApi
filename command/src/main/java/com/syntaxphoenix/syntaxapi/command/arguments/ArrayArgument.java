package com.syntaxphoenix.syntaxapi.command.arguments;

import java.lang.reflect.Array;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
@SuppressWarnings("unchecked")
public class ArrayArgument<E extends BaseArgument> extends BaseArgument {

	private E[] value;

	public ArrayArgument(ArgumentType type) {
		this.value = (E[]) Array.newInstance(type.getClassType(), 8);
	}

	public ArrayArgument(ArgumentType type, int length) {
		this.value = (E[]) Array.newInstance(type.getClassType(), length);
	}

	public ArrayArgument(E... value) {
		this.value = value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.ARRAY;
	}

	@Override
	public Object asObject() {
		return value;
	}

	public E[] getValue() {
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
