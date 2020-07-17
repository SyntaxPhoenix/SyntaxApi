package com.syntaxphoenix.syntaxapi.nbt.utils;

import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

public interface NbtSaveable<E extends NbtTag> {
	
	public E asNbt();

}
