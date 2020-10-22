package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_Double} tag.
 * 
 */
public final class NbtDouble extends NbtTag implements Cloneable, NbtNumber {

	private double value;

	public NbtDouble() {
		this.value = 0;
	}

	public NbtDouble(double value) {
		this.value = value;
	}

	@Override
	public void setValue(Number number) {
		this.value = number.doubleValue();
	}

	@Override
	public Double getValue() {
		return value;
	}

	public double getDoubleValue() {
		return value;
	}

	public void setDoubleValue(double value) {
		this.value = value;
	}

	@Override
	public NbtType getType() {
		return NbtType.DOUBLE;
	}

	// MISC

	@Override
	public boolean equals(Object obj) {
		return obj instanceof NbtDouble && equals((NbtDouble) obj);
	}

	public boolean equals(NbtDouble tag) {
		return this.value == tag.value;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}

	@Override
	public String toMSONString() {
		return value + "d";
	}

	@Override
	public NbtDouble clone() {
		return new NbtDouble(value);
	}

}