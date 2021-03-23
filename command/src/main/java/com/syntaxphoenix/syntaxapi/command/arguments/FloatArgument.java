package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.NumericArgument;

/**
 * @author Lauriichen
 *
 */
public class FloatArgument extends NumericArgument {

    private Float value;

    public FloatArgument() {
        this.value = 0F;
    }

    public FloatArgument(Float value) {
        this.value = value;
    }

    @Override
    public ArgumentType getType() {
        return ArgumentType.FLOAT;
    }

    @Override
    public Object asObject() {
        return value;
    }

    @Override
    public Number asNumber() {
        return value;
    }

    public Float getValue() {
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
