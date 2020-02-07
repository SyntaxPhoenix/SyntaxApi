package com.syntaxphoenix.syntaxapi.command.arguments;

import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.NumericArgument;

/**
 * @author Lauriichen
 *
 */
public class BigIntegerArgument extends NumericArgument {

	private BigInteger value;

	public BigIntegerArgument() {
		this.value = BigInteger.ZERO.abs();
	}

	public BigIntegerArgument(BigInteger value) {
		this.value = value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.BIG_INTEGER;
	}

	@Override
	public Object asObject() {
		return value;
	}
	
	@Override
	public Number asNumber() {
		return value;
	}

	public BigInteger getValue() {
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
