package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class StringArgument extends BaseArgument {
	
	private String vString;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.STRING;
	}

	@Override
	public Object asObject() {
		return vString;
	}
	
	public String getValue() {
		return vString;
	}

}
