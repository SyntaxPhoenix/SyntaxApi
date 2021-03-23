package com.syntaxphoenix.syntaxapi.command;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.syntaxphoenix.syntaxapi.command.arguments.ArrayArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.BigDecimalArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.BigIntegerArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.BooleanArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.ByteArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.DoubleArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.FloatArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.IntegerArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.ListArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.LongArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.ShortArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.StringArgument;

public class DefaultArgumentSerializer extends ArgumentSerializer {

    @Override
    public String toString(ArrayArgument<BaseArgument> argument) {
        BaseArgument[] value = argument.getValue();
        if (value == null || value.length == 0) {
            return "[]";
        }
        Iterator<BaseArgument> iterator = Arrays.stream(value).iterator();
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        while (iterator.hasNext()) {
            BaseArgument entry = iterator.next();
            if (entry == null) {
                continue;
            }
            builder.append(toString(entry));
            if (iterator.hasNext()) {
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
    public String toString(BigDecimalArgument argument) {
        return argument.getValue().toString();
    }

    @Override
    public String toString(DoubleArgument argument) {
        return argument.getValue().toString();
    }

    @Override
    public String toString(ByteArgument argument) {
        return argument.getValue().toString();
    }

    @Override
    public String toString(ShortArgument argument) {
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
        List<BaseArgument> value = argument.getValue();
        if (value == null || value.isEmpty()) {
            return "{}";
        }
        Iterator<BaseArgument> iterator = value.iterator();
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        while (iterator.hasNext()) {
            BaseArgument entry = iterator.next();
            if (entry == null) {
                continue;
            }
            builder.append(toString(entry));
            if (iterator.hasNext()) {
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

    @Override
    public String[] asStringArray(BaseArgument... arguments) {
        if (arguments == null || arguments.length == 0) {
            return new String[0];
        }
        int length = arguments.length;
        String[] array = new String[length];
        for (int index = 0; index < length; index++) {
            array[index] = arguments[index].toString(this);
        }
        return array;
    }

}