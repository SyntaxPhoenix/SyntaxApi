package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class ArrayArgument<E> extends BaseArgument {
	
	private E[] array;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.ARRAY;
	}
	
	@Override
	public Object asObject() {
		return array;
	}
	
	public E[] getValue() {
		return array;
	}

}
