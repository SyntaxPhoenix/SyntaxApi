package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.NumericArgument;

/**
 * @author Lauriichen
 *
 */
public class DoubleArgument extends NumericArgument {
	
	private Double value;
	
	public DoubleArgument() {
		this.value = 0D;
	}
	
	public DoubleArgument(Double value) {
		this.value = value;
	}
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.DOUBLE;
	}

	@Override
	public Object asObject() {
		return value;
	}
	
	@Override
	public Number asNumber() {
		return value;
	}
	
	public Double getValue() {
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
