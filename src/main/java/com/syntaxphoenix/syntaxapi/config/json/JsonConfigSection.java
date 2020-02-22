package com.syntaxphoenix.syntaxapi.config.json;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.reflections.ClassCache;
import com.syntaxphoenix.syntaxapi.utils.config.JsonTools;

/**
 * @author Lauriichen
 *
 */
public class JsonConfigSection extends BaseSection {

	/**
	 *  
	 */
	public JsonConfigSection() {
		super("");
	}

	/**
	 * @param String
	 *            {name}
	 */
	public JsonConfigSection(String name) {
		super(name);
	}

	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseSection#initSection(java.lang.String)
	 */
	@Override
	protected BaseSection initSection(String name) {
		return new JsonConfigSection(name);
	}

	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseSection#isSectionInstance(com.syntaxphoenix.syntaxapi.config.BaseSection)
	 */
	@Override
	protected boolean isSectionInstance(BaseSection section) {
		return section instanceof JsonConfigSection;
	}

	/**
	 * @param JsonObject
	 *            {input json}
	 */
	public void fromJson(JsonObject input) {
		if (input.isJsonNull()) {
			return;
		}
		Set<Entry<String, JsonElement>> set = input.entrySet();
		if (!set.isEmpty()) {
			for (Entry<String, JsonElement> entry : set) {
				JsonElement current = entry.getValue();
				if (current.isJsonNull()) {
					continue;
				}
				if (current.isJsonObject()) {
					JsonObject object = current.getAsJsonObject();
					if (object.get("class") == null) {
						((JsonConfigSection) createSection(entry.getKey())).fromJson(object);
					} else {
						Class<?> clazz = ClassCache.getClass(object.get("class").getAsString());
						if (clazz == null) {
							continue;
						}
						set(entry.getKey(),
								JsonTools.getConfiguredGson().fromJson(object.get("classValue"), clazz));
					}
				} else if (current.isJsonPrimitive()) {
					JsonPrimitive primitive = current.getAsJsonPrimitive();
					if (primitive.isBoolean()) {
						set(entry.getKey(), primitive.getAsBoolean());
					} else if (primitive.isNumber()) {
						set(entry.getKey(), primitive.getAsNumber());
					} else {
						set(entry.getKey(), primitive.getAsString());
					}
				}
			}
		}
	}

	/**
	 * @param String
	 *            {input json}
	 */
	public void fromJsonString(String input) {
		fromJson(JsonTools.readJson(input));
	}

	/**
	 * @return JsonObject {section values as json}
	 */
	public JsonObject toJson() {
		JsonObject object = new JsonObject();
		if (!values.isEmpty()) {
			for (Entry<String, Object> entry : values.entrySet()) {
				Object input = entry.getValue();
				if (input instanceof JsonConfigSection) {
					object.add(entry.getKey(), ((JsonConfigSection) input).toJson());
				} else {
					if (input instanceof Number) {
						object.addProperty(entry.getKey(), (Number) input);
					} else if (input instanceof Boolean) {
						object.addProperty(entry.getKey(), (Boolean) input);
					} else if (input instanceof Character) {
						object.addProperty(entry.getKey(), (Character) input);
					} else if (input instanceof String) {
						object.addProperty(entry.getKey(), (String) input);
					} else if (input instanceof Serializable) {
						JsonObject serialized = new JsonObject();
						JsonElement element = JsonTools.getConfiguredGson().toJsonTree(input);
						serialized.addProperty("class",
								input.getClass().getName().replace(".java", "").replace(".class", ""));
						serialized.add("classValue", element);
						object.add(entry.getKey(), serialized);
					}
				}
			}
		}
		return object;
	}

	/**
	 * @return String {section values as json string}
	 */
	public String toJsonString() {
		return JsonTools.getConfiguredGson().toJson(toJson());
	}

}
