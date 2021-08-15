package net.test;

import java.io.File;
import java.io.FileWriter;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.net.http.CustomRequestData;
import com.syntaxphoenix.syntaxapi.net.http.RequestData;
import com.syntaxphoenix.syntaxapi.net.http.RequestTextSerializer;
import com.syntaxphoenix.syntaxapi.utils.java.Files;

public class RestSerializer implements RequestTextSerializer {

    private final JsonParser parser = new JsonParser();

    @Override
    public RequestData<JsonObject> serialize(String data) throws Exception {
        File file = new File("debug", System.currentTimeMillis() + ".txt");
        Files.createFile(file);
        FileWriter writer = new FileWriter(file);
        writer.write(data);
        writer.flush();
        writer.close();
        JsonValue<?> element = parser.fromString(data);
        if (element.getType() != ValueType.OBJECT) {
            return new CustomRequestData<>(JsonObject.class, new JsonObject());
        }
        return new CustomRequestData<>(JsonObject.class, (JsonObject) element);
    }

}
