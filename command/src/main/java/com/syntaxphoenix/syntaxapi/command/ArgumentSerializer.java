package com.syntaxphoenix.syntaxapi.command;

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

public abstract class ArgumentSerializer {

    public static final ArgumentSerializer DEFAULT = new DefaultArgumentSerializer();

    public String toString(BaseArgument argument) {
        ArgumentType type = argument.getType();
        String output = "";
        switch (type) {
        case BOOLEAN:
            output = toString(argument.asBoolean());
            break;
        case BYTE:
            output = toString(argument.asByte());
            break;
        case SHORT:
            output = toString(argument.asShort());
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

    public abstract String toString(ByteArgument argument);

    public abstract String toString(ShortArgument argument);

    public abstract String toString(FloatArgument argument);

    public abstract String toString(IntegerArgument argument);

    public abstract String toString(ListArgument<BaseArgument> argument);

    public abstract String toString(LongArgument argument);

    public abstract String toString(StringArgument argument);

    public abstract String toString(BooleanArgument argument);

    /**
     * @param arguments array of complex arguments
     * @return array of raw arguments
     */
    public abstract String[] asStringArray(BaseArgument... arguments);

}
