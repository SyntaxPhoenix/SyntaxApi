package com.syntaxphoenix.syntaxapi.utils.reflection;

import java.util.HashMap;

/**
 * @author Lauriichen
 *
 */
public class Reflector {

	private static final HashMap<String, Class<?>> CLASS_CACHE = new HashMap<>();

	public static Class<?> getClass(String classPath) {
		try {
			return getClassEx(classPath);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static Class<?> getClassEx(String classPath) throws ClassNotFoundException {
		if (CLASS_CACHE.containsKey(classPath)) {
			return CLASS_CACHE.get(classPath);
		}
		Class<?> clazz = Class.forName(classPath);
		CLASS_CACHE.put(classPath, clazz);
		return clazz;
	}

}
