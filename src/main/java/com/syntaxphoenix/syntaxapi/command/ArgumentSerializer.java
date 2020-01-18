package com.syntaxphoenix.syntaxapi.command;

import com.syntaxphoenix.syntaxapi.command.arguments.*;

public abstract class ArgumentSerializer {

	public static final ArgumentSerializer DEFAULT = new DefaultArgumentSerializer();
	
	public String toString(BaseArgument argument) {
		return argument.toString(this);
	}
	
	public abstract String toString(ArrayArgument<BaseArgument> argument);
	
	public abstract String toString(BigIntegerArgument argument);
	
	public abstract String toString(DoubleArgument argument);
	
	public abstract String toString(FloatArgument argument);
	
	public abstract String toString(IntegerArgument argument);
	
	public abstract String toString(ListArgument<BaseArgument> argument);
	
	public abstract String toString(LongArgument argument);
	
	public abstract String toString(StringArgument argument);
	
	public abstract String toString(BooleanArgument argument);

}
