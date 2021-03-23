package com.syntaxphoenix.syntaxapi.json.value;

import java.math.BigDecimal;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonBigDecimal extends JsonNumber<BigDecimal> {

    public JsonBigDecimal(BigDecimal value) {
        super(value);
    }

    @Override
    public ValueType getType() {
        return ValueType.BIG_DECIMAL;
    }

}
