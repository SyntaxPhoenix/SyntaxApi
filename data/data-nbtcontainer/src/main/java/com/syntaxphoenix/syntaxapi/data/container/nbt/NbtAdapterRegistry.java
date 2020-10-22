package com.syntaxphoenix.syntaxapi.data.container.nbt;

import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.data.DataAdapter;
import com.syntaxphoenix.syntaxapi.data.DataAdapterRegistry;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

public class NbtAdapterRegistry extends DataAdapterRegistry<NbtTag> {

	@SuppressWarnings("unchecked")
	public NbtAdapterRegistry() {
		super(clazz -> (DataAdapter<?, NbtTag, NbtTag>) NbtAdapter.createAdapter(clazz));
	}

	public Object extract(NbtTag base) {
		return extract(base.getType().getOwningClass(), base);
	}

	@Override
	protected <I, R extends NbtTag> NbtAdapter<I, R> createAdapter(Class<I> primitiveType, Class<R> resultType, Function<I, R> builder,
		Function<R, I> extractor) {
		return new NbtAdapter<>(primitiveType, resultType, builder, extractor);
	}

}
