package com.syntaxphoenix.syntaxapi.test.json;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;
import com.syntaxphoenix.syntaxapi.json.value.JsonInteger;
import com.syntaxphoenix.syntaxapi.json.value.JsonString;

public class JsonValueTest {

    @Test
    public void tryJsonWriter() throws IOException {

        System.out.println("== Write Test ==\n");

        JsonWriter writer = new JsonWriter().setIndent(2).setSpaces(true);
        
        writer.toStream(new JsonInteger(152), System.out);
        writer.toStream(new JsonString("Test"), System.out);

        System.out.println("\n== Write Test ==");

    }

}
