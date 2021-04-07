package com.syntaxphoenix.syntaxapi.config.json;

import java.io.File;
import java.io.IOException;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;

/**
 * @author Lauriichen
 *
 */
public class JsonConfig extends JsonConfigSection implements BaseConfig {

    private final JsonParser parser = new JsonParser();
    private final JsonWriter writer = new JsonWriter().setPretty(true);

    @Override
    public void load(File file) throws IOException {
        if (file.exists()) {
            fromJson(parser.fromFile(file));
        }
    }

    @Override
    public void save(File file) throws IOException {
        if (!file.exists()) {
            String parentPath = file.getParent();
            if (parentPath != null && !parentPath.isEmpty()) {
                File parent = file.getParentFile();
                if (parent.exists()) {
                    if (!parent.isDirectory()) {
                        parent.delete();
                        parent.mkdirs();
                    }
                } else {
                    parent.mkdirs();
                }
            }
            file.createNewFile();
        }
        writer.toFile(toJson(), file);
    }

}
