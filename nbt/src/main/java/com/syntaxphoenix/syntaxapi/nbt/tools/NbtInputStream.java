package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syntaxphoenix.syntaxapi.nbt.*;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

/**
 * An NBTInputStream extends {@link DataInputStream} by allowing to read named
 * tags.
 */
public final class NbtInputStream extends DataInputStream {

	private final static Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * Creates a new {@code NBTInputStream}, which will source its data from the
	 * specified input stream.
	 * 
	 * @param in the input stream
	 */
	public NbtInputStream(InputStream in) {
		super(in);
	}

	/**
	 * <p>
	 * Reads a tag and its name from the stream.
	 * </p>
	 * <p>
	 * Should the tag be of type {@link NbtType#COMPOUND} or {@link NbtType#LIST}
	 * will the full content (all elements in the compounds or list) be read.
	 * </p>
	 * <p>
	 * Null may be returned if the id of the tag can not be read due to the stream
	 * ending (expected end). If however the stream ends while reading either the
	 * tag name or the tag payload, an {@link IOException} is thrown (unexpected
	 * end).
	 * </p>
	 *
	 * @return the tag that was read or null if EOF is reached
	 * @throws IOException if an I/O error occurs
	 */
	public NbtNamedTag readNamedTag() throws IOException {
		return readNamedTag(0);
	}

	/**
	 * <p>
	 * Reads a tag and its name from the stream.
	 * </p>
	 * <p>
	 * Should the tag be of type {@link NbtType#COMPOUND} or {@link NbtType#LIST}
	 * will the full content (all elements in the compounds or list) be read.
	 * </p>
	 * <p>
	 * Null may be returned if the id of the tag can not be read due to the stream
	 * ending (expected end). If however the stream ends while reading either the
	 * tag name or the tag payload, an {@link IOException} is thrown (unexpected
	 * end).
	 * </p>
	 *
	 * @param depth the depth (used for recursive reading of lists or compounds)
	 * @return the tag that was read or null if EOF is reached
	 * @throws IOException if an I/O error occurs
	 */
	public NbtNamedTag readNamedTag(int depth) throws IOException {
		int id = read();
		if (id == -1)
			return null;
		NbtType type = NbtType.getById((byte) id);

		String name = type != NbtType.END ? readString() : "";

		return new NbtNamedTag(name, readTag(type, depth));
	}

	/**
	 * <p>
	 * Reads the payload of a tag given the type.
	 * </p>
	 * <p>
	 * This method accepts a depth parameter which in necessary for recursive
	 * reading of compounds and lists.
	 * </p>
	 * <p>
	 * The depth parameter indicates what depth the currently called function has,
	 * starting with 0 if this method in being called initially.
	 * </p>
	 * <p>
	 * Should this method be called while reading a compound or list, the depth will
	 * be 1. Should these compounds or lists contain further compounds and lists
	 * will the depth be 2 (and so on).
	 * </p>
	 * 
	 * @param type  the type
	 * @param depth the depth (used for recursive reading of lists or compounds)
	 * @return the tag
	 * @throws IOException if an I/O error occurs.
	 */
	public NbtTag readTag(NbtType type, int depth) throws IOException {
		switch (type) {
		case END:
			return readTagEnd(depth);
		case BYTE:
			return new NbtByte(readByte());
		case SHORT:
			return new NbtShort(readShort());
		case INT:
			return new NbtInt(readInt());
		case LONG:
			return new NbtLong(readLong());
		case FLOAT:
			return new NbtFloat(readFloat());
		case DOUBLE:
			return new NbtDouble(readDouble());
		case BYTE_ARRAY:
			return readTagByteArray();
		case STRING:
			return readTagString();
		case LIST:
			return readTagList(depth);
		case COMPOUND:
			return readTagCompound(depth);
		case INT_ARRAY:
			return readTagIntArray();
		case LONG_ARRAY:
			return readTagLongArray();
		default:
			throw new IOException("invalid tag type: " + type);
		}
	}

	public NbtEnd readTagEnd(int depth) throws IOException {
		if (depth == 0)
			throw new IOException("TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
		return NbtEnd.INSTANCE;
	}

	public NbtByteArray readTagByteArray() throws IOException {
		int length = readInt();
		byte[] bytes = new byte[length];
		readFully(bytes);
		return new NbtByteArray(bytes);
	}

	public NbtString readTagString() throws IOException {
		String input = readString();
		if (Strings.isNumeric(input)) {
			return new NbtBigInt(input);
		}
		return new NbtString(input);
	}

	public NbtList<?> readTagList(int depth) throws IOException {
		NbtType elementType = NbtType.getById(readByte());
		int length = readInt();

		if (elementType == NbtType.END && length > 0)
			throw new IOException("List is of type TAG_End but not empty");

		List<NbtTag> tagList = new ArrayList<>();
		for (int i = 0; i < length; ++i) {
			NbtTag tag = readTag(elementType, depth + 1);
			tagList.add(tag);
		}

		return NbtList.createFromTypeAndFill(elementType, tagList);
	}

	public NbtCompound readTagCompound(int depth) throws IOException {
		Map<String, NbtTag> tagMap = new HashMap<>();
		while (true) {
			NbtNamedTag namedTag = readNamedTag(depth + 1);
			if (namedTag == null)
				throw new IOException("NBT ends inside a list");

			NbtTag tag = namedTag.getTag();
			if (tag instanceof NbtEnd)
				break;
			else
				tagMap.put(namedTag.getName(), tag);
		}

		return new NbtCompound(tagMap);
	}

	public NbtIntArray readTagIntArray() throws IOException {
		int length = readInt();
		int[] data = new int[length];
		for (int i = 0; i < length; i++)
			data[i] = readInt();

		return new NbtIntArray(data);
	}

	public NbtLongArray readTagLongArray() throws IOException {
		int length = readInt();
		long[] data = new long[length];
		for (int i = 0; i < length; i++)
			data[i] = readLong();

		return new NbtLongArray(data);
	}

	public String readString() throws IOException {
		int length = readUnsignedShort();
		byte[] bytes = new byte[length];
		readFully(bytes);

		return new String(bytes, UTF_8);
	}

}
