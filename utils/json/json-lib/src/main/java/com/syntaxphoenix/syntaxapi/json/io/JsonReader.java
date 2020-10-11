package com.syntaxphoenix.syntaxapi.json.io;

import static com.syntaxphoenix.syntaxapi.json.io.JsonParser.*;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

import com.syntaxphoenix.syntaxapi.json.JsonArray;

public class JsonReader {

	public final Reader reader;

	public final JsonArray values = new JsonArray();

	public final char[] buffer = new char[4096];

	public final Stack<JsonScope> stack = new Stack<>();
	public JsonToken token;

	public int limit;
	public int cursor;

	public int lineAmount;
	public int lineCurrent;

	public JsonReader(Reader reader) throws IOException {
		this.reader = reader;
		reader.reset();
		stack.push(JsonScope.EMPTY_READER);
	}

	protected JsonArray parse() throws IOException, JsonSyntaxException {
		while (nextToken()) {
			switch (token) {
			case START_OBJECT:
				break;
			case END_ARRAY:
				break;
			case END_OBJECT:
				break;
			case KEY:
				break;
			case SEPERATOR:
				break;
			case START_ARRAY:
				break;
			case VALUE:
				break;
			case EOF:
				return values;
			}
		}
		return values;
	}

	protected boolean nextToken() throws IOException {
		JsonScope current = stack.pop();
		switch (current) {
		case EMPTY_OBJECT:
		case FILLED_OBJECT:
			stack.push(JsonScope.PENDING_NAME);
			break;
		case EMPTY_ARRAY:
			stack.push(JsonScope.FILLED_ARRAY);
			break;
		case FILLED_ARRAY:
			break;
		case EMPTY_READER:
			stack.push(JsonScope.FILLED_READER);
			break;
		case FILLED_READER:
			char character = nextCharacter();
			break;
		case PENDING_NAME:
			break;
		case CLOSED:
			return false;
		}
	}

	protected char nextCharacter() throws IOException, EndOfFileException {
		char[] buffer = this.buffer;
		int position = cursor;
		int limit = this.limit;
		while (true) {
			if (position == limit) {
				cursor = position;
				if (!readToBuffer(1)) {
					break;
				}
				position = cursor;
				limit = this.limit;
			}
			char current = buffer[position++];
			switch (current) {
			case '\n':
				lineAmount++;
				lineCurrent = position;
			case ' ':
			case '\r':
			case '\t':
				continue;
			case '/':
				cursor = position;
				if (position == 1) {
					cursor--;
					boolean loaded = readToBuffer(2);
					cursor++;
					if (!loaded) {
						return current;
					}
				}
				char test = buffer[cursor];
				switch (test) {
				case '*':
					cursor++;
					if (skipTo(INDICATOR_EOC)) {
						throw new JsonSyntaxException("Never ending comment");
					}
					continue;
				case '/':
					cursor++;
					skipTo(INDICATOR_EOL);
					position = cursor;
					limit = this.limit;
					continue;
				default:
					return current;
				}
			case '#':
				cursor = position;
				skipTo(INDICATOR_EOL);
				position = cursor;
				limit = this.limit;
				continue;
			default:
				return current;
			}
		}
		throw new EndOfFileException();
	}

	protected boolean skipTo(int indicator) throws IOException {
		switch (indicator) {
		case INDICATOR_EOL:
			while (cursor < limit || readToBuffer(1)) {
				char current = buffer[cursor++];
				switch (current) {
				case '\n':
					lineAmount++;
					lineCurrent = cursor;
				case '\r':
					break;
				default:
					continue;
				}
				break;
			}
			return true;
		case INDICATOR_EOC:
			loop:
			for (; cursor + 2 <= limit || readToBuffer(2); cursor++) {
				if (buffer[cursor] == '\n') {
					lineAmount++;
					lineCurrent = cursor + 1;
					continue;
				}
				if (buffer[cursor] != '*' || buffer[cursor + 1] != '/') {
					continue loop;
				}
				return true;
			}
			return false;
		default:
			return true;
		}
	}

	protected boolean readToBuffer(int minimum) throws IOException {
		char[] buffer = this.buffer;
		lineCurrent -= cursor;
		if (limit != cursor) {
			limit -= cursor;
			System.arraycopy(buffer, cursor, buffer, 0, limit);
		} else {
			limit = 0;
		}

		cursor = 0;
		int total;
		while ((total = reader.read(buffer, limit, buffer.length - limit)) != -1) {
			limit += total;

			if (lineAmount == 0 && lineCurrent == 0 && limit > 0 && buffer[0] == '\ufeff') {
				cursor++;
				lineCurrent++;
				minimum++;
			}

			if (limit >= minimum) {
				return true;
			}
		}
		return false;
	}

}
