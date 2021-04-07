package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.json.value.*;
import com.syntaxphoenix.syntaxapi.json.*;
import com.syntaxphoenix.syntaxapi.nbt.*;

public class NbtJsonParser {

    /**
     * Convert json to nbt
     * 
     * @param element - json element that should be converted
     * 
     * @return resulting nbt tag
     */
    public static NbtTag toNbt(JsonValue<?> element) {
        if (element == null || element.hasType(ValueType.NULL)) {
            return null;
        }
        switch (element.getType()) {
        case ARRAY:
            return toNbtList((JsonArray) element);
        case OBJECT:
            return toNbtCompound((JsonObject) element);
        case BIG_DECIMAL:
            return new NbtBigDecimal(((JsonBigDecimal) element).getValue());
        case BIG_INTEGER:
            return new NbtBigDecimal(((JsonBigDecimal) element).getValue());
        case BOOLEAN:
            return new NbtByte((byte) (((JsonBoolean) element).getValue() ? 1 : 0));
        case BYTE:
            return new NbtByte(((JsonByte) element).getValue());
        case DOUBLE:
            return new NbtDouble(((JsonDouble) element).getValue());
        case FLOAT:
            return new NbtFloat(((JsonFloat) element).getValue());
        case INTEGER:
            return new NbtInt(((JsonInteger) element).getValue());
        case LONG:
            return new NbtLong(((JsonLong) element).getValue());
        case SHORT:
            return new NbtShort(((JsonShort) element).getValue());
        case STRING:
            return new NbtString(((JsonString) element).getValue());
        default:
            return null;
        }
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
        for (JsonEntry<?> entry : object) {
            NbtTag tag = toNbt(entry.getValue());
            if (tag == null) {
                continue;
            }
            compound.set(entry.getKey(), tag);
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
    public static JsonValue<?> toJson(NbtTag tag) {
        if (tag instanceof NbtCompound) {
            return toJsonObject((NbtCompound) tag);
        } else if (tag instanceof NbtList) {
            return toJsonArray((NbtList<?>) tag);
        }
        return JsonValue.fromPrimitive(tag.getValue());
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
            object.set(key, toJson(compound.get(key)));
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
