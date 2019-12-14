package com.syntaxphoenix.syntaxapi.command;

import com.syntaxphoenix.syntaxapi.command.arguments.*;
/**
 * @author Lauriichen
 *
 */
public abstract class BaseArgument {
	
	public abstract ArgumentType getType();
	
	public ArgumentSuperType getSuperType() {
		return getType().getSuperType();
	}
	
	public abstract Object asObject();
	
	public ArrayArgument<?> asArray() {
		return (ArrayArgument<?>) this;
	}
	public ListArgument<?> asList() {
		return (ListArgument<?>) this;
	}
	public StringArgument asString() {
		return (StringArgument) this;
	}
	public IntegerArgument asInteger() {
		return (IntegerArgument) this;
	}
	public LongArgument asLong() {
		return (LongArgument) this;
	}
	public FloatArgument asFloat() {
		return (FloatArgument) this;
	}
	public DoubleArgument asDouble() {
		return (DoubleArgument) this;
	}

}
