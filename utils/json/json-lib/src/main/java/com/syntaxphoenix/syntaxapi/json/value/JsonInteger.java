package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonInteger extends JsonNumber<Integer> {

    public JsonInteger(Integer value) {
        super(value);
    }

    @Override
    public final ValueType getType() {
        return ValueType.INTEGER;
    }

}
