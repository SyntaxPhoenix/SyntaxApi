package com.syntaxphoenix.syntaxapi.command;

import com.syntaxphoenix.syntaxapi.command.arguments.*;
/**
 * @author Lauriichen
 *
 */
@SuppressWarnings("unchecked")
public abstract class BaseArgument {
	
	public abstract ArgumentType getType();
	
	public ArgumentSuperType getSuperType() {
		return getType().getSuperType();
	}
	
	public abstract Object asObject();
	
	public abstract String toString();
	
	public abstract String toString(ArgumentSerializer serializer);
	
	public BooleanArgument asBoolean() {
		return (BooleanArgument) this;
	}
	public ArrayArgument<BaseArgument> asArray() {
		return (ArrayArgument<BaseArgument>) this;
	}
	public ListArgument<BaseArgument> asList() {
		return (ListArgument<BaseArgument>) this;
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
	public BigIntegerArgument asBigInteger() {
		return (BigIntegerArgument) this;
	}
	public BigDecimalArgument asBigDecimal() {
		return (BigDecimalArgument) this;
	}

	public <E> E as(Class<E> example) {
		return (E) asObject();
	}
	public <E> E as(E example) {
		return (E) asObject();
	}
	
}
