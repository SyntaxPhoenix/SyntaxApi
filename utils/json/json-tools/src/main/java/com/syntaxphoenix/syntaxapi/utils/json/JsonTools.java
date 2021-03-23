package com.syntaxphoenix.syntaxapi.utils.json;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;
import com.syntaxphoenix.syntaxapi.utils.java.Collect;
import com.syntaxphoenix.syntaxapi.utils.java.Collect.CollectorImpl;

public class JsonTools {

    public static final AbstractReflect JSON_PRIMITIVE = new Reflect(JsonPrimitive.class).searchConstructor("value", Object.class)
        .searchField("value", "value");
    public static final AbstractReflect JSON_OBJECT = new Reflect(JsonObject.class).searchField("map", "members");

    private static JsonParser parser = new JsonParser();
    private static Gson gson;

    public static Gson getConfiguredGson() {
        if (gson != null) {
            return gson;
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return gson = builder.create();
    }

    public static JsonParser getJsonParser() {
        return parser;
    }

    /**
     * @param input the raw json string
     * @return output the complex json object
     */
    public static JsonObject readJson(String input) {
        return getJsonParser().parse(input).getAsJsonObject();
    }

    public static JsonPrimitive createPrimitive(Object object) {
        if (object instanceof Character) {
            return new JsonPrimitive((Character) object);
        }
        if (object instanceof String) {
            return new JsonPrimitive((String) object);
        }
        if (object instanceof Number) {
            return new JsonPrimitive((Number) object);
        }
        if (object instanceof Boolean) {
            return new JsonPrimitive((Boolean) object);
        }
        return new JsonPrimitive(object.toString());
    }

    public static Object getContent(JsonPrimitive primitive) {
        return JSON_PRIMITIVE.getFieldValue("value", primitive);
    }

    @SuppressWarnings("unchecked")
    public static JsonObject merge(JsonObject output, JsonObject input) {
        ((Map<String, JsonElement>) JSON_OBJECT.getFieldValue("map", output))
            .putAll((Map<String, JsonElement>) JSON_OBJECT.getFieldValue("map", input));
        return output;
    }

    public static Collector<Entry<String, List<String>>, JsonObject, JsonObject> toJsonObject() {
        return new CollectorImpl<Entry<String, List<String>>, JsonObject, JsonObject>((Supplier<JsonObject>) JsonObject::new,
            (output, entry) -> {
                if (entry == null || entry.getKey() == null) {
                    return;
                }
                List<String> list = entry.getValue();
                if (list.size() == 1) {
                    output.add(entry.getKey(), toJsonPrimitive(list.get(0)));
                    return;
                }
                output.add(entry.getKey(), list.stream().collect(toJsonArray()));
            }, (left, right) -> {
                return merge(left, right);
            }, Collect.passthrough(), Collect.CH_ID);
    }

    public static Collector<String, JsonArray, JsonArray> toJsonArray() {
        return new CollectorImpl<>((Supplier<JsonArray>) JsonArray::new, (output, value) -> output.add(toJsonPrimitive(value)),
            (array1, array2) -> {
                array1.addAll(array2);
                return array1;
            }, Collect.passthrough(), Collect.CH_ID);
    }

    public static JsonPrimitive toJsonPrimitive(String value) {
        return createPrimitive(ValueIdentifier.identify(value));
    }

}
