package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class IntegerArgument extends BaseArgument {
	
	private Integer vInteger;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.INTEGER;
	}

	@Override
	public Object asObject() {
		return vInteger;
	}
	
	public Integer getValue() {
		return vInteger;
	}

}
