package com.syntaxphoenix.syntaxapi.config.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonEntry;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;

public final class JsonHelper {

    private JsonHelper() {}

    @SuppressWarnings({
        "rawtypes",
        "unchecked"
    })
    public static ArrayList convertToList(JsonArray array) {
        ArrayList list = new ArrayList();
        for (JsonValue<?> value : array) {
            switch (value.getType()) {
            case ARRAY:
                list.add(convertToList((JsonArray) value));
                break;
            case OBJECT:
                list.add(convertToMap((JsonObject) value));
                break;
            case NULL:
                list.add(null);
                break;
            case JSON:
                continue;
            default:
                list.add(value.getValue());
            }
        }
        return list;
    }

    @SuppressWarnings({
        "rawtypes",
        "unchecked"
    })
    public static HashMap convertToMap(JsonObject object) {
        HashMap map = new HashMap();
        for (JsonEntry<?> entry : object) {
            switch (entry.getType()) {
            case ARRAY:
                map.put(entry.getKey(), convertToList((JsonArray) entry.getValue()));
                break;
            case OBJECT:
                map.put(entry.getKey(), convertToMap((JsonObject) entry.getValue()));
                break;
            case NULL:
                map.put(entry.getKey(), null);
                break;
            case JSON:
                continue;
            default:
                map.put(entry.getKey(), entry.getValue().getValue());
            }
        }
        return map;
    }

    public static JsonObject addValuesTo(Map<String, ?> map, JsonObject object) {
        for (Entry<String, ?> entry : map.entrySet()) {
            object.set(entry.getKey(), from(entry.getValue()));
        }
        return object;
    }

    public static JsonValue<?> addValuesTo(Iterator<?> collection, JsonArray array) {
        while (collection.hasNext()) {
            array.add(from(collection.next()));
        }
        if (array.size() == 1) {
            return array.get(0);
        }
        return array;
    }

    public static JsonValue<?> addValuesTo(Collection<?> collection, JsonArray array) {
        for (Object value : collection) {
            array.add(from(value));
        }
        if (array.size() == 1) {
            return array.get(0);
        }
        return array;
    }

    public static JsonValue<?> addValuesTo(Object[] collection, JsonArray array) {
        for (Object value : collection) {
            array.add(from(value));
        }
        if (array.size() == 1) {
            return array.get(0);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public static JsonValue<?> from(Object object) {
        JsonValue<?> value = JsonValue.fromPrimitive(object);
        if (value.getType() != ValueType.NULL && object != null) {
            return value;
        }
        if (object instanceof Map) {
            return addValuesTo((Map<String, ?>) object, new JsonObject());
        }
        if (object instanceof Collection) {
            return addValuesTo((Collection<?>) object, new JsonArray());
        }
        if (object instanceof Iterator) {
            return addValuesTo((Iterator<?>) object, new JsonArray());
        }
        if (object.getClass().isArray()) {
            return addValuesTo((Object[]) object, new JsonArray());
        }
        return value;
    }

}
