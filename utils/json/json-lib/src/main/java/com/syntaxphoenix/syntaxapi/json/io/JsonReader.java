package com.syntaxphoenix.syntaxapi.json.io;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EmptyStackException;
import java.util.Stack;

public class JsonReader {

	public static final int INDICATOR_EOL = 0;
	public static final int INDICATOR_EOC = 1;

	public static final int LENGTH_BYTE = 3;
	public static final int LENGTH_SHORT = 5;
	public static final int LENGTH_INTEGER = 10;
	public static final int LENGTH_LONG = 19;
	public static final int LENGTH_FLOAT = 21;
	public static final int LENGTH_DOUBLE = 79;

	public static final BigInteger SIZE_BYTE = BigInteger.valueOf(Byte.MAX_VALUE);
	public static final BigInteger SIZE_SHORT = BigInteger.valueOf(Short.MAX_VALUE);
	public static final BigInteger SIZE_INTEGER = BigInteger.valueOf(Integer.MAX_VALUE);
	public static final BigInteger SIZE_LONG = BigInteger.valueOf(Long.MAX_VALUE);

	public static final BigDecimal SIZE_FLOAT = new BigDecimal(Float.MAX_VALUE);
	public static final BigDecimal SIZE_DOUBLE = new BigDecimal(Double.MAX_VALUE);

	public static final int NUMBER_NONE = -1;
	public static final int NUMBER_DIGIT = 0;
	public static final int NUMBER_SIGN = 1;
	public static final int NUMBER_DECIMAL = 2;

	public static final int NUMBER_EXP_IND = 10;
	public static final int NUMBER_EXP_SIGN = 11;
	public static final int NUMBER_EXP_DIGIT = 12;
	public static final int NUMBER_EXP_FRAC = 13;

	public final Reader reader;
	public final char[] buffer = new char[4096];

	public final Stack<JsonScope> stack = new Stack<>();
	public JsonState state;

	public JsonState next;

	public int limit;
	public int cursor;

	public int lineAmount;
	public int lineCurrent;

	public String stringBuffer;

	public JsonReader(Reader reader) throws IOException {
		this.reader = reader;
		reader.reset();
		stack.push(JsonScope.EMPTY_READER);
	}

	/*
	 * Accissible things
	 */

	public JsonToken next() throws IOException, JsonSyntaxException {
		return nextState(false).asToken();
	}

	public boolean hasNext() throws IOException, JsonSyntaxException {
		JsonState state = peekState();
		return state != JsonState.END_OBJECT && state != JsonState.END_ARRAY && state != JsonState.EOF;
	}

	public String readName() throws IOException, JsonSyntaxException {
		JsonState current = currentState();
		String value;
		switch (current) {
		case KEY_SINGLE:
			value = null;
			break;
		case KEY_DOUBLE:
			value = null;
			break;
		default:
			throw illegalState(JsonToken.KEY, current);
		}
		resetState();
		return value;
	}

	public void beginArray() throws IOException, JsonSyntaxException {
		JsonState current = currentState();
		if (current != JsonState.START_ARRAY) {
			throw illegalState(JsonToken.START_ARRAY, current);
		}
		stack.push(JsonScope.EMPTY_ARRAY);
		resetState();
	}

	public void endArray() throws IOException, JsonSyntaxException {
		JsonState current = currentState();
		if (current != JsonState.END_ARRAY) {
			throw illegalState(JsonToken.END_ARRAY, current);
		}
		resetState();
	}

	public void beginObject() throws IOException, JsonSyntaxException {
		JsonState current = currentState();
		if (current != JsonState.START_OBJECT) {
			throw illegalState(JsonToken.START_OBJECT, current);
		}
		stack.push(JsonScope.EMPTY_OBJECT);
		resetState();
	}

	public void endObject() throws IOException, JsonSyntaxException {
		JsonState current = currentState();
		if (current != JsonState.END_OBJECT) {
			throw illegalState(JsonToken.END_OBJECT, current);
		}
		resetState();
	}

	/*
	 * Inner workings
	 */

	protected String readQuotedValue(boolean single) {
		return single ? readSingleQuotedValue() : readDoubleQuotedValue();
	}

	protected String readSingleQuotedValue() {
		char[] buffer = this.buffer;
		StringBuilder builder = null;
		while (true) {
			int position = cursor;
			int limit = this.limit;

			int start = position;
			while (position < limit) {
				char current = buffer[position++];
				switch (current) {
				case '\'':
					cursor = position;
					int length0 = position - start - 1;
					if (builder == null) {
						return new String(buffer, start, length0);
					} else {
						return builder.append(buffer, start, length0).toString();
					}
				case '\\':
					cursor = position;
					int length1 = position - start - 1;
					if(builder == null) {
						int expected = (length1 + 1) * 2;
						builder = new StringBuilder(Math.max(expected, 16));
					}
					builder.append(buffer, start, length1);
				}
			}
		}
	}

	protected String readDoubleQuotedValue() {

	}
	
	protected char readEspacedCharacter() throws IOException {
		if(cursor == limit && !readToBuffer(1)) {
			throw wrongSyntax("Never ending escape sequence");
		}
	}

	protected void resetState() {
		state = null;
	}

	protected JsonState currentState() throws IOException, JsonSyntaxException {
		JsonState current = this.state;
		if (current == null) {
			current = nextState(false);
		}
		return current;
	}

	protected JsonState peekState() throws IOException, JsonSyntaxException {
		return next == null ? next = nextState(true) : next;
	}

	protected JsonState nextState(boolean peek) throws IOException, JsonSyntaxException {
		next = null;
		JsonScope current = peek ? peek() : pop();
		try {
			switch (current) {
			case FILLED_OBJECT:
				char character1 = nextCharacter();
				switch (character1) {
				case '}':
					stack.push(JsonScope.PENDING_NAME);
					return state = JsonState.END_OBJECT;
				case ',':
					break;
				default:
					throw wrongSyntax("Never ending object");
				}
			case EMPTY_OBJECT:
				stack.push(JsonScope.PENDING_NAME);
				char character2 = nextCharacter();
				switch (character2) {
				case '\'':
					return state = JsonState.KEY_SINGLE;
				case '"':
					return state = JsonState.KEY_DOUBLE;
				case '}':
					if (current == JsonScope.EMPTY_OBJECT) {
						wrongSyntax("Unnamed element");
					}
					return state = JsonState.END_OBJECT;
				default:
					throw wrongSyntax("Unnamed element");
				}
			case EMPTY_ARRAY:
				stack.push(JsonScope.FILLED_ARRAY);
				break;
			case FILLED_ARRAY:
				char character3 = nextCharacter();
				switch (character3) {
				case ']':
					return state = JsonState.END_ARRAY;
				case ',':
					break;
				default:
					throw wrongSyntax("Never ending array");
				}
				break;
			case EMPTY_READER:
				stack.push(JsonScope.FILLED_READER);
				break;
			case FILLED_READER:
				nextCharacter();
				cursor--;
				break;
			case PENDING_NAME:
				stack.push(JsonScope.FILLED_OBJECT);
				char character0 = nextCharacter();
				if (character0 != ':') {
					throw wrongSyntax("Expected ':'");
				}
				break;
			case CLOSED:
				return state = JsonState.EOF;
			}

			char character = nextCharacter();
			switch (character) {
			case ']':
				if (current == JsonScope.EMPTY_ARRAY) {
					return state = JsonState.END_ARRAY;
				}
			case ',':
				throw wrongSyntax("Unexpected value");
			case '\'':
				return state = JsonState.VALUE_SINGLE;
			case '"':
				return state = JsonState.VALUE_DOUBLE;
			case '[':
				return state = JsonState.START_ARRAY;
			case '{':
				return state = JsonState.START_OBJECT;
			default:
				cursor--;
			}

			if (isKeyword() || isNumber() || isHexNumber()) {
				return state;
			}

			throw wrongSyntax("No value present");
		} catch (EndOfFileException eof) {
			return state = JsonState.EOF;
		}
	}

	protected boolean isKeyword() throws IOException, JsonSyntaxException {
		char current = buffer[cursor];
		JsonState expect;
		String word;
		String wordUpped;
		switch (current) {
		case 't':
		case 'T':
			word = "true";
			wordUpped = "TRUE";
			expect = JsonState.TRUE;
			break;
		case 'f':
		case 'F':
			word = "false";
			wordUpped = "FALSE";
			expect = JsonState.FALSE;
			break;
		case 'n':
		case 'N':
			word = "null";
			wordUpped = "NULL";
			expect = JsonState.NULL;
			break;
		default:
			return false;
		}

		int length = word.length();
		for (int index = 1; index < length; index++) {
			if (cursor + index >= limit && !readToBuffer(index + 1)) {
				return false;
			}
			char character = buffer[cursor + index];
			if (character != word.charAt(index) && character != wordUpped.charAt(index)) {
				return false;
			}
		}

		if ((cursor + length < limit || readToBuffer(length + 1)) && isLiteral(buffer[cursor + length])) {
			return false;
		}

		cursor += length;
		state = expect;
		return true;
	}

	protected boolean isNumber() throws IOException {
		char[] buffer = this.buffer;
		int position = cursor;
		int limit = this.limit;

		boolean first = true;
		boolean decimal = false;
		boolean negative = false;
		boolean exponential = false;

		int exponentialLength = 0;

		StringBuilder value = new StringBuilder();
		StringBuilder exponentialValue = new StringBuilder();

		int parser = NUMBER_NONE;

		int index = 0;

		characterLoop:
		for (; true; index++) {
			if (position + index == limit) {
				if (!readToBuffer(index + 1)) {
					break;
				}
				position = cursor;
				limit = this.limit;
			}

			char current = buffer[position + index];
			switch (current) {
			case '-':
				switch (parser) {
				case NUMBER_NONE:
					negative = true;
					parser = NUMBER_SIGN;
					value.append(current);
					continue;
				case NUMBER_EXP_IND:
					parser = NUMBER_EXP_SIGN;
					exponentialLength = 2;
					value.append(current);
					continue;
				default:
					return false;
				}
			case '+':
				switch (parser) {
				case NUMBER_NONE:
					parser = NUMBER_SIGN;
					value.append(current);
					continue;
				case NUMBER_EXP_IND:
					parser = NUMBER_EXP_SIGN;
					exponentialLength = 2;
					value.append(current);
					continue;
				default:
					return false;
				}
			case 'e':
			case 'E':
				if (parser != NUMBER_DIGIT || exponential) {
					return false;
				}
				exponential = true;
				parser = NUMBER_EXP_IND;
				exponentialLength = 1;
				value.append(current);
				continue;
			case '.':
				if (parser != NUMBER_DIGIT || decimal) {
					return false;
				}
				decimal = true;
				parser = NUMBER_DECIMAL;
				value.append(current);
				continue;
			default:
				if (isDigit(current)) {
					if (!isLiteral(current)) {
						break characterLoop;
					}
					return false;
				}
				switch (parser) {
				case NUMBER_NONE:
					if (first && current == '0') {
						continue;
					}
					first = false;
					parser = NUMBER_DIGIT;
					value.append(current);
					continue;
				case NUMBER_SIGN:
				case NUMBER_DIGIT:
					value.append(current);
					continue;
				case NUMBER_DECIMAL:
					value.append(current);
					parser = NUMBER_DIGIT;
					continue;
				case NUMBER_EXP_IND:
				case NUMBER_EXP_SIGN:
					value.append(current);
					exponentialValue.append(current);
					parser = NUMBER_EXP_DIGIT;
					continue;
				default:
					return false;
				}
			}
		}
		try {
			stringBuffer = value.toString();
			int length = (((decimal ? stringBuffer.split(".", 2)[0].length() : stringBuffer.length()) - exponentialLength)
				* (10 ^ Integer.parseInt(exponentialValue.toString()))) - (negative ? 0 : 1);

			if (decimal) {
				switch (length) {
				case LENGTH_FLOAT:
					state = isLower(stringBuffer, SIZE_FLOAT, negative) ? JsonState.FLOAT : JsonState.DOUBLE;
					break;
				case LENGTH_DOUBLE:
					state = isLower(stringBuffer, SIZE_DOUBLE, negative) ? JsonState.DOUBLE : JsonState.BIG_DECIMAL;
					break;
				default:
					if (length < LENGTH_FLOAT) {
						state = JsonState.FLOAT;
						break;
					}
					if (length < LENGTH_DOUBLE) {
						state = JsonState.DOUBLE;
						break;
					}
					state = JsonState.BIG_DECIMAL;
				}
			} else {
				switch (length) {
				case LENGTH_BYTE:
					state = isLower(stringBuffer, SIZE_BYTE, negative) ? JsonState.BYTE : JsonState.SHORT;
					break;
				case LENGTH_SHORT:
					state = isLower(stringBuffer, SIZE_SHORT, negative) ? JsonState.SHORT : JsonState.INTEGER;
					break;
				case LENGTH_INTEGER:
					state = isLower(stringBuffer, SIZE_INTEGER, negative) ? JsonState.INTEGER : JsonState.LONG;
					break;
				case LENGTH_LONG:
					state = isLower(stringBuffer, SIZE_LONG, negative) ? JsonState.LONG : JsonState.BIG_INTEGER;
					break;
				default:
					if (length < LENGTH_BYTE) {
						state = JsonState.BYTE;
						break;
					}
					if (length < LENGTH_SHORT) {
						state = JsonState.SHORT;
						break;
					}
					if (length < LENGTH_INTEGER) {
						state = JsonState.INTEGER;
						break;
					}
					if (length < LENGTH_LONG) {
						state = JsonState.LONG;
						break;
					}
					state = JsonState.BIG_INTEGER;
				}
			}
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}

	protected boolean isHexNumber() throws IOException {
		return false;
	}

	protected boolean isLower(String value, BigDecimal comparision, boolean negative) {
		if (negative) {
			return new BigDecimal(value).compareTo(comparision.multiply(BigDecimal.ONE.negate())) == 1;
		}
		return new BigDecimal(value).compareTo(comparision) == -1;
	}

	protected boolean isLower(String value, BigInteger comparision, boolean negative) {
		if (negative) {
			return new BigInteger(value).compareTo(comparision.multiply(BigInteger.ONE.negate())) == 1;
		}
		return new BigInteger(value).compareTo(comparision) == -1;
	}

	protected char nextCharacter() throws IOException, EndOfFileException, JsonSyntaxException {
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
						throw wrongSyntax("Never ending comment");
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

	protected boolean isHexDigit(char character) {
		switch (character) {
		case 'a':
		case 'A':
		case 'b':
		case 'B':
		case 'c':
		case 'C':
		case 'd':
		case 'D':
		case 'e':
		case 'E':
		case 'f':
		case 'F':
			return true;
		default:
			return false;
		}
	}

	protected boolean isDigit(char character) {
		return character >= '0' && character <= '9';
	}

	protected boolean isLiteral(char character) throws JsonSyntaxException {
		switch (character) {
		case '/':
		case '#':
		case '{':
		case '}':
		case '[':
		case ']':
		case ':':
		case ',':
		case ' ':
		case '\t':
		case '\f':
		case '\r':
		case '\n':
			return false;
		case '\\':
		case ';':
		case '=':
			throw wrongSyntax("Unexpected character");
		default:
			return true;
		}
	}

	protected JsonScope pop() {
		try {
			return stack.pop();
		} catch (EmptyStackException exception) {
			return JsonScope.CLOSED;
		}
	}

	protected JsonScope peek() {
		try {
			return stack.peek();
		} catch (EmptyStackException exception) {
			return JsonScope.CLOSED;
		}
	}

	protected JsonSyntaxException wrongSyntax(String message) {
		return new JsonSyntaxException(applyLocation(message));
	}

	protected IllegalStateException illegalState(JsonToken expected, JsonState state) {
		return new IllegalStateException(applyLocation("Expected " + expected.name() + " but was " + state.asToken().name()));
	}

	protected String applyLocation(String message) {
		return message + getLocation();
	}

	protected String getLocation() {
		return " at line " + (lineAmount + 1) + " position " + (cursor - lineCurrent + 1);
	}

}
