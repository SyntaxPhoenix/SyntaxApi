package com.syntaxphoenix.syntaxapi.command.arguments;

import java.util.ArrayList;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class ListArgument<E extends BaseArgument> extends BaseArgument {
	
	private ArrayList<E> value;
	
	public ListArgument() {
		this.value = new ArrayList<>();
	}
	
	public ListArgument(ArrayList<E> value) {
		this.value = value;
	}
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.LIST;
	}
	
	@Override
	public Object asObject() {
		return value;
	}
	
	public ArrayList<E> getValue() {
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
