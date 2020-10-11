package com.syntaxphoenix.syntaxapi.json.io;

import java.io.IOException;
import java.io.Reader;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.value.*;
import com.syntaxphoenix.syntaxapi.utils.io.TextDeserializer;

public class JsonParser implements TextDeserializer<JsonValue<?>> {

	@Override
	public JsonValue<?> fromReader(Reader reader) throws IOException, JsonSyntaxException {
		return read(new JsonReader(reader));
	}
	
	protected JsonValue<?> read(JsonReader reader) throws IOException, JsonSyntaxException {
		JsonToken token = reader.next();
		switch(token) {
		case NULL:
			return JsonNull.get();
		case BOOLEAN:
			return new JsonBoolean(false);
		case STRING:
			return new JsonString(null);
		case START_ARRAY:
			JsonArray array = new JsonArray();
			while (reader.hasNext()) {
				array.add(read(reader));
			}
		case START_OBJECT:
			JsonObject object = new JsonObject();
	        while (reader.hasNext()) {
	        	
	        }
	        return object;
		case BYTE:
			break;
		case SHORT:
			break;
		case INTEGER:
			break;
		case LONG:
			break;
		case BIG_INTEGER:
			break;
		case FLOAT:
			break;
		case DOUBLE:
			break;
		case BIG_DECIMAL:
			break;
		case EOF:
		case KEY:
		case NUMBER:
		case END_ARRAY:
		case END_OBJECT:
		default:
			throw new IllegalArgumentException();
		}
	}

}
