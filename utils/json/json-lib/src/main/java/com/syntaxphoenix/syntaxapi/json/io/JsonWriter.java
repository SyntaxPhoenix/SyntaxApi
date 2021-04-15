package com.syntaxphoenix.syntaxapi.json.io;

import java.io.IOException;
import java.io.Writer;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonEntry;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.value.JsonBoolean;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;
import com.syntaxphoenix.syntaxapi.json.value.JsonNumber;
import com.syntaxphoenix.syntaxapi.json.value.JsonString;
import com.syntaxphoenix.syntaxapi.utils.io.TextSerializer;

public class JsonWriter implements TextSerializer<JsonValue<?>> {

    public static final int TAB_SPACES = 4;

    private boolean pretty = false;
    private boolean spaces = false;
    private int indent = 1;

    public boolean isPretty() {
        return pretty;
    }

    public JsonWriter setPretty(boolean pretty) {
        this.pretty = pretty;
        return this;
    }

    public boolean usesSpaces() {
        return spaces;
    }

    public JsonWriter setSpaces(boolean spaces) {
        this.spaces = spaces;
        return this;
    }

    public int getIndent() {
        return indent;
    }

    public JsonWriter setIndent(int indent) {
        this.indent = indent;
        return this;
    }

    public JsonWriter setTabIndent(int indent) {
        this.indent = indent * TAB_SPACES;
        return this;
    }

    @Override
    public void toWriter(JsonValue<?> object, Writer writer) throws IOException {
        switch (object.getType()) {
        case ARRAY:
            writeArray((JsonArray) object, writer, 0);
            break;
        case OBJECT:
            writeObject((JsonObject) object, writer, 0);
            break;
        default:
            break;
        }
    }

    public void writeEntry(JsonEntry<?> entry, Writer writer, int depth) throws IOException {
        if (pretty) {
            indent(writer, depth);
        }
        writeUnescapedString(entry.getKey(), writer);
        writer.append(':');
        if (pretty) {
            writer.append(' ');
        }
        writeValue(entry.getValue(), writer, depth);
    }

    public void writeValue(JsonValue<?> value, Writer writer, int depth) throws IOException {
        switch (value.getType()) {
        case NULL:
            writeNull((JsonNull<?>) value, writer);
            break;
        case ARRAY:
            writeArray((JsonArray) value, writer, depth);
            break;
        case OBJECT:
            writeObject((JsonObject) value, writer, depth);
            break;
        case STRING:
            writeString((JsonString) value, writer);
            break;
        case BOOLEAN:
            writeBoolean((JsonBoolean) value, writer);
            break;
        case BYTE:
        case SHORT:
        case INTEGER:
        case LONG:
        case FLOAT:
        case DOUBLE:
        case BIG_INTEGER:
        case BIG_DECIMAL:
            writeNumber((JsonNumber<?>) value, writer);
            break;
        case NUMBER:
            break;
        default:
            break;
        }
    }

    public void writeObject(JsonObject object, Writer writer, int depth) throws IOException {
        writer.append('{');
        int size = object.size();
        if (size != 0) {
            if (pretty) {
                writer.append('\n');
            }
            int current = 0;
            int deep = depth + 1;
            for (JsonEntry<?> entry : object) {
                writeEntry(entry, writer, deep);
                if (++current != size) {
                    writer.append(',');
                }
                if (pretty) {
                    writer.append('\n');
                }
            }
            if (pretty) {
                indent(writer, depth);
            }
        }
        writer.append('}');
    }

    public void writeArray(JsonArray array, Writer writer, int depth) throws IOException {
        writer.append('[');
        int size = array.size();
        if (size != 0) {
            if (pretty) {
                writer.append('\n');
            }
            int current = 0;
            int deep = depth + 1;
            for (JsonValue<?> value : array) {
                if (pretty) {
                    indent(writer, deep);
                }
                writeValue(value, writer, deep);
                if (++current != size) {
                    writer.append(',');
                }
                if (pretty) {
                    writer.append('\n');
                }
            }
            if (pretty) {
                indent(writer, depth);
            }
        }
        writer.append(']');
    }

    public void writeString(JsonString string, Writer writer) throws IOException {
        writeUnescapedString(string.getValue(), writer);
    }

    public void writeNumber(JsonNumber<?> number, Writer writer) throws IOException {
        writer.append(number.getValue().toString());
    }

    public void writeNull(JsonNull<?> jsonNull, Writer writer) throws IOException {
        writer.append("null");
    }

    public void writeBoolean(JsonBoolean jsonBoolean, Writer writer) throws IOException {
        writer.append(jsonBoolean.getValue().toString());
    }

    /*
     * Helpers
     */

    private void indent(Writer writer, int depth) throws IOException {
        int amount = indent * depth;
        char append = spaces ? ' ' : '\t';
        for (int count = 0; count < amount; count++) {
            writer.append(append);
        }
    }

    private void writeUnescapedString(String string, Writer writer) throws IOException {
        // TODO: escape string
        writer.append('"');
        writer.append(string);
        writer.append('"');
    }

}
