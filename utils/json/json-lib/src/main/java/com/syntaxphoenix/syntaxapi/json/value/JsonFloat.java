package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonFloat extends JsonNumber<Float> {

    public JsonFloat(Float value) {
        super(value);
    }

    @Override
    public ValueType getType() {
        return ValueType.FLOAT;
    }

}
