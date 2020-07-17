package com.syntaxphoenix.syntaxapi.data.property;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Lauriichen
 * 
 */
public class Property<E> implements Serializable {

	private static final long serialVersionUID = 8586167622486650403L;
	private static final transient HashMap<Class<?>, Class<?>> PRIMITIVE_OBJECT_TYPES = new HashMap<>();

	static {
		PRIMITIVE_OBJECT_TYPES.put(byte.class, Byte.class);
		PRIMITIVE_OBJECT_TYPES.put(short.class, Short.class);
		PRIMITIVE_OBJECT_TYPES.put(int.class, Integer.class);
		PRIMITIVE_OBJECT_TYPES.put(long.class, Long.class);
		PRIMITIVE_OBJECT_TYPES.put(float.class, Float.class);
		PRIMITIVE_OBJECT_TYPES.put(double.class, Double.class);
		PRIMITIVE_OBJECT_TYPES.put(boolean.class, Boolean.class);
	}

	/*
	 * 
	 */

	private String key;
	private E value;

	public Property(String key, E value) {
		this.key = key;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public <Z> Property<Z> tryParse(Class<Z> clz) {
		if (instanceOf(clz)) {
			return (Property<Z>) this;
		} else if (PRIMITIVE_OBJECT_TYPES.containsKey(clz)) {
			Class<?> clazz = PRIMITIVE_OBJECT_TYPES.get(clz);
			if (instanceOf(clazz)) {
				return (Property<Z>) this;
			}
		}
		return null;
	}

	public Property<String> parseString() {
		Property<String> output;
		if ((output = tryParse(String.class)) == null) {
			output = new Property<String>(key, value.toString());
		}
		return output;
	}

	public <Z> boolean instanceOf(Class<Z> clz) {
		return clz.isInstance(value);
	}

	public boolean isSerializable() {
		return value.getClass().isAssignableFrom(Serializable.class);
	}

	public String getHolderKey() {
		return "%" + key + "%";
	}

	public String getKey() {
		return key;
	}

	@SuppressWarnings("unchecked")
	public Class<E> getValueOwner() {
		return (Class<E>) value.getClass();
	}

	public E getValue() {
		return value;
	}

	/*
	 * 
	 * Creation
	 * 
	 */

	public static Property<Object> createObject(String key, Object value) {
		return new Property<Object>(key, value);
	}

	public static <T> Property<T> create(String key, T value) {
		return new Property<T>(key, value);
	}

}
