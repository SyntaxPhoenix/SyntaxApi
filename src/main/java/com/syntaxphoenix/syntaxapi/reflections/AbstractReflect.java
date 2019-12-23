package com.syntaxphoenix.syntaxapi.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class AbstractReflect {

	private final Class<?> owner;
	private final HashMap<String, Constructor<?>> constructors = new HashMap<>();
	private final HashMap<String, Method> methods = new HashMap<>();
	private final HashMap<String, Field> fields = new HashMap<>();

	protected AbstractReflect(String classPath) {
		this.owner = Reflector.getClass(classPath);
	}

	protected AbstractReflect(Class<?> owner) {
		this.owner = owner;
	}

	public Class<?> getOwner() {
		return owner;
	}

	public boolean containsMethod(String name) {
		return methods.containsKey(name);
	}

	public boolean containsField(String name) {
		return fields.containsKey(name);
	}

	public Object init() {
		try {
			return owner.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object init(String name, Object... args) {
		Constructor<?> constructor = getConstructor(name);
		if (constructor != null) {
			try {
				return constructor.newInstance(args);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public AbstractReflect execute(String name, Object... args) {
		return execute(null, name, args);
	}

	public AbstractReflect execute(Object source, String name, Object... args) {
		run(source, name, args);
		return this;
	}

	public Object run(String name, Object... args) {
		return run(null, name, args);
	}

	public Object run(Object source, String name, Object... args) {
		Method method = getMethod(name);
		if (method != null) {
			try {
				return method.invoke(source, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Object getFieldValue(String name) {
		return getFieldValue(name, null);
	}

	public Object getFieldValue(String name, Object source) {
		Field field = getField(name);
		if (field != null) {
			boolean access = field.isAccessible();
			if (!access) {
				field.setAccessible(true);
			}
			Object output = null;
			try {
				output = field.get(source);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			field.setAccessible(access);
			return output;
		}
		return null;
	}

	public Constructor<?> getConstructor(String name) {
		return constructors.get(name);
	}

	public Method getMethod(String name) {
		return methods.get(name);
	}

	public Field getField(String name) {
		return fields.get(name);
	}

	public AbstractReflect searchConstructor(String name, Class<?>... args) {
		if (containsMethod(name)) {
			return this;
		}
		Constructor<?> constructor = null;
		try {
			constructor = owner.getConstructor(args);
		} catch (NoSuchMethodException | SecurityException e) {
		}
		if (constructor != null) {
			constructors.put(name, constructor);
		}
		return this;
	}

	public AbstractReflect searchMethod(String name, String methodName, Class<?>... args) {
		if (containsMethod(name)) {
			return this;
		}
		Method method = null;
		try {
			method = owner.getDeclaredMethod(methodName, args);
		} catch (NoSuchMethodException | SecurityException e) {
		}
		try {
			method = owner.getMethod(methodName, args);
		} catch (NoSuchMethodException | SecurityException e) {
		}
		if (method != null) {
			methods.put(name, method);
		}
		return this;
	}

	public AbstractReflect searchField(String name, String fieldName) {
		if (containsField(name)) {
			return this;
		}
		Field field = null;
		try {
			field = owner.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
		}
		try {
			field = owner.getField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
		}
		if (field != null) {
			fields.put(name, field);
		}
		return this;
	}

}
