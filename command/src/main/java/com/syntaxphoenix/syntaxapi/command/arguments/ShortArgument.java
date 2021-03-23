package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.NumericArgument;

public class ShortArgument extends NumericArgument {

    private Short value;

    public ShortArgument() {
        this.value = 0;
    }

    public ShortArgument(Short value) {
        this.value = value;
    }

    @Override
    public ArgumentType getType() {
        return ArgumentType.SHORT;
    }

    @Override
    public Object asObject() {
        return value;
    }

    @Override
    public Number asNumber() {
        return value;
    }

    public Short getValue() {
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
