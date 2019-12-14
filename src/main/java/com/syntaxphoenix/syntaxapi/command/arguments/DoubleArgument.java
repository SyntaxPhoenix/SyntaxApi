package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class DoubleArgument extends BaseArgument {
	
	private Double vDouble;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.DOUBLE;
	}

	@Override
	public Object asObject() {
		return vDouble;
	}
	
	public Double getValue() {
		return vDouble;
	}

}
