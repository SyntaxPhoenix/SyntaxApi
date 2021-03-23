package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Compare;

public class Arrays {

    public static Object[] merge(Object[] array1, Object... array2) {
        Object[] output = new Object[array1.length + array2.length];
        System.arraycopy(array1, 0, output, 0, array1.length);
        System.arraycopy(array2, 0, output, array1.length, array2.length);
        return output;
    }

    @SafeVarargs
    public static <E> E[] merge(IntFunction<E[]> function, E[] array1, E... array2) {
        E[] output = function.apply(array1.length + array2.length);
        System.arraycopy(array1, 0, output, 0, array1.length);
        System.arraycopy(array2, 0, output, array1.length, array2.length);
        return output;
    }

    public static Object[] subArray(Object[] args, int index) {
        if (index < 0 || index >= args.length) {
            return new Object[0];
        }
        int length = args.length - index;
        Object[] output = new Object[length];
        System.arraycopy(args, index, output, 0, length);
        return output;
    }

    public static <E> E[] subArray(IntFunction<E[]> function, E[] args, int index) {
        if (index < 0 || index >= args.length) {
            return function.apply(0);
        }
        int length = args.length - index;
        E[] output = function.apply(length);
        System.arraycopy(args, index, output, 0, length);
        return output;
    }

    public static Object[] subArray(Object[] args, int index, int length) {
        if (index < 0 || index >= args.length) {
            return new Object[0];
        }
        Object[] output = new Object[length];
        System.arraycopy(args, index, output, 0, length);
        return output;
    }

    public static <E> E[] subArray(IntFunction<E[]> function, E[] args, int index, int length) {
        if (index < 0 || index >= args.length) {
            return function.apply(0);
        }
        E[] output = function.apply(length);
        System.arraycopy(args, index, output, 0, length);
        return output;
    }

    public static Object[][] partition(Object[] args, int length) {
        int size = (int) Math.floor(args.length / (float) length);
        if (args.length % length != 0) {
            size++;
        }
        Object[][] output = new Object[size][length];
        for (int index = 0; index < size; index++) {
            System.arraycopy(args, index * length, output[index], 0, length);
        }
        return output;
    }

    public static <E> E[][] partition(BiFunction<Integer, Integer, E[][]> function, E[] args, int length) {
        int size = (int) Math.floor(args.length / (float) length);
        if (args.length % length != 0) {
            size++;
        }
        E[][] output = function.apply(size, length);
        for (int index = 0; index < size; index++) {
            System.arraycopy(args, index * length, output[index], 0, length);
        }
        return output;
    }

    public static <A> boolean contains(A[] values, A find) {
        return java.util.Arrays.stream(values).anyMatch(value -> value.equals(find));
    }

    public static <A, B> boolean contains(A[] values, B find, Compare<A, B> comparator) {
        return java.util.Arrays.stream(values).anyMatch(value -> comparator.compare(value, find));
    }

}
