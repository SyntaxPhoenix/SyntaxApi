package com.syntaxphoenix.syntaxapi.nbt.utils;

import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

public interface NbtLoadable<E extends NbtTag> {

	public void fromNbt(E nbt);

}
