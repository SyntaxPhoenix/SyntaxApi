package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.syntaxphoenix.syntaxapi.nbt.*;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public final class MojangsonParser {

	private static final Pattern DOUBLE_NS = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?",
			Pattern.CASE_INSENSITIVE),
			DOUBLE_S = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d",
					Pattern.CASE_INSENSITIVE),
			FLOAT = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", Pattern.CASE_INSENSITIVE),
			BYTE = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", Pattern.CASE_INSENSITIVE),
			LONG = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", Pattern.CASE_INSENSITIVE),
			SHORT = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", Pattern.CASE_INSENSITIVE),
			INT = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");

	private final String str;
	private int index;

	public static NbtNamedTag parse(String mson) throws MojangsonParseException {
		return new MojangsonParser(mson).parseRootCompound();
	}

	private MojangsonParser(String str) {
		this.str = str;
	}

	// PARSE

	private NbtNamedTag parseRootCompound() throws MojangsonParseException {
		String name;
		NbtCompound result;

		skipWhitespace();
		if (!hasNext()) {
			throw parseException("Expected key");
		}
		if (currentChar() == '{') {
			name = "";
			result = parseCompound();
		} else {
			name = currentChar() == '"' ? parseQuotedString() : parseSimpleString();
			expectChar(':');
			result = parseCompound();
		}

		expectNoTrail();

		return new NbtNamedTag(name, result);
	}

	private String parseCompoundKey() throws MojangsonParseException {
		skipWhitespace();
		if (!hasNext()) {
			throw parseException("Expected key");
		}
		return currentChar() == '"' ? parseQuotedString() : parseSimpleString();
	}

	private NbtTag parseStringOrLiteral() throws MojangsonParseException {
		skipWhitespace();
		if (currentChar() == '"')
			return new NbtString(parseQuotedString());
		String str = parseSimpleString();
		if (str.isEmpty())
			throw parseException("Expected value");
		return parseLiteral(str);
	}

	private NbtTag parseLiteral(String str) {
		try {
			if (FLOAT.matcher(str).matches()) {
				return new NbtFloat(Float.parseFloat(str.substring(0, str.length() - 1)));
			}
			if (BYTE.matcher(str).matches()) {
				return new NbtByte(Byte.parseByte(str.substring(0, str.length() - 1)));
			}
			if (LONG.matcher(str).matches()) {
				return new NbtLong(Long.parseLong(str.substring(0, str.length() - 1)));
			}
			if (SHORT.matcher(str).matches()) {
				return new NbtShort(Short.parseShort(str.substring(0, str.length() - 1)));
			}
			if (INT.matcher(str).matches()) {
				return new NbtInt(Integer.parseInt(str));
			}
			if (DOUBLE_S.matcher(str).matches()) {
				return new NbtDouble(Double.parseDouble(str.substring(0, str.length() - 1)));
			}
			if (DOUBLE_NS.matcher(str).matches()) {
				return new NbtDouble(Double.parseDouble(str));
			}
			if ("true".equalsIgnoreCase(str)) {
				return new NbtByte((byte) 1);
			}
			if ("false".equalsIgnoreCase(str)) {
				return new NbtByte((byte) 0);
			}
		} catch (NumberFormatException ex) {
		}
		if (Strings.isNumeric(str)) {
			return new NbtBigInt(str);
		}
		if(Strings.isDecimal(str)) {
			return new NbtBigDecimal(str);
		}
		return new NbtString(str);
	}

	private String parseQuotedString() throws MojangsonParseException {
		int j = ++this.index;
		StringBuilder builder = null;
		boolean escape = false;

		while (hasNext()) {
			char c = nextChar();
			if (escape) {
				if ((c != '\\') && (c != '"')) {
					throw parseException("Invalid escape of '" + c + "'");
				}
				escape = false;
			} else {
				if (c == '\\') {
					escape = true;
					if (builder != null) {
						continue;
					}
					builder = new StringBuilder(this.str.substring(j, this.index - 1));
					continue;
				}
				if (c == '"') {
					return builder == null ? this.str.substring(j, this.index - 1) : builder.toString();
				}
			}
			if (builder != null) {
				builder.append(c);
			}
		}
		throw parseException("Missing termination quote");
	}

	private String parseSimpleString() {
		int j = this.index;
		while (hasNext() && isSimpleChar(currentChar())) {
			this.index += 1;
		}
		return this.str.substring(j, this.index);
	}

	private NbtTag parseAnything() throws MojangsonParseException {
		skipWhitespace();
		if (!hasNext())
			throw parseException("Expected value");

		int c = currentChar();
		if (c == '{')
			return parseCompound();
		else if (c == '[')
			return parseDetectedArray();
		else
			return parseStringOrLiteral();
	}

	private NbtTag parseDetectedArray() throws MojangsonParseException {
		if (hasCharsLeft(2) && getChar(1) != '"' && getChar(2) == ';') {
			return parseNumArray();
		}
		return parseList();
	}

	private NbtCompound parseCompound() throws MojangsonParseException {
		expectChar('{');

		NbtCompound compound = new NbtCompound();

		skipWhitespace();
		while ((hasNext()) && (currentChar() != '}')) {
			String str = parseCompoundKey();
			if (str.isEmpty()) {
				throw parseException("Expected non-empty key");
			}
			expectChar(':');

			compound.set(str, parseAnything());
			if (!advanceToNextArrayElement()) {
				break;
			}
			if (!hasNext()) {
				throw parseException("Expected key");
			}
		}
		expectChar('}');

		return compound;
	}

	@SuppressWarnings("unchecked")
	private NbtList<?> parseList() throws MojangsonParseException {
		expectChar('[');

		skipWhitespace();
		if (!hasNext()) {
			throw parseException("Expected value");
		}
		NbtList<NbtTag> list = null;
		;
		NbtType listType = null;

		while (currentChar() != ']') {
			NbtTag element = parseAnything();
			NbtType elementType = element.getType();

			if (listType == null) {
				listType = elementType;
			} else if (elementType != listType) {
				throw parseException("Unable to insert " + elementType + " into ListTag of type " + listType);
			}
			if (list == null) {
				list = (NbtList<NbtTag>) NbtList.createFromType(elementType);
			}
			list.add(element);
			if (!advanceToNextArrayElement()) {
				break;
			}
			if (!hasNext()) {
				throw parseException("Expected value");
			}
		}
		expectChar(']');

		return list;
	}

	private NbtTag parseNumArray() throws MojangsonParseException {
		expectChar('[');
		char arrayType = nextChar();
		expectChar(';');
		// nextChar(); semicolon ignored by Mojang

		skipWhitespace();
		if (!hasNext()) {
			throw parseException("Expected value");
		}
		if (arrayType == 'B')
			return new NbtByteArray(parseNumArray(NbtType.BYTE_ARRAY, NbtType.BYTE));
		else if (arrayType == 'L')
			return new NbtLongArray(parseNumArray(NbtType.LONG_ARRAY, NbtType.LONG));
		else if (arrayType == 'I')
			return new NbtIntArray(parseNumArray(NbtType.INT_ARRAY, NbtType.INT));
		throw parseException("Invalid array type '" + arrayType + "' found");
	}

	private Number[] parseNumArray(NbtType arrayType, NbtType primType) throws MojangsonParseException {
		List<Number> result = new ArrayList<>();
		while (currentChar() != ']') {
			NbtTag element = parseAnything();
			NbtType elementType = element.getType();

			if (elementType != primType) {
				throw parseException("Unable to insert " + elementType + " into " + arrayType);
			}
			if (primType == NbtType.BYTE) {
				result.add(((NbtByte) element).getValue());
			} else if (primType == NbtType.LONG) {
				result.add(((NbtLong) element).getValue());
			} else {
				result.add(((NbtInt) element).getValue());
			}
			if (!advanceToNextArrayElement()) {
				break;
			}
			if (!hasNext()) {
				throw parseException("Expected value");
			}
		}
		expectChar(']');

		return result.toArray(new Number[result.size()]);
	}

	// CHARACTER NAVIGATION

	private boolean advanceToNextArrayElement() {
		skipWhitespace();
		if (hasNext() && currentChar() == ',') {
			this.index += 1;
			skipWhitespace();
			return true;
		}
		return false;
	}

	private void skipWhitespace() {
		while (hasNext() && Character.isWhitespace(currentChar())) {
			this.index += 1;
		}
	}

	private boolean hasCharsLeft(int paramInt) {
		return this.index + paramInt < this.str.length();
	}

	private boolean hasNext() {
		return hasCharsLeft(0);
	}

	/**
	 * Returns the character in the string at the current index plus a given offset.
	 *
	 * @param offset the offset
	 * @return the character at the offset
	 */
	private char getChar(int offset) {
		return this.str.charAt(this.index + offset);
	}

	/**
	 * Returns the current character.
	 *
	 * @return the current character
	 */
	private char currentChar() {
		return getChar(0);
	}

	/**
	 * Returns the current character and increments the index.
	 *
	 * @return the current character
	 */
	private char nextChar() {
		return this.str.charAt(this.index++);
	}

	// UTIL

	/**
	 * Verifies whether the current character is of given value and whether the
	 * parser can advance. If these conditions are met, the parser advances by one.
	 * If these conditions are not met, an exception is thrown.
	 *
	 * @param c the expected character
	 * @throws MojangsonParseException if {@link #currentChar()} does not equal
	 *                                 {@code c} or if {@link #hasNext()} returns
	 *                                 false
	 */
	private void expectChar(char c) throws MojangsonParseException {
		skipWhitespace();

		boolean hasNext = hasNext();
		if (hasNext && currentChar() == c) {
			this.index += 1;
			return;
		}
		throw new MojangsonParseException(
				"Expected '" + c + "' but got '" + (hasNext ? Character.valueOf(currentChar()) : "<EOF>") + "'",
				this.str, this.index + 1);
	}

	/**
	 * Verifies that the string has ended or that all characters from the next
	 * character on only consists of whitespace.
	 *
	 * @throws MojangsonParseException if the following characters contain a
	 *                                 non-whitespace character
	 */
	private void expectNoTrail() throws MojangsonParseException {
		skipWhitespace();
		if (hasNext()) {
			this.index++;
			throw parseException("Trailing data " + currentChar() + " found");
		}
	}

	private MojangsonParseException parseException(String paramString) {
		return new MojangsonParseException(paramString, this.str, this.index);
	}

	private static boolean isSimpleChar(char paramChar) {
		return (paramChar >= '0' && paramChar <= '9') || (paramChar >= 'A' && paramChar <= 'Z')
				|| (paramChar >= 'a' && paramChar <= 'z') || paramChar == '_' || paramChar == '-' || paramChar == '.'
				|| paramChar == '+';
	}

}
