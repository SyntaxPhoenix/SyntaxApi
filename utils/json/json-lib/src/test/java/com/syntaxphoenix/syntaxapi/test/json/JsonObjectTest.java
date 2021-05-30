package com.syntaxphoenix.syntaxapi.test.json;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.syntaxphoenix.syntaxapi.json.JsonEntry;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.utils.java.lang.StringBuilder;

public class JsonObjectTest {

    @Test
    public void testValueTypeParent() {

        System.out.println("\n== ValueType Parent Test ==\n");

        ValueType[] types = ValueType.values();
        StringBuilder builder = new StringBuilder();
        for (ValueType type : types) {
            System.out.println(builder.append(type.name()).append(" => JSON(").append(type.isType(ValueType.JSON)).append("); NUMBER(")
                .append(type.isType(ValueType.NUMBER)).append(')').toStringClear());
        }

        System.out.println("\n== ValueType Parent Test ==");

    }

    @Test
    public void testObjectHas() {

        System.out.println("\n== Object Has Test ==\n");

        StringBuilder builder = new StringBuilder();
        ValueType[] types = ValueType.values();

        JsonObject object = new JsonObject();
        object.set("string", "string");
        object.set("boolean", false);
        object.set("byte", Byte.MAX_VALUE - 1);
        object.set("short", Short.MAX_VALUE - 1);
        object.set("integer", Integer.MAX_VALUE - 1);
        object.set("long", Long.MAX_VALUE - 1);
        object.set("float", Float.MAX_VALUE - 1);
        object.set("double", Double.MAX_VALUE - 1);

        for (JsonEntry<?> entry : object) {
            for (ValueType type : types) {
                System.out
                    .println(builder.append(entry.getKey()).append(" => ").append(entry.getType().name()).append(" TEST(").append(type.name()).append(") RESULT(").append(object.has(entry.getKey(), type)).append(')').toStringClear());
            }
            System.out.println();
        }

        System.out.println("== Object Has Test ==");

    }

    @Test
    public void testNumberParse() throws IOException {

        System.out.println("\n== Number Parse Test ==\n");

        JsonObject object = new JsonObject();
        object.set("short", Byte.MAX_VALUE + 1);
        object.set("long", Integer.MAX_VALUE + 1);
        object.set("double", Float.MAX_VALUE + 1);
        
        String out = object.toString();
        JsonParser parser = new JsonParser();
        JsonObject val = (JsonObject) parser.fromString(out);
        
        System.out.println(val.get("short").getValue());
        System.out.println(val.get("long").getValue());
        System.out.println(val.get("double").getValue());
        System.out.println();

        System.out.println("== Object Parse Test ==");

    }

}
