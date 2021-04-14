package com.syntaxphoenix.syntaxapi.data.container.nbt;

import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.data.DataAdapter;
import com.syntaxphoenix.syntaxapi.data.DataAdapterRegistry;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

public class NbtAdapterRegistry extends DataAdapterRegistry<NbtTag> {

    public Object extract(NbtTag base) {
        if (base.getType() == NbtType.END) {
            return null;
        }
        return extract(base.getValue().getClass(), base);
    }

    @Override
    protected <I, R extends NbtTag> NbtAdapter<I, R> createAdapter(Class<I> primitiveType, Class<R> resultType, Function<I, R> builder,
        Function<R, I> extractor) {
        return new NbtAdapter<>(primitiveType, resultType, builder, extractor);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <I, R extends NbtTag> DataAdapter<I, R, NbtTag> buildAdapter(Class<?> clazz) {
        return (DataAdapter<I, R, NbtTag>) NbtAdapter.createAdapter(this, clazz);
    }

}
