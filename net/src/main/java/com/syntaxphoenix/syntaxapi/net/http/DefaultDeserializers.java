package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.json.JsonTools;

public class DefaultDeserializers {

    /*
     * Default deserializers
     */

    public static final ContentSerializer PLAIN = null;

    /*
     * Json deserializers
     */

    public static final JsonContentDeserializer JSON = value -> JsonTools.readJson(value);

    public static final JsonContentDeserializer URL_ENCODED = value -> {
        JsonObject output = new JsonObject();
        String[] entries = (value = value.replaceFirst("\\?", "")).contains("&") ? value.split("&")
            : new String[] {
                value
        };
        for (int index = 0; index < entries.length; index++) {
            String current = entries[index];
            if (!current.contains("=")) {
                continue;
            }
            String[] entry = current.split("=", 2);
            output.addProperty(entry[0], entry[1]);
        }
        return output;
    };

}
