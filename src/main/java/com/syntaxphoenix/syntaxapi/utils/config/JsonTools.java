package com.syntaxphoenix.syntaxapi.utils.config;

import static com.syntaxphoenix.syntaxapi.command.DefaultArgumentIdentifier.DEFAULT;

import java.util.HashMap;
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
import com.syntaxphoenix.syntaxapi.reflections.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflections.Reflect;
import com.syntaxphoenix.syntaxapi.utils.java.Collect.CollectorImpl;
import com.syntaxphoenix.syntaxapi.utils.java.Collect;

public class JsonTools {

	public static final AbstractReflect JSON_PRIMITIVE = new Reflect(JsonPrimitive.class).searchConstructor("value",
			Object.class);
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
	 * @param String {input json}
	 * @return JsonObject {output json}
	 */
	public static JsonObject readJson(String input) {
		return getJsonParser().parse(input).getAsJsonObject();
	}

	public static JsonPrimitive createPrimitive(Object object) {
		return (JsonPrimitive) JSON_PRIMITIVE.init("value", object);
	}

	@SuppressWarnings("unchecked")
	public static Collector<Entry<String, List<String>>, Map<String, JsonElement>, JsonObject> toJsonObject() {
		return new CollectorImpl<>((Supplier<Map<String, JsonElement>>) HashMap::new, (output, entry) -> {
			List<String> list = entry.getValue();
			if (list.size() == 1) {
				output.put(entry.getKey(), toJsonPrimitive(list.get(0)));
				return;
			}
			output.put(entry.getKey(), list.stream().collect(toJsonArray()));
		}, (left, right) -> {
			left.putAll(right);
			return left;
		}, map -> {
			JsonObject object = new JsonObject();
			((Map<String, JsonElement>) JSON_OBJECT.getFieldValue("map", object)).putAll(map);
			return object;
		}, Collect.CH_ID);
	}

	public static Collector<String, JsonArray, JsonArray> toJsonArray() {
		return new CollectorImpl<>((Supplier<JsonArray>) JsonArray::new,
				(output, value) -> output.add(toJsonPrimitive(value)), (array1, array2) -> {
					array1.addAll(array2);
					return array1;
				}, Collect.passthrough(), Collect.CH_ID);
	}

	public static JsonPrimitive toJsonPrimitive(String value) {
		return createPrimitive(DEFAULT.process(value).get(0).asObject());
	}

}
