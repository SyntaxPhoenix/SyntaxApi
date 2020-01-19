package com.syntaxphoenix.syntaxapi.command;

import com.syntaxphoenix.syntaxapi.command.arguments.*;

public abstract class ArgumentSerializer {

	public static final ArgumentSerializer DEFAULT = new DefaultArgumentSerializer();

	public String toString(BaseArgument argument) {
		ArgumentType type = argument.getType();
		String output = "";
		switch (type) {
		case BOOLEAN:
			output = toString(argument.asBoolean());
			break;
		case ARRAY:
			output = toString(argument.asArray());
			break;
		case BIG_INTEGER:
			output = toString(argument.asBigInteger());
			break;
		case BIG_DECIMAL:
			output = toString(argument.asBigDecimal());
			break;
		case DOUBLE:
			output = toString(argument.asDouble());
			break;
		case FLOAT:
			output = toString(argument.asFloat());
			break;
		case INTEGER:
			output = toString(argument.asInteger());
			break;
		case LIST:
			output = toString(argument.asList());
			break;
		case LONG:
			output = toString(argument.asLong());
			break;
		case STRING:
			output = toString(argument.asString());
			break;
		case CUSTOM:
			output = argument.asObject().toString();
			break;
		}
		return output;
	}

	public abstract String toString(ArrayArgument<BaseArgument> argument);

	public abstract String toString(BigDecimalArgument argument);

	public abstract String toString(BigIntegerArgument argument);

	public abstract String toString(DoubleArgument argument);

	public abstract String toString(FloatArgument argument);

	public abstract String toString(IntegerArgument argument);

	public abstract String toString(ListArgument<BaseArgument> argument);

	public abstract String toString(LongArgument argument);

	public abstract String toString(StringArgument argument);

	public abstract String toString(BooleanArgument argument);

}
