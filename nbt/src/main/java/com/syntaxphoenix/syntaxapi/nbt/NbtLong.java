package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_Long} tag.
 */
public final class NbtLong extends NbtTag implements Cloneable, NbtNumber {

	private long value;

	public NbtLong() {
		this.value = 0;
	}

	public NbtLong(long value) {
		this.value = value;
	}

	@Override
	public void setValue(Number number) {
		this.value = number.longValue();
	}

	@Override
	public Long getValue() {
		return value;
	}

	public long getLongValue() {
		return value;
	}

	public void setLongValue(long value) {
		this.value = value;
	}

	@Override
	public NbtType getType() {
		return NbtType.LONG;
	}

	// MISC

	@Override
	public boolean equals(Object obj) {
		return obj instanceof NbtLong && equals((NbtLong) obj);
	}

	public boolean equals(NbtLong tag) {
		return this.value == tag.value;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(value);
	}

	@Override
	public String toMSONString() {
		return value + "L";
	}

	@Override
	public NbtLong clone() {
		return new NbtLong(value);
	}

}
