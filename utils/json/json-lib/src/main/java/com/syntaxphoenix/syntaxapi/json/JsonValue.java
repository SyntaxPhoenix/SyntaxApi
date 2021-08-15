package com.syntaxphoenix.syntaxapi.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;
import com.syntaxphoenix.syntaxapi.json.value.JsonBigDecimal;
import com.syntaxphoenix.syntaxapi.json.value.JsonBigInteger;
import com.syntaxphoenix.syntaxapi.json.value.JsonBoolean;
import com.syntaxphoenix.syntaxapi.json.value.JsonByte;
import com.syntaxphoenix.syntaxapi.json.value.JsonDouble;
import com.syntaxphoenix.syntaxapi.json.value.JsonFloat;
import com.syntaxphoenix.syntaxapi.json.value.JsonInteger;
import com.syntaxphoenix.syntaxapi.json.value.JsonLong;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;
import com.syntaxphoenix.syntaxapi.json.value.JsonShort;
import com.syntaxphoenix.syntaxapi.json.value.JsonString;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public abstract class JsonValue<E> {

    private static final JsonWriter PRETTY = new JsonWriter().setPretty(true);
    private static final JsonWriter UNPRETTY = new JsonWriter();

    @SuppressWarnings("unchecked")
    public static <E> JsonValue<E> fromPrimitive(E primitive) {
        if (primitive == null) {
            return JsonNull.get();
        }
        Class<?> complex = Primitives.fromPrimitive(primitive.getClass());
        if (complex == String.class) {
            return (JsonValue<E>) new JsonString((String) primitive);
        }
        if (complex == Boolean.class) {
            return (JsonValue<E>) new JsonBoolean((Boolean) primitive);
        }
        if (complex == Byte.class) {
            return (JsonValue<E>) new JsonByte((Byte) primitive);
        }
        if (complex == Short.class) {
            return (JsonValue<E>) new JsonShort((Short) primitive);
        }
        if (complex == Integer.class) {
            return (JsonValue<E>) new JsonInteger((Integer) primitive);
        }
        if (complex == Long.class) {
            return (JsonValue<E>) new JsonLong((Long) primitive);
        }
        if (complex == Float.class) {
            return (JsonValue<E>) new JsonFloat((Float) primitive);
        }
        if (complex == Double.class) {
            return (JsonValue<E>) new JsonDouble((Double) primitive);
        }
        if (complex == BigInteger.class) {
            return (JsonValue<E>) new JsonBigInteger((BigInteger) primitive);
        }
        if (complex == BigDecimal.class) {
            return (JsonValue<E>) new JsonBigDecimal((BigDecimal) primitive);
        }
        return JsonNull.get();
    }

    public abstract ValueType getType();

    public abstract E getValue();

    public boolean hasType(ValueType type) {
        return type.hasType(this);
    }

    public boolean isPrimitive() {
        return getType().isPrimitive();
    }

    @Override
    public String toString() {
        try {
            return UNPRETTY.toString(this);
        } catch (IOException e) {
            System.out.println(Exceptions.stackTraceToString(e));
            return "";
        }
    }

    public String toPrettyString() {
        try {
            return PRETTY.toString(this);
        } catch (IOException e) {
            return "";
        }
    }

}
