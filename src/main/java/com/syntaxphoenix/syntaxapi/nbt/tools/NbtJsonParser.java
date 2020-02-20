package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.syntaxphoenix.syntaxapi.nbt.*;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class NbtJsonParser {
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static JsonElement toJson(Object object) {
		if(object instanceof JsonElement) {
			return (JsonElement) object;
		}
		return GSON.toJsonTree(object);
	}
	
	public static NbtTag toNbt(Object object) {
		if(object instanceof JsonElement) {
			return toNbt((JsonElement) object);
		}
		return toNbt(toJson(object));
	}

	public static NbtTag toNbt(JsonElement element) {
		if (element.isJsonObject()) {
			return toNbtCompound(element.getAsJsonObject());
		} else if (element.isJsonArray()) {
			return toNbtList(element.getAsJsonArray());
		} else if (element.isJsonPrimitive()) {
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if(primitive.isBoolean()) {
				return new NbtByte((byte) (primitive.getAsBoolean() ? 1 : 0));
			} else if(primitive.isString()) {
				String input = primitive.getAsString();
				if(Strings.isNumeric(input)) {
					return new NbtBigInt(input);
				} else if(Strings.isDecimal(input)) {
					return new NbtBigDecimal(input);
				}
				return new NbtString(input);
			} else if(primitive.isNumber()) {
				return NbtNumber.fromNumber(primitive.getAsNumber());
			}
		}
		return null;
	}

	public static NbtCompound toNbtCompound(JsonObject object) {
		NbtCompound compound = new NbtCompound();
		if(object.size() == 0) {
			return compound;
		}
		for(String key : object.keySet()) {
			NbtTag tag = toNbt(object.get(key));
			if(tag == null) {
				continue;
			}
			compound.set(key, tag);
		}
		return compound;
	}

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

	public static NbtList<?>[] toNbtLists(JsonArray array) {
		if (array.size() == 0) {
			return new NbtList[0];
		}
		HashMap<NbtType, NbtList<?>> tags = new HashMap<>();
		for (int index = 0; index < array.size(); index++) {
			NbtTag tag = toNbt(array.get(index));
			NbtType type = tag.getType();
			if(tags.containsKey(type)) {
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
	
	/*
	 * 
	 */
	
	public static JsonElement toJson(NbtTag tag) {
		if(tag instanceof NbtCompound) {
			return toJsonObject((NbtCompound) tag);
		} else if(tag instanceof NbtList) {
			return toJsonArray((NbtList<?>) tag);
		}
		return GSON.toJsonTree(tag.getValue());
	}
	
	public static JsonObject toJsonObject(NbtCompound compound) {
		JsonObject object = new JsonObject();
		if(compound.size() == 0) {
			return object;
		}
		for(String key : compound.getKeys()) {
			object.add(key, toJson(compound.get(key)));
		}
		return object;
	}
	
	public static JsonArray toJsonArray(NbtList<?> list) {
		JsonArray array = new JsonArray();
		if(list.isEmpty()) {
			return array;
		}
		for(NbtTag tag : list) {
			array.add(toJson(tag));
		}
		return array;
	}

}
