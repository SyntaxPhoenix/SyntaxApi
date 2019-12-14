package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class FloatArgument extends BaseArgument {
	
	private Float vFloat;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.FLOAT;
	}

	@Override
	public Object asObject() {
		return vFloat;
	}
	
	public Float getValue() {
		return vFloat;
	}

}
