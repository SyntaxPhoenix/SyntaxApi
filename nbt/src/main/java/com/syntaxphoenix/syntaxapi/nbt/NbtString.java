package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_String} tag.
 */
public class NbtString extends NbtTag implements Cloneable {

    private String value;

    public NbtString() {
        setValue("");
    }

    public NbtString(String value) {
        setValue(value);
    }

    public boolean isBigInteger() {
        return false;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public NbtType getType() {
        return NbtType.STRING;
    }

    // MISC

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toMSONString() {
        return toMSONString(value);
    }

    @Override
    public NbtString clone() {
        return new NbtString(value);
    }

    // UTIL

    /**
     * Converts a regular string into a Mojangson string by surrounding it with
     * quotes and escaping backslashes and quotes inside it.
     *
     * @param str the string
     * @return the Mojangson string
     */
    public static String toMSONString(String str) {
        StringBuilder builder = new StringBuilder("\"");
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if ((c == '\\') || (c == '"')) {
                builder.append('\\');
            }
            builder.append(c);
        }
        return builder.append('\"').toString();
    }

}
