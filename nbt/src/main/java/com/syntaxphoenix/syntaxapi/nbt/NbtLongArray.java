package com.syntaxphoenix.syntaxapi.nbt;

import java.util.Arrays;

/**
 * The {@code TAG_Long_Array} tag.
 */
public final class NbtLongArray extends NbtTag {

	private final long[] value;

	/**
	 * Creates the tag with an empty name.
	 *
	 * @param value the value of the tag
	 */
	public NbtLongArray(long... value) {
		this.value = value;
	}

	public NbtLongArray(Number[] numbers) {
		this.value = new long[numbers.length];
		for (int i = 0; i < numbers.length; i++)
			value[i] = numbers[i].longValue();
	}

	/**
	 * Returns the length of this array.
	 *
	 * @return the length of this array
	 */
	public int length() {
		return value.length;
	}

	@Override
	public long[] getValue() {
		return value;
	}

	@Override
	public NbtType getType() {
		return NbtType.LONG_ARRAY;
	}

	// MISC

	@Override
	public boolean equals(Object obj) {
		return obj instanceof NbtLongArray && equals((NbtLongArray) obj);
	}

	public boolean equals(NbtLongArray tag) {
		return Arrays.equals(this.value, tag.value);
	}

	@Override
	public String toMSONString() {
		StringBuilder stringbuilder = new StringBuilder("[I;");
		for (int i = 0; i < this.value.length; i++) {
			if (i != 0) {
				stringbuilder.append(',');
			}
			stringbuilder.append(this.value[i]);
		}
		return stringbuilder.append(']').toString();
	}

	@Override
	public NbtLongArray clone() {
		return new NbtLongArray(value.clone());
	}

}
