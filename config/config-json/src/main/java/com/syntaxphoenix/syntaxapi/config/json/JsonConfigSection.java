package com.syntaxphoenix.syntaxapi.config.json;

import java.util.Map.Entry;

import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonEntry;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;

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

    public void fromJson(JsonValue<?> input) {
        if (input.getType() != ValueType.OBJECT) {
            return;
        }
        fromObject((JsonObject) input);
    }

    private void fromObject(JsonObject object) {
        for (JsonEntry<?> entry : object) {
            switch (entry.getType()) {
            case ARRAY:
                set(entry.getKey(), JsonHelper.convertToList((JsonArray) entry.getValue()));
                break;
            case OBJECT:
                ((JsonConfigSection) createSection(entry.getKey())).fromObject((JsonObject) entry.getValue());
                break;
            case NULL:
                set(entry.getKey(), null);
                break;
            case JSON:
                continue;
            default:
                set(entry.getKey(), entry.getValue().getValue());
            }
        }
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        if (!values.isEmpty()) {
            for (Entry<String, Object> entry : values.entrySet()) {
                Object input = entry.getValue();
                if (input instanceof JsonConfigSection) {
                    object.set(entry.getKey(), ((JsonConfigSection) input).toJson());
                    continue;
                }
                object.set(entry.getKey(), JsonHelper.from(input));
            }
        }
        return object;
    }

}
