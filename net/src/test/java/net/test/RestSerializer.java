package net.test;

import java.io.File;
import java.io.FileWriter;

import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.net.http.CustomRequestData;
import com.syntaxphoenix.syntaxapi.net.http.RequestData;
import com.syntaxphoenix.syntaxapi.net.http.RequestSerializer;
import com.syntaxphoenix.syntaxapi.utils.java.Files;
import com.syntaxphoenix.syntaxapi.utils.json.JsonTools;

public class RestSerializer implements RequestSerializer {

    @Override
    public RequestData<JsonObject> serialize(String data) throws Exception {
        File file = new File("debug", System.currentTimeMillis() + ".txt");
        Files.createFile(file);
        FileWriter writer = new FileWriter(file);
        writer.write(data);
        writer.flush();
        writer.close();
        return new CustomRequestData<>(JsonObject.class, JsonTools.readJson(data));
    }

}
