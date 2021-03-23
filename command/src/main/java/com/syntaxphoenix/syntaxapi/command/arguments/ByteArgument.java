package com.syntaxphoenix.syntaxapi.command.arguments;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.NumericArgument;

public class ByteArgument extends NumericArgument {

    private Byte value;

    public ByteArgument() {
        this.value = 0;
    }

    public ByteArgument(Byte value) {
        this.value = value;
    }

    @Override
    public ArgumentType getType() {
        return ArgumentType.BYTE;
    }

    @Override
    public Object asObject() {
        return value;
    }

    @Override
    public Number asNumber() {
        return value;
    }

    public Byte getValue() {
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
