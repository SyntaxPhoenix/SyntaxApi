package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.syntaxphoenix.syntaxapi.command.arguments.ArrayArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.BigIntegerArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.BooleanArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.DoubleArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.FloatArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.IntegerArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.ListArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.LongArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.StringArgument;

public class DefaultArgumentSerializer extends ArgumentSerializer {

	@Override
	public String toString(BaseArgument argument) {
		return argument.toString(this);
	}
	
	@Override
	public String toString(ArrayArgument<BaseArgument> argument) {
		BaseArgument[] value = argument.getValue();
		if(value == null || value.length == 0) {
			return "[]";
		}
		@SuppressWarnings("unchecked")
		Iterator<BaseArgument> iterator = (Iterator<BaseArgument>) Arrays.stream(value);
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		while(iterator.hasNext()) {
			BaseArgument entry = iterator.next();
			if(entry == null) {
				continue;
			}
			builder.append(toString(entry));
			if(iterator.hasNext()) {
				builder.append(',');
				builder.append(' ');
			}
		}
		builder.append(']');
		return builder.toString();
	}

	@Override
	public String toString(BigIntegerArgument argument) {
		return argument.getValue().toString();
	}

	@Override
	public String toString(DoubleArgument argument) {
		return argument.getValue().toString();
	}

	@Override
	public String toString(FloatArgument argument) {
		return argument.getValue().toString();
	}

	@Override
	public String toString(IntegerArgument argument) {
		return argument.getValue().toString();
	}

	@Override
	public String toString(ListArgument<BaseArgument> argument) {
		ArrayList<BaseArgument> value = argument.getValue();
		if(value == null || value.isEmpty()) {
			return "{}";
		}
		Iterator<BaseArgument> iterator = value.iterator();
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		while(iterator.hasNext()) {
			BaseArgument entry = iterator.next();
			if(entry == null) {
				continue;
			}
			builder.append(toString(entry));
			if(iterator.hasNext()) {
				builder.append(',');
				builder.append(' ');
			}
		}
		builder.append('}');
		return builder.toString();
	}

	@Override
	public String toString(LongArgument argument) {
		return argument.getValue().toString();
	}

	@Override
	public String toString(StringArgument argument) {
		return argument.getValue();
	}

	@Override
	public String toString(BooleanArgument argument) {
		return argument.getValue().toString();
	}
	
}