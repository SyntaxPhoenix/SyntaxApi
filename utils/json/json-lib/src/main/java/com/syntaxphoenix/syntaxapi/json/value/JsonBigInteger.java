package com.syntaxphoenix.syntaxapi.json.value;

import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonBigInteger extends JsonNumber<BigInteger> {

    public JsonBigInteger(BigInteger value) {
        super(value);
    }

    @Override
    public ValueType getType() {
        return ValueType.BIG_INTEGER;
    }

}
