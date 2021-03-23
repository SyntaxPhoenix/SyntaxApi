package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_Float} tag.
 */
public final class NbtFloat extends NbtTag implements Cloneable, NbtNumber {

    private float value;

    public NbtFloat() {
        this.value = 0;
    }

    public NbtFloat(float value) {
        this.value = value;
    }

    @Override
    public void setValue(Number number) {
        this.value = number.floatValue();
    }

    @Override
    public Float getValue() {
        return value;
    }

    public float getFloatValue() {
        return value;
    }

    public void setFloatValue(float value) {
        this.value = value;
    }

    @Override
    public NbtType getType() {
        return NbtType.FLOAT;
    }

    // MISC

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NbtFloat && equals((NbtFloat) obj);
    }

    public boolean equals(NbtFloat tag) {
        return this.value == tag.value;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

    @Override
    public String toMSONString() {
        return value + "f";
    }

    @Override
    public NbtFloat clone() {
        return new NbtFloat(value);
    }

}
