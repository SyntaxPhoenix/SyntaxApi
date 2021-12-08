package com.syntaxphoenix.syntaxapi.test.json;

import java.io.IOException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;
import com.syntaxphoenix.syntaxapi.test.json.utils.JsonData;
import com.syntaxphoenix.syntaxapi.test.json.utils.MojangProfileServer;
import com.syntaxphoenix.syntaxapi.test.json.utils.Skin;

public class JsonIOTest {

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
    public void tryMojangExample() throws Exception {

        System.out.println("== Skin Test ==\n");

        String name = "Lauriichan";
        String rawId = MojangProfileServer.getUniqueIdString(name);
        if (rawId.isEmpty()) {
            return;
        }
        Skin skin = MojangProfileServer.getSkinShorten(name, rawId);

        System.out.println(new String(Base64.getDecoder().decode(skin.getValue())));

        System.out.println("\n== Skin Test ==");

    }

    @Test
    public void tryPowerExample() throws Exception {

        System.out.println("== Power Test ==\n");

        JsonParser parser = new JsonParser();

        JsonValue<?> value = parser.fromString(
            "{\"StatusSNS\":{\"Time\":\"2021-12-08T20:22:23\",\"ENERGY\":{\"TotalStartTime\":\"2021-07-09T16:56:28\",\"Total\":91.117,\"Yesterday\":0.386,\"Today\":1.274,\"Power\":87,\"ApparentPower\":151,\"ReactivePower\":124,\"Factor\":0.58,\"Voltage\":229,\"Current\":0.662}}}");

        JsonWriter writer = new JsonWriter().setPretty(true).setIndent(2).setSpaces(true);

        writer.toStream(value, System.out);

        System.out.println("\n== Power Test ==");

    }

    @Test
    public void tryNumberParsing() throws Exception {

        System.out.println("\n== Number Test ==");

        JsonParser parser = new JsonParser();

        JsonValue<?> value = parser.fromString("{\"byte\":0,\"short\":" + (Short.MAX_VALUE - 1) + ",\"integer\":" + (Integer.MAX_VALUE - 1)
            + ",\"long\":" + (Long.MAX_VALUE - 1) + ",\"float\":" + (Float.MAX_VALUE - 1) + ",\"double\":" + (Double.MAX_VALUE - 1) + "}");

        JsonWriter writer = new JsonWriter().setPretty(true).setIndent(2).setSpaces(true);

        writer.toStream(value, System.out);

        System.out.println("\n== Number Test ==");

    }

    @Test
    public void tryPrettyExample() throws Exception {

        System.out.println("\n== Pretty Test ==");

        JsonParser parser = new JsonParser();

        JsonValue<?> value = parser.fromString(JsonData.PRETTY_DATA);

        JsonWriter writer = new JsonWriter().setPretty(true).setIndent(2).setSpaces(true);

        writer.toStream(value, System.out);

        System.out.println("\n== Pretty Test ==");

    }

}
