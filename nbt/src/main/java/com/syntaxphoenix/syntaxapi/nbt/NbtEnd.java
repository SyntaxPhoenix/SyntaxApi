package com.syntaxphoenix.syntaxapi.nbt;

/**
 * The {@code TAG_End} tag.
 */
public final class NbtEnd extends NbtTag implements Cloneable {

	public final static NbtEnd INSTANCE = new NbtEnd();

	private NbtEnd() {
	}

	@Override
	public Void getValue() {
		return null;
	}

	@Override
	public NbtType getType() {
		return NbtType.END;
	}

	// MISC

	@Override
	public boolean equals(Object obj) {
		return obj instanceof NbtEnd;
	}

	@Override
	public String toMSONString() {
		return "END";
	}

	@Override
	public NbtEnd clone() {
		return INSTANCE;
	}

}
