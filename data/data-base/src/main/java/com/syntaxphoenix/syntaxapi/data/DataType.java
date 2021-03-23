package com.syntaxphoenix.syntaxapi.data;

public interface DataType<P extends Object, C extends Object> {

    public default boolean isComplex(Object object) {
        return getComplex().isInstance(object);
    }

    public default boolean isPrimitive(Object object) {
        return getPrimitive().isInstance(object);
    }

    public Class<C> getComplex();

    public Class<P> getPrimitive();

    public P toPrimitive(DataAdapterContext context, C complex);

    public C fromPrimitive(DataAdapterContext context, P primitive);

    @SuppressWarnings("unchecked")
    public default P toPrimitiveObj(DataAdapterContext context, Object complex) {
        if (complex == null) {
            return null;
        }
        if (isComplex(complex)) {
            return toPrimitive(context, (C) complex);
        }
        if (isPrimitive(complex)) {
            return (P) complex;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public default C fromPrimitiveObj(DataAdapterContext context, Object primitive) {
        if (primitive == null) {
            return null;
        }
        if (isPrimitive(primitive)) {
            return fromPrimitive(context, (P) primitive);
        }
        if (isComplex(primitive)) {
            return (C) primitive;
        }
        return null;
    }

}
