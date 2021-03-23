package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.syntaxphoenix.syntaxapi.nbt.NbtBigDecimal;
import com.syntaxphoenix.syntaxapi.nbt.NbtBigInt;
import com.syntaxphoenix.syntaxapi.nbt.NbtByte;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtNumber;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class NbtJsonParser {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Object fromJson(JsonElement element, Class<?> classOfT) {
        return GSON.fromJson(element, classOfT);
    }

    /**
     * Convert nbt to json and then serialize it with
     * {@code fromJson(JsonElement, Class<?>)}
     * 
     * @param tag  - Nbt to convert
     * @param type - Type of in nbt serialized object
     * 
     * @return the object
     */
    public static Object fromNbt(NbtTag tag, Class<?> type) {
        return fromJson(toJson(tag), type);
    }

    /**
     * Serialize an object to json
     * 
     * @param object - Object to serialize
     * 
     * @return json that was created
     */
    public static JsonElement toJson(Object object) {
        if (object instanceof JsonElement) {
            return (JsonElement) object;
        } else if (object instanceof NbtTag) {
            return toJson((NbtTag) object);
        }
        return GSON.toJsonTree(object);
    }

    /**
     * Serialize an object to nbt
     * 
     * @param object - Object to serialize
     * 
     * @return nbt that was created
     */
    public static NbtTag toNbt(Object object) {
        if (object instanceof JsonElement) {
            return toNbt((JsonElement) object);
        } else if (object instanceof NbtTag) {
            return (NbtTag) object;
        }
        return toNbt(toJson(object));
    }

    /**
     * Convert json to nbt
     * 
     * @param element - json element that should be converted
     * 
     * @return resulting nbt tag
     */
    public static NbtTag toNbt(JsonElement element) {
        if (element.isJsonObject()) {
            return toNbtCompound(element.getAsJsonObject());
        } else if (element.isJsonArray()) {
            return toNbtList(element.getAsJsonArray());
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return new NbtByte((byte) (primitive.getAsBoolean() ? 1 : 0));
            } else if (primitive.isString()) {
                String input = primitive.getAsString();
                if (Strings.isNumeric(input)) {
                    return new NbtBigInt(input);
                } else if (Strings.isDecimal(input)) {
                    return new NbtBigDecimal(input);
                }
                return new NbtString(input);
            } else if (primitive.isNumber()) {
                return NbtNumber.fromNumber(primitive.getAsNumber());
            }
        }
        return null;
    }

    /**
     * Convert a JsonObject to an NbtCompound
     * 
     * @param object - JsonObject that should be converted
     * 
     * @return resulting NbtCompound
     */
    public static NbtCompound toNbtCompound(JsonObject object) {
        NbtCompound compound = new NbtCompound();
        if (object.size() == 0) {
            return compound;
        }
        for (String key : object.keySet()) {
            NbtTag tag = toNbt(object.get(key));
            if (tag == null) {
                continue;
            }
            compound.set(key, tag);
        }
        return compound;
    }

    /**
     * Convert a JsonArray to an NbtList
     * 
     * @param array - JsonArray that should be converted
     * 
     * @return resulting NbtList
     */
    public static NbtList<?> toNbtList(JsonArray array) {
        NbtList<?>[] lists = toNbtLists(array);
        if (lists.length == 0) {
            return new NbtList<>();
        } else if (lists.length == 1) {
            return lists[0];
        }
        NbtList<NbtList<?>> list = new NbtList<>();
        for (NbtList<?> add : lists) {
            list.add(add);
        }
        return list;
    }

    /**
     * Convert a JsonArray to an NbtList Array
     * 
     * @param array - JsonArray that should be converted
     * 
     * @return resulting NbtList Array that contains a List for each NbtType
     */
    public static NbtList<?>[] toNbtLists(JsonArray array) {
        if (array.size() == 0) {
            return new NbtList[0];
        }
        HashMap<NbtType, NbtList<?>> tags = new HashMap<>();
        for (int index = 0; index < array.size(); index++) {
            NbtTag tag = toNbt(array.get(index));
            NbtType type = tag.getType();
            if (tags.containsKey(type)) {
                tags.get(type).addTag(tag);
            } else {
                NbtList<?> list = new NbtList<>();
                list.addTag(tag);
                tags.put(type, list);
            }
        }
        NbtList<?>[] output = tags.values().toArray(new NbtList[0]);
        tags.clear();
        return output;
    }

    /**
     * Convert nbt to json
     * 
     * @param tag - nbt that should be converted
     * 
     * @return resulting json
     */
    public static JsonElement toJson(NbtTag tag) {
        if (tag instanceof NbtCompound) {
            return toJsonObject((NbtCompound) tag);
        } else if (tag instanceof NbtList) {
            return toJsonArray((NbtList<?>) tag);
        }
        return GSON.toJsonTree(tag.getValue());
    }

    /**
     * Convert an NbtCompound to a JsonObject
     * 
     * @param compound - NbtCompound that should be converted
     * 
     * @return resulting JsonObject
     */
    public static JsonObject toJsonObject(NbtCompound compound) {
        JsonObject object = new JsonObject();
        if (compound.size() == 0) {
            return object;
        }
        for (String key : compound.getKeys()) {
            object.add(key, toJson(compound.get(key)));
        }
        return object;
    }

    /**
     * Convert a NbtList to an JsonArray
     * 
     * @param list - NbtList that should be converted
     * 
     * @return resulting JsonArray
     */
    public static JsonArray toJsonArray(NbtList<?> list) {
        JsonArray array = new JsonArray();
        if (list.isEmpty()) {
            return array;
        }
        for (NbtTag tag : list) {
            array.add(toJson(tag));
        }
        return array;
    }

}
