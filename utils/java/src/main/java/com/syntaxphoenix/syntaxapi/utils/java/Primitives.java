package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public final class Primitives {

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_COMPLEX, COMPLEX_TO_PRIMITIVE;

	static {
		Map<Class<?>, Class<?>> collect = new LinkedHashMap<>(16);

		collect.put(byte.class, Byte.class);
		collect.put(short.class, Short.class);
		collect.put(int.class, Integer.class);
		collect.put(long.class, Long.class);
		collect.put(float.class, Float.class);
		collect.put(double.class, Double.class);
		collect.put(void.class, Void.class);
		collect.put(char.class, Character.class);

		PRIMITIVE_TO_COMPLEX = Collections.unmodifiableMap(collect);

		collect = new LinkedHashMap<>(16);
		for (Entry<Class<?>, Class<?>> entry : PRIMITIVE_TO_COMPLEX.entrySet())
			collect.put(entry.getValue(), entry.getKey());

		COMPLEX_TO_PRIMITIVE = Collections.unmodifiableMap(collect);
	}

	public static boolean isComplex(Object object) {
		return object == null ? false : isComplex(object.getClass());
	}

	public static boolean isComplex(Class<?> type) {
		return COMPLEX_TO_PRIMITIVE.containsKey(type);
	}

	public static boolean isPrimitive(Object object) {
		return object == null ? false : isPrimitive(object.getClass());
	}

	public static boolean isPrimitive(Class<?> type) {
		return PRIMITIVE_TO_COMPLEX.containsKey(type);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> fromPrimitive(Class<T> primitive) {
		Objects.requireNonNull(primitive);

		Class<?> complex = PRIMITIVE_TO_COMPLEX.get(primitive);
		return complex == null ? primitive : (Class<T>) complex;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> toPrimitive(Class<T> complex) {
		Objects.requireNonNull(complex);

		Class<?> primitive = COMPLEX_TO_PRIMITIVE.get(complex);
		return primitive == null ? complex : (Class<T>) primitive;
	}

}
