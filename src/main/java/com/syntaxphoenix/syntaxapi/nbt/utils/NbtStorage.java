package com.syntaxphoenix.syntaxapi.nbt.utils;

import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

public interface NbtStorage<E extends NbtTag> extends NbtLoadable<E>, NbtSaveable<E> {
	
}
