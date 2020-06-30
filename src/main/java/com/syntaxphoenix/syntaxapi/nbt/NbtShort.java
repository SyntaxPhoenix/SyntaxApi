package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_Short} tag.
 */
public final class NbtShort extends NbtTag implements Cloneable, NbtNumber {

    private short value;
    
    public NbtShort() {
        this.value = 0;
    }
    
    public NbtShort(short value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Number number) {
    	this.value = number.shortValue();
    }
    
    @Override
    public Short getValue() {
        return value;
    }

    public short getShortValue() {
        return value;
    }
    
    public void setShortValue(short value) {
        this.value = value;
    }
    
    @Override
    public NbtType getType() {
        return NbtType.SHORT;
    }
    
    // MISC
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NbtShort && equals((NbtShort) obj);
    }
    
    public boolean equals(NbtShort tag) {
        return this.value == tag.value;
    }
    
    @Override
    public int hashCode() {
        return Short.hashCode(value);
    }
    
    @Override
    public String toMSONString() {
        return value+"s";
    }
    
    @Override
    public NbtShort clone() {
        return new NbtShort(value);
    }

}
