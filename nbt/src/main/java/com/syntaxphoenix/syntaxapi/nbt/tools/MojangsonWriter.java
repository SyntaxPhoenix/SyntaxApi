package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

public class MojangsonWriter extends Writer {

    private static final String NEWLINE = System.getProperty("line.separator"), INDENT = "    ";

    private static final Pattern SIMPLE_STRING = Pattern.compile("[A-Za-z0-9._+-]+");

    private final Writer writer;
    private final boolean pretty;

    private int indent = 0;

    /**
     * Constructs a new writer.
     *
     * @param writer the writer
     * @param pretty whether to "pretty-print", adding whitespace, line breaks and
     *               indent
     */
    public MojangsonWriter(Writer writer, boolean pretty) {
        this.writer = Objects.requireNonNull(writer);
        this.pretty = pretty;
    }

    /**
     * Constructs a new writer with disabled pretty printing.
     *
     * @param writer the writer
     */
    public MojangsonWriter(Writer writer) {
        this(writer, false);
    }

    public void writeNamedTag(String name, NbtTag root) throws IOException {
        if (!name.isEmpty()) {
            write((new NbtString(name).toMSONString()));
            write(':');
            if (pretty) {
                write(' ');
            }
        }

        writeTag(root);
    }

    public void writeNamedTag(NbtNamedTag nbt) throws IOException {
        writeNamedTag(nbt.getName(), nbt.getTag());
    }

    public void writeTag(NbtTag tag) throws IOException {
        if (!pretty) {
            write(tag.toMSONString());
            return;
        }

        NbtType type = tag.getType();
        if (type == NbtType.END || type.isPrimitive() || type.isArray()) {
            writer.write(tag.toMSONString());
        } else if (type == NbtType.COMPOUND) {
            writeCompound((NbtCompound) tag);
        } else if (type == NbtType.LIST) {
            writeList((NbtList<?>) tag);
        } else {
            throw new AssertionError(type);
        }
    }

    private void writeCompound(NbtCompound compound) throws IOException {
        if (!pretty) {
            write(compound.toMSONString());
            return;
        }

        write('{');

        if (!compound.isEmpty()) {
            boolean simple = isPrimitive(compound);

            if (!simple) {
                indent++;
                endLn();
            }

            Map<String, NbtTag> map = compound.getValue();
            Set<String> keys = map.keySet();
            boolean first = true;

            if (simple) {
                for (String key : keys) {
                    if (first) {
                        first = false;
                    } else {
                        write(", ");
                    }
                    write(SIMPLE_STRING.matcher(key).matches() ? key : NbtString.toMSONString(key));
                    write(": ");
                    writeTag(map.get(key));
                }
            } else {
                for (String key : keys) {
                    if (first) {
                        first = false;
                    } else {
                        write(",");
                        endLn();
                    }
                    write(SIMPLE_STRING.matcher(key).matches() ? key : NbtString.toMSONString(key));
                    write(": ");
                    writeTag(map.get(key));
                }
            }

            if (!simple) {
                indent--;
                endLn();
            }
        }

        write('}');
    }

    private void writeList(NbtList<?> list) throws IOException {
        if (!pretty) {
            write(list.toMSONString());
            return;
        }

        write('[');

        if (!list.isEmpty()) {
            boolean simple = isPrimitive(list);
            if (!simple) {
                indent++;
                endLn();
            }

            boolean first = true;

            if (simple) {
                for (NbtTag tag : list) {
                    if (first) {
                        first = false;
                    } else {
                        write(", ");
                    }
                    writeTag(tag);
                }
            } else {
                for (NbtTag tag : list) {
                    if (first) {
                        first = false;
                    } else {
                        write(",");
                        endLn();
                    }
                    writeTag(tag);
                }
            }

            if (!simple) {
                indent--;
                endLn();
            }
        }

        write(']');
    }

    /**
     * Writes the indentation as many times as necessary.
     *
     * @throws IOException if an I/O error occurs
     */
    protected void indent() throws IOException {
        if (indent == 1) {
            writer.write(INDENT);
        } else if (indent > 0) {
            for (int i = 0; i < indent; i++) {
                writer.append(INDENT);
            }
        }
    }

    /**
     * Ends the line with the native new line sequence. This will be CRLF on Windows
     * and LF on most Unix systems.
     *
     * @throws IOException if an I/O error occurs
     */
    protected void endLn() throws IOException {
        writer.write(NEWLINE);
        indent();
    }

    // WRITER IMPL

    @Override
    public void write(int c) throws IOException {
        writer.write(c);
    }

    @Override
    public void write(String str) throws IOException {
        writer.write(str);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    // UTIL

    private static boolean isPrimitive(NbtList<?> list) {
        return list.isEmpty() || list.getElementType().isPrimitive();
    }

    private static boolean isPrimitive(NbtCompound compound) {
        if (compound.isEmpty()) {
            return true;
        }
        for (NbtTag val : compound.getValue().values()) {
            if (!val.getType().isPrimitive()) {
                return false;
            }
        }
        return true;
    }

}
