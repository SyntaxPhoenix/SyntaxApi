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

/**
 * @author Lauriichen
 *
 */
@SuppressWarnings("unchecked")
public abstract class BaseArgument {

    public abstract ArgumentType getType();

    public Class<?> getClassType() {
        return getType().getClassType();
    }

    public ArgumentSuperType getSuperType() {
        return getType().getSuperType();
    }

    public abstract Object asObject();

    @Override
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

    public NumericArgument asNumeric() {
        return (NumericArgument) this;
    }

    public IntegerArgument asInteger() {
        return (IntegerArgument) this;
    }

    public LongArgument asLong() {
        return (LongArgument) this;
    }

    public ByteArgument asByte() {
        return (ByteArgument) this;
    }

    public ShortArgument asShort() {
        return (ShortArgument) this;
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
