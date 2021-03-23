package com.syntaxphoenix.syntaxapi.config.json;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LazilyParsedNumber;
import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.reflection.ClassCache;
import com.syntaxphoenix.syntaxapi.utils.json.JsonTools;
import com.syntaxphoenix.syntaxapi.utils.json.ValueIdentifier;

/**
 * @author Lauriichen
 *
 */
public class JsonConfigSection extends BaseSection {

    public JsonConfigSection() {
        super("");
    }

    public JsonConfigSection(String name) {
        super(name);
    }

    @Override
    protected BaseSection initSection(String name) {
        return new JsonConfigSection(name);
    }

    @Override
    protected boolean isSectionInstance(BaseSection section) {
        return section instanceof JsonConfigSection;
    }

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
                        set(entry.getKey(), JsonTools.getConfiguredGson().fromJson(object.get("classValue"), clazz));
                    }
                    continue;
                }
                if (current.isJsonPrimitive()) {
                    JsonPrimitive primitive = current.getAsJsonPrimitive();
                    if (primitive.isBoolean()) {
                        set(entry.getKey(), primitive.getAsBoolean());
                        continue;
                    }
                    if (primitive.isNumber()) {
                        Number number = primitive.getAsNumber();
                        if (number instanceof LazilyParsedNumber) {
                            set(entry.getKey(), ValueIdentifier.identify(number.toString()));
                            continue;
                        }
                        if (number instanceof Byte) {
                            set(entry.getKey(), number.byteValue());
                            continue;
                        }
                        if (number instanceof Short) {
                            set(entry.getKey(), number.shortValue());
                            continue;
                        }
                        if (number instanceof Integer) {
                            set(entry.getKey(), number.intValue());
                            continue;
                        }
                        if (number instanceof Long) {
                            set(entry.getKey(), number.longValue());
                            continue;
                        }
                        if (number instanceof Float) {
                            set(entry.getKey(), number.floatValue());
                            continue;
                        }
                        if (number instanceof Double) {
                            set(entry.getKey(), number.doubleValue());
                            continue;
                        }
                        continue;
                    }
                    if (primitive.isString()) {
                        set(entry.getKey(), primitive.getAsString());
                        continue;
                    }
                    set(entry.getKey(), ValueIdentifier.identify(JsonTools.getContent(primitive).toString()));
                    continue;
                }
            }
        }
    }

    public void fromJsonString(String input) {
        fromJson(JsonTools.readJson(input));
    }

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
                        serialized.addProperty("class", input.getClass().getName().replace(".java", "").replace(".class", ""));
                        serialized.add("classValue", element);
                        object.add(entry.getKey(), serialized);
                    }
                }
            }
        }
        return object;
    }

    public String toJsonString() {
        return JsonTools.getConfiguredGson().toJson(toJson());
    }

}
