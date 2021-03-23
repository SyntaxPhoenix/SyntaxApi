package com.syntaxphoenix.syntaxapi.nbt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/**
 * The {@code TAG_Compound} tag.
 */
public final class NbtCompound extends NbtTag {

    private static final Pattern SIMPLE_STRING = Pattern.compile("[A-Za-z0-9._+-]+");

    private final Map<String, NbtTag> value;

    public NbtCompound(Map<String, NbtTag> value) {
        this.value = new LinkedHashMap<>(value);
    }

    public NbtCompound(NbtNamedTag... tags) {
        this.value = new LinkedHashMap<>();

        for (NbtNamedTag tag : tags) {
            this.value.put(tag.getName(), tag.getTag());
        }
    }

    public NbtCompound() {
        this.value = new LinkedHashMap<>();
    }

    // GETTERS

    /**
     * Returns the size of this compound.
     *
     * @return the size of this compound
     */
    public int size() {
        return value.size();
    }

    @Override
    public Map<String, NbtTag> getValue() {
        return value;
    }

    @Override
    public NbtType getType() {
        return NbtType.COMPOUND;
    }

    public void clear() {
        value.clear();
    }

    @Override
    public NbtCompound clone() {
        NbtCompound compound = new NbtCompound();
        for (Entry<String, NbtTag> entry : value.entrySet()) {
            compound.set(entry.getKey(), entry.getValue().clone());
        }
        return compound;
    }

    /**
     * Returns a tag number named with the given key.
     *
     * @param key the key
     * @return a byte
     * @throws NoSuchElementException if there is no tag with given name
     */
    public NbtNumber getNumber(String key) {
        if (!hasKey(key)) {
            return null;
        }
        NbtTag tag = get(key);
        if (!(tag instanceof NbtNumber)) {
            return null;
        }
        return (NbtNumber) tag;
    }

    /**
     * Removes a value with given Key
     * 
     * @param key the key
     * @return value
     */
    public NbtTag remove(String key) {
        if (hasKey(key)) {
            return value.remove(key);
        }
        return null;
    }

    /**
     * Returns a tag named with the given key.
     *
     * @param key the key
     * @return a byte
     * @throws NoSuchElementException if there is no tag with given name
     */
    public NbtTag get(String key) {
        if (!hasKey(key)) {
            return null;
        }
        return value.get(key);
    }

    /**
     * Returns a byte named with the given key.
     *
     * @param key the key
     * @return a byte
     * @throws NoSuchElementException if there is no byte with given name
     */
    public byte getByte(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtByte)) {
            return 0;
        }
        return ((NbtByte) tag).getValue();
    }

    /**
     * Returns an short named with the given key.
     *
     * @param key the key
     * @return an short
     * @throws NoSuchElementException if there is no short with given name
     */
    public short getShort(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtShort)) {
            return 0;
        }
        return ((NbtShort) tag).getValue();
    }

    /**
     * Returns an int named with the given key.
     *
     * @param key the key
     * @return an int
     * @throws NoSuchElementException if there is no int with given name
     */
    public int getInt(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtInt)) {
            return 0;
        }
        return ((NbtInt) tag).getValue();
    }

    /**
     * Returns an int named with the given key.
     *
     * @param key the key
     * @return an int
     * @throws NoSuchElementException if there is no int with given name
     */
    public BigInteger getBigInt(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtBigInt)) {
            return null;
        }
        return ((NbtBigInt) tag).getInteger();
    }

    /**
     * Returns an long named with the given key.
     *
     * @param key the key
     * @return an long
     * @throws NoSuchElementException if there is no long with given name
     */
    public long getLong(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtLong)) {
            return 0;
        }
        return ((NbtLong) tag).getValue();
    }

    /**
     * Returns float named with the given key.
     *
     * @param key the key
     * @return a float
     * @throws NoSuchElementException if there is no float with given name
     */
    public float getFloat(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtFloat)) {
            return 0;
        }
        return ((NbtFloat) tag).getValue();
    }

    /**
     * Returns a double named with the given key.
     *
     * @param key the key
     * @return a double
     * @throws NoSuchElementException if there is no int with given name
     */
    public double getDouble(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtDouble)) {
            return 0;
        }
        return ((NbtDouble) tag).getValue();
    }

    /**
     * Returns a byte array named with the given key.
     *
     * @param key the key
     * @return a byte array
     * @throws NoSuchElementException if there is no int with given name
     */
    public byte[] getByteArray(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtByteArray)) {
            return null;
        }
        return ((NbtByteArray) tag).getValue();
    }

    /**
     * Returns a string named with the given key.
     *
     * @param key the key
     * @return a string
     * @throws NoSuchElementException if there is no int with given name
     */
    public String getString(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtString)) {
            return null;
        }
        return ((NbtString) tag).getValue();
    }

    /**
     * Returns a list named with the given key.
     *
     * @param key the key
     * @return a list
     * @throws NoSuchElementException if there is no int with given name
     */
    public List<?> getList(String key) {
        NbtList<?> list = getTagList(key);
        if (list == null) {
            return list;
        }
        return list.getValue();
    }

    /**
     * Returns a list named with the given key.
     *
     * @param key the key
     * @return a list
     * @throws NoSuchElementException if there is no list with given name
     */
    public NbtList<?> getTagList(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtList)) {
            return null;
        }
        return (NbtList<?>) tag;
    }

    /**
     * Returns a list named with the given key.
     *
     * @param key the key
     * @return a list
     * @throws NoSuchElementException if there is no compound with given name
     */
    public Map<String, NbtTag> getCompoundMap(String key) {
        return getCompound(key).getValue();
    }

    /**
     * Returns a compound named with the given key.
     *
     * @param key the key
     * @return a compound
     * @throws NoSuchElementException if there is no compound with given name
     */
    public NbtCompound getCompound(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtCompound)) {
            return null;
        }
        return (NbtCompound) tag;
    }

    /**
     * Returns an int array named with the given key.
     *
     * @param key the key
     * @return a int array
     * @throws NoSuchElementException if there is no int array with given name
     */
    public int[] getIntArray(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtIntArray)) {
            return null;
        }
        return ((NbtIntArray) tag).getValue();
    }

    /**
     * Returns a long array named with the given key.
     *
     * @param key the key
     * @return a int array
     * @throws NoSuchElementException if there is no int array with given name
     */
    public long[] getLongArray(String key) {
        NbtTag tag = value.get(key);
        if (!(tag instanceof NbtLongArray)) {
            return null;
        }
        return ((NbtLongArray) tag).getValue();
    }

    /**
     * Returns a boolean named with the given key.
     *
     * @param path the key
     * @return a boolean
     * @throws NoSuchElementException if there is no boolean with given name
     */
    public boolean getBoolean(String path) {
        byte byt = getByte(path);
        if (byt == 0 || byt == 1) {
            return byt == 1;
        }
        return false;
    }

    /**
     * Returns an immutable set containing all the keys in this compound.
     *
     * @return an immutable set
     */
    public Set<String> getKeys() {
        return Collections.unmodifiableSet(value.keySet());
    }

    // PREDICATES

    /**
     * Returns whether this compound is empty.
     *
     * @return whether this compound is empty
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * Returns whether this compound tag contains the given key.
     *
     * @param key the given key
     * @return true if the tag contains the given key
     */
    public boolean hasKey(String key) {
        return value.containsKey(key);
    }

    /**
     * Returns whether this compound tag contains the given key and its value is of
     * a given type.
     *
     * @param key  the given key
     * @param type the type of the value
     * @return true if the tag contains an entry with given key and of given type
     */
    public boolean hasKey(String key, NbtType type) {
        Objects.requireNonNull(type);
        return value.containsKey(key) && value.get(key).getType() == type;
    }

    /**
     * Returns whether this compound tag contains the given key and its value is of
     * a given type.
     *
     * @param key  the given key
     * @param type the type of the value
     * @return true if the tag contains an entry with given key and of given type
     */
    public boolean hasKeyOfType(String key, NbtType type) {
        return hasKey(key, type);
    }

    // MUTATORS

    /**
     * Put the given name and its corresponding tag into the compound tag.
     *
     * @param name the tag name
     * @param tag  the tag value
     */
    public void set(String name, NbtTag tag) {
        this.value.put(name, tag);
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, byte[] value) {
        set(key, new NbtByteArray(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, byte value) {
        set(key, new NbtByte(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param path the key
     * @param bool the value
     */
    public void set(String path, boolean bool) {
        set(path, (byte) (bool ? 1 : 0));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, double value) {
        set(key, new NbtDouble(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, float value) {
        set(key, new NbtFloat(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, int[] value) {
        set(key, new NbtIntArray(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, long[] value) {
        set(key, new NbtLongArray(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, int value) {
        set(key, new NbtInt(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, BigInteger value) {
        set(key, new NbtBigInt(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, BigDecimal value) {
        set(key, new NbtBigDecimal(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, long value) {
        set(key, new NbtLong(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, short value) {
        set(key, new NbtShort(value));
    }

    /**
     * Put the given key and value into the compound tag.
     *
     * @param key   they key
     * @param value the value
     */
    public void set(String key, String value) {
        set(key, new NbtString(value));
    }

    // ITERATION

    /**
     * Performs an action for every pair of keys and tags.
     *
     * @param action the action
     */
    public void forEach(BiConsumer<String, ? super NbtTag> action) {
        this.value.forEach(action::accept);
    }

    // MISC

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NbtCompound && equals((NbtCompound) obj);
    }

    public boolean equals(NbtCompound tag) {
        return this.isEmpty() && tag.isEmpty() || this.value.equals(tag.value);
    }

    @Override
    public String toMSONString() {
        StringBuilder builder = new StringBuilder("{");
        Set<String> keys = this.value.keySet();

        for (String key : keys) {
            if (builder.length() > 1) {
                builder.append(',');
            }
            builder.append(SIMPLE_STRING.matcher(key).matches() ? key : NbtString.toMSONString(key)).append(':')
                .append(this.value.get(key).toMSONString());
        }

        return builder.append("}").toString();
    }

}
