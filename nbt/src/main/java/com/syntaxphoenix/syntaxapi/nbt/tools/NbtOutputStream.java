package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import com.syntaxphoenix.syntaxapi.nbt.NbtByte;
import com.syntaxphoenix.syntaxapi.nbt.NbtByteArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtDouble;
import com.syntaxphoenix.syntaxapi.nbt.NbtFloat;
import com.syntaxphoenix.syntaxapi.nbt.NbtInt;
import com.syntaxphoenix.syntaxapi.nbt.NbtIntArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtLong;
import com.syntaxphoenix.syntaxapi.nbt.NbtLongArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtShort;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

/**
 * An NBTInputStream extends {@link DataOutputStream} by allowing to write named
 * tags.
 */
public final class NbtOutputStream extends DataOutputStream {

    private final static Charset UTF_8 = Charset.forName("UTF-8");

    private final static int END_ID = NbtType.END.getId();

    /**
     * Creates a new {@code NBTOutputStream}, which will write data to the specified
     * underlying output stream.
     * 
     * @param out the output stream
     */
    public NbtOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes a tag.
     * 
     * @param name the name of the tag
     * @param tag  the tag to write
     * @throws IOException if an I/O error occurs
     */
    public void writeNamedTag(String name, NbtTag tag) throws IOException {
        if (tag == null) {
            return;
        }

        int typeId = tag.getType().getId();
        byte[] nameBytes = name.getBytes(UTF_8);

        writeByte(typeId);
        writeShort(nameBytes.length);
        write(nameBytes);

        if (typeId == END_ID) {
            throw new IOException("Named TAG_End not permitted.");
        }

        writeTag(tag);
    }

    /**
     * Writes a tag.
     *
     * @param tag the tag to write
     * @throws IOException if an I/O error occurs
     */
    public void writeNamedTag(NbtNamedTag tag) throws IOException {
        writeNamedTag(tag.getName(), tag.getTag());
    }

    /**
     * Writes a tag payload.
     * 
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTag(NbtTag tag) throws IOException {
        switch (tag.getType()) {
        case END:
            break;
        case BYTE:
            writeByte(((NbtByte) tag).getByteValue());
            break;
        case SHORT:
            writeShort(((NbtShort) tag).getShortValue());
            break;
        case INT:
            writeInt(((NbtInt) tag).getIntValue());
            break;
        case LONG:
            writeLong(((NbtLong) tag).getLongValue());
            break;
        case FLOAT:
            writeFloat(((NbtFloat) tag).getFloatValue());
            break;
        case DOUBLE:
            writeDouble(((NbtDouble) tag).getDoubleValue());
            break;
        case BYTE_ARRAY:
            writeTagByteArray((NbtByteArray) tag);
            break;
        case STRING:
            writeTagString((NbtString) tag);
            break;
        case LIST:
            writeTagList((NbtList<?>) tag);
            break;
        case COMPOUND:
            writeTagCompound((NbtCompound) tag);
            break;
        case INT_ARRAY:
            writeTagIntArray((NbtIntArray) tag);
            break;
        case LONG_ARRAY:
            writeTagLongArray((NbtLongArray) tag);
            break;
        default:
            throw new IOException("invalid tag type: " + tag.getType());
        }
    }

    /**
     * Writes a {@code TAG_String} tag.
     *
     * @param tag the tag.
     * @throws IOException if an I/O error occurs
     */
    public void writeTagString(NbtString tag) throws IOException {
        byte[] bytes = tag.getValue().getBytes(UTF_8);
        writeShort(bytes.length);
        write(bytes);
    }

    /**
     * Writes a {@code TAG_Byte_Array} tag.
     * 
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagByteArray(NbtByteArray tag) throws IOException {
        byte[] bytes = tag.getValue();
        writeInt(bytes.length);
        write(bytes);
    }

    /**
     * Writes a {@code TAG_List} tag.
     * 
     * @param tag the tag.
     * @throws IOException if an I/O error occurs
     */
    public void writeTagList(NbtList<?> tag) throws IOException {
        NbtType type = tag.getElementType();
        List<? extends NbtTag> tags = tag.getValue();
        int size = tags.size();

        writeByte(type.getId());
        writeInt(size);

        if (tags.isEmpty()) {
            return;
        }

        for (NbtTag element : tags) {
            writeTag(element);
        }
    }

    /**
     * Writes a {@code TAG_Compound} tag.
     *
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagCompound(NbtCompound tag) throws IOException {
        for (Map.Entry<String, NbtTag> entry : tag.getValue().entrySet()) {
            writeNamedTag(entry.getKey(), entry.getValue());
        }
        writeByte(END_ID);
    }

    /**
     * Writes a {@code TAG_Int_Array} tag.
     *
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagIntArray(NbtIntArray tag) throws IOException {
        writeInt(tag.length());
        for (int aData : tag.getValue()) {
            writeInt(aData);
        }
    }

    /**
     * Writes a {@code TAG_Long_Array} tag.
     *
     * @param tag the tag
     * @throws IOException if an I/O error occurs
     */
    public void writeTagLongArray(NbtLongArray tag) throws IOException {
        writeInt(tag.length());
        for (long aData : tag.getValue()) {
            writeLong(aData);
        }
    }

}
