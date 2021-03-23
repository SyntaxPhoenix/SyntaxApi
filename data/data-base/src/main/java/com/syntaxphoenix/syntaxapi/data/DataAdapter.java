package com.syntaxphoenix.syntaxapi.data;

import java.util.function.Function;

public abstract class DataAdapter<I, R, B> {

    private final Function<I, R> builder;
    private final Function<R, I> extractor;

    private final Class<I> primitiveType;
    private final Class<R> resultType;

    public DataAdapter(Class<I> primitiveType, Class<R> resultType, Function<I, R> builder, Function<R, I> extractor) {
        this.primitiveType = primitiveType;
        this.resultType = resultType;
        this.builder = builder;
        this.extractor = extractor;
    }

    public abstract Class<B> getBaseType();

    protected I extract(B input) {
        return resultType.isInstance(input) ? extractor.apply(resultType.cast(input)) : null;
    }

    protected R build(Object input) {
        return primitiveType.isInstance(input) ? builder.apply(primitiveType.cast(input)) : null;
    }

    public boolean isInstance(B base) {
        return resultType.isInstance(base);
    }

}
