package com.syntaxphoenix.syntaxapi.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class DataTypeChain<P extends Object, C extends Object> implements DataType<P, C> {

    private final ArrayList<DataType<?, ?>> types = new ArrayList<>();

    /*
     * Contains
     */

    public boolean has(DataType<?, ?> type) {
        return hasExact(type) || hasComplex(type.getComplex());
    }

    public boolean hasExact(DataType<?, ?> type) {
        return types.contains(type);
    }

    public boolean hasComplex(Class<?> complex) {
        return types.stream().anyMatch(type -> Objects.equals(type.getComplex(), complex));
    }

    /*
     * Add
     */

    public DataTypeChain<P, C> add(DataType<?, ?> type) {
        if (type == null) {
            return this;
        }
        if (has(type)) {
            return this;
        }
        types.add(type);
        return this;
    }

    public DataTypeChain<P, C> addAll(DataType<?, ?>... types) {
        if (types == null) {
            return this;
        }
        for (int index = 0; index < types.length; index++) {
            add(types[index]);
        }
        return this;
    }

    public DataTypeChain<P, C> addAll(Iterable<DataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        return addAll(types.iterator());
    }

    public DataTypeChain<P, C> addAll(Iterator<DataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        while (types.hasNext()) {
            add(types.next());
        }
        return this;
    }

    /*
     * Remove
     */

    public DataTypeChain<P, C> remove(DataType<?, ?> type) {
        types.remove(type);
        return this;
    }

    public DataTypeChain<P, C> removeAll(DataType<?, ?>... types) {
        if (types == null) {
            return this;
        }
        for (int index = 0; index < types.length; index++) {
            remove(types[index]);
        }
        return this;
    }

    public DataTypeChain<P, C> removeAll(Iterable<DataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        return removeAll(types.iterator());
    }

    public DataTypeChain<P, C> removeAll(Iterator<DataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        while (types.hasNext()) {
            remove(types.next());
        }
        return this;
    }

    /*
     * Get
     */

    public DataType<?, ?> get(int index) {
        return types.get(index);
    }

    public DataType<?, ?>[] get(int start, int length) {
        return types.subList(start, start + length).toArray(new DataType[0]);
    }

    /*
     * Other
     */

    public int size() {
        return types.size();
    }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    public DataTypeChain<P, C> clear() {
        types.clear();
        return this;
    }

    /*
     * Cast
     */

    @SuppressWarnings("unchecked")
    public <T> Optional<DataTypeChain<P, T>> asComplex(Class<T> complex) {
        return Optional.ofNullable(Objects.equals(getComplex(), complex) ? (DataTypeChain<P, T>) this : null);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<DataTypeChain<T, C>> asPrimitive(Class<T> primitive) {
        return Optional.ofNullable(Objects.equals(getPrimitive(), primitive) ? (DataTypeChain<T, C>) this : null);
    }

    /*
     * DataType
     */

    @SuppressWarnings("unchecked")
    @Override
    public Class<C> getComplex() {
        return isEmpty() ? null : (Class<C>) types.get(size() - 1).getComplex();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<P> getPrimitive() {
        return isEmpty() ? null : (Class<P>) types.get(0).getPrimitive();
    }

    @SuppressWarnings("unchecked")
    @Override
    public C fromPrimitive(DataAdapterContext context, P primitive) {
        Object output = primitive;
        for (int index = size() - 1; index > -1; index--) {
            if (output == null) {
                return null;
            }
            output = types.get(index).fromPrimitiveObj(context, output);
        }
        return isComplex(output) ? (C) output : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public P toPrimitive(DataAdapterContext context, C complex) {
        Object output = complex;
        for (int index = size() - 1; index > -1; index--) {
            if (output == null) {
                return null;
            }
            output = types.get(index).toPrimitiveObj(context, output);
        }
        return isPrimitive(output) ? (P) output : null;
    }

}
