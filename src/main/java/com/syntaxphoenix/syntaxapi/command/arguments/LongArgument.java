package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class LongArgument extends BaseArgument {
	
	private Long vLong;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.LONG;
	}

	@Override
	public Object asObject() {
		return vLong;
	}
	
	public Long getValue() {
		return vLong;
	}

}
