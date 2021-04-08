package com.syntaxphoenix.syntaxapi.test.json;

import java.io.IOException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;

public class JsonTest {

    @Test
    public void tryJsonWriter() throws IOException {
        
        System.out.println("== Write Test ==\n");

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
        
        System.out.println("\n== Write Test ==");

    }

    @Test
    public void tryJsonReader() throws Exception {
        
        System.out.println("== Read Test ==\n");

        JsonParser parser = new JsonParser();

        JsonValue<?> value = parser.fromString("{\"test\":\"Ein wert\",\"test2\":397598,\"test3\":[\"Werte1\",\"Werte2\",[],{}]}");

        JsonWriter writer = new JsonWriter().setPretty(true).setIndent(2).setSpaces(true);

        writer.toStream(value, System.out);
        
        System.out.println("\n== Read Test ==");

    }

    @Test
    public void tryRealExample() throws Exception {
        
        System.out.println("== Skin Test ==\n");

        String name = "Lauriichan";
        String rawId = MojangProfileServer.getUniqueIdString(name);
        if(rawId.isEmpty()) {
            return;
        }
        Skin skin = MojangProfileServer.getSkinShorten(name, rawId);
        
        System.out.println(new String(Base64.getDecoder().decode(skin.getValue())));
        
        System.out.println("\n== Skin Test ==");

    }

}
