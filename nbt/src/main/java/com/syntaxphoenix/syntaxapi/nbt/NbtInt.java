package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_Int} tag.
 */
public final class NbtInt extends NbtTag implements Cloneable, NbtNumber {

    private int value;
    
    public NbtInt() {
        this.value = 0;
    }
    
    public NbtInt(int value) {
        this.value = value;
    }
    
    @Override
    public void setValue(Number number) {
    	this.value = number.intValue();
    }
    
    @Override
    public Integer getValue() {
        return value;
    }

    public int getIntValue() {
        return value;
    }
    
    public void setIntValue(int value) {
        this.value = value;
    }
    
    @Override
    public NbtType getType() {
        return NbtType.INT;
    }
    
    // MISC
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NbtInt && equals((NbtInt) obj);
    }
    
    public boolean equals(NbtInt tag) {
        return this.value == tag.value;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
    
    @Override
    public String toMSONString() {
        return Integer.toString(value);
    }
    
    @Override
    public NbtInt clone() {
        return new NbtInt(value);
    }
    
}
