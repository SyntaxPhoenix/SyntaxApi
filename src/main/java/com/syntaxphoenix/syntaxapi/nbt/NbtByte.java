package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_Byte} tag.
 */
public final class NbtByte extends NbtTag implements Cloneable, NbtNumber {

    private byte value;
    
    public NbtByte() {
        this.value = 0;
    }
    
    public NbtByte(byte value) {
        this.value = value;
    }
    
    @Override
    public Number asNumber() {
		return value;
    }
    
    @Override
    public void setValue(Number number) {
    	this.value = number.byteValue();
    }

    @Override
    public Byte getValue() {
        return value;
    }

    public byte getByteValue() {
        return value;
    }
    
    public void setByteValue(byte value) {
        this.value = value;
    }
    
    @Override
    public NbtType getType() {
        return NbtType.BYTE;
    }
    
    // MISC
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NbtByte && equals((NbtByte) obj);
    }
    
    public boolean equals(NbtByte tag) {
        return this.value == tag.value;
    }
    
    @Override
    public int hashCode() {
        return Byte.hashCode(value);
    }
    
    @Override
    public String toMSONString() {
        return Byte.toUnsignedInt(value)+"b";
    }
    
    @Override
    public NbtByte clone() {
        return new NbtByte(value);
    }

}
