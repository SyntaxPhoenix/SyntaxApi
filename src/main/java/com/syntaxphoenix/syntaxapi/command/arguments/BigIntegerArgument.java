package com.syntaxphoenix.syntaxapi.command.arguments;

import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class BigIntegerArgument extends BaseArgument {
	
	private BigInteger bigInteger;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.BIG_INTEGER;
	}

	@Override
	public Object asObject() {
		return bigInteger;
	}
	
	public BigInteger getValue() {
		return bigInteger;
	}

}
