package com.syntaxphoenix.syntaxapi.test.json;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;

public class JsonTest {

    @Test
    public void tryJsonWriter() throws IOException {

        JsonWriter writer = new JsonWriter().setPretty(true).setIndent(2).setSpaces(true);

        JsonObject object = new JsonObject();
        object.set("test", "Ein wert");
        object.set("test2", 397598L);
        JsonArray array = new JsonArray();
        array.add("Werte1");
        array.add("Werte2");
        array.add(new JsonArray());
        array.add(new JsonObject());
        object.set("test3", array);

        writer.toStream(object, System.out);
        System.out.println();

    }

    @Test
    public void tryJsonReader() throws Exception {

        JsonParser parser = new JsonParser();

        JsonValue<?> value = parser.fromString("{\r\n" + "  \"test\": \"Ein wert\",\r\n" + "  \"test2\": 397598,\r\n" + "  \"test3\": [\r\n"
            + "    \"Werte1\",\r\n" + "    \"Werte2\",\r\n" + "    [],\r\n" + "    {}\r\n" + "  ]\r\n" + "}");

        JsonWriter writer = new JsonWriter().setPretty(true).setIndent(2).setSpaces(true);

        writer.toStream(value, System.out);

    }

}
