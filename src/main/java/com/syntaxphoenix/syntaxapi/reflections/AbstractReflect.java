package com.syntaxphoenix.syntaxapi.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.utils.java.Reflections;

public abstract class AbstractReflect {

	public static final AbstractReflect FIELD = new Reflect(Field.class).searchField("modify", "modifiers");

	private final Class<?> owner;
	private final HashMap<String, Constructor<?>> constructors = new HashMap<>();
	private final HashMap<String, Method> methods = new HashMap<>();
	private final HashMap<String, Field> fields = new HashMap<>();

	protected AbstractReflect(String classPath) {
		this.owner = ClassCache.getClass(classPath);
	}

	protected AbstractReflect(Class<?> owner) {
		this.owner = owner;
	}

	/*
	 * 
	 */

	public Class<?> getOwner() {
		return owner;
	}

	/*
	 * 
	 */

	public void delete() {
		constructors.clear();
		methods.clear();
		fields.clear();
		try {
			fields.put("owner", this.getClass().getDeclaredField("owner"));
			setFieldValue("owner", null);
			fields.remove("owner");
		} catch (NoSuchFieldException | SecurityException e) {
		}
	}
	
	/*
	 * 
	 */
	
	public Collection<Constructor<?>> getConstructors() {
		return constructors.values();
	}
	
	public Collection<Method> getMethods() {
		return methods.values();
	}
	
	public Collection<Field> getFields() {
		return fields.values();
	}
	
	/*
	 * 
	 */

	public boolean putConstructor(String name, Constructor<?> constructor) {
		if (constructors.containsKey(name)) {
			return false;
		}
		constructors.put(name, constructor);
		return true;
	}

	public boolean putMethod(String name, Method method) {
		if (methods.containsKey(name)) {
			return false;
		}
		methods.put(name, method);
		return true;
	}

	public boolean putField(String name, Field field) {
		if (fields.containsKey(name)) {
			return false;
		}
		fields.put(name, field);
		return true;
	}

	/*
	 * 
	 */

	public boolean containsMethod(String name) {
		return methods.containsKey(name);
	}

	public boolean containsField(String name) {
		return fields.containsKey(name);
	}

	/*
	 * 
	 */

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

	/*
	 * 
	 */

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
			boolean access;
			if (!(access = method.isAccessible())) {
				method.setAccessible(true);
			}
			try {
				return method.invoke(source, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				if (!access) {
					method.setAccessible(access);
				}
				e.printStackTrace();
			}
			if (!access) {
				method.setAccessible(access);
			}
		}
		return null;
	}

	/*
	 * 
	 */

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
				if (!access) {
					field.setAccessible(access);
				}
				e.printStackTrace();
			}
			if (!access) {
				field.setAccessible(access);
			}
			return output;
		}
		return null;
	}

	public void setFieldValue(String name, Object value) {
		setFieldValue(null, name, value);
	}

	public void setFieldValue(Object source, String name, Object value) {
		Field field = getField(name);
		if (field != null) {
			boolean access = field.isAccessible();
			if (!access) {
				field.setAccessible(true);
			}
			boolean isFinal;
			int previous = field.getModifiers();
			if (isFinal = Modifier.isFinal(previous)) {
				FIELD.setFieldValue(field, "modify", previous & ~Modifier.FINAL);
			}
			try {
				field.set(source, value);
			} catch (IllegalAccessException | IllegalArgumentException e) {
				if (!access) {
					field.setAccessible(access);
				}
				if (isFinal) {
					FIELD.setFieldValue(field, "modify", previous);
				}
				e.printStackTrace();
			}
			if (!access) {
				field.setAccessible(access);
			}
			if (isFinal) {
				FIELD.setFieldValue(field, "modify", previous);
			}
		}
	}

	/*
	 * 
	 */

	public Constructor<?> getConstructor(String name) {
		return constructors.get(name);
	}

	public Method getMethod(String name) {
		return methods.get(name);
	}

	public Field getField(String name) {
		return fields.get(name);
	}

	/*
	 * 
	 */

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

	public AbstractReflect searchConstructorsByArguments(String base, Class<?>... arguments) {
		Constructor<?>[] constructors = owner.getConstructors();
		if (constructors.length == 0) {
			return this;
		}
		base += '-';
		int current = 0;
		for (Constructor<?> constructor : constructors) {
			Class<?>[] args = constructor.getParameterTypes();
			if (args.length != arguments.length) {
				continue;
			}
			if (Reflections.hasSameArguments(arguments, args)) {
				putConstructor(base + current, constructor);
				current++;
			}
		}
		return this;
	}

	/*
	 * 
	 */

	public AbstractReflect searchMethod(String name, String methodName, Class<?>... arguments) {
		if (containsMethod(name)) {
			return this;
		}
		Method method = null;
		try {
			method = owner.getDeclaredMethod(methodName, arguments);
		} catch (NoSuchMethodException | SecurityException e) {
		}
		if (method == null) {
			try {
				method = owner.getMethod(methodName, arguments);
			} catch (NoSuchMethodException | SecurityException e) {
			}
		}
		if (method != null) {
			methods.put(name, method);
		}
		return this;
	}

	public AbstractReflect searchMethodsByArguments(String base, Class<?>... arguments) {
		Method[] methods = owner.getMethods();
		if (methods.length == 0) {
			return this;
		}
		base += '-';
		int current = 0;
		for (Method method : methods) {
			Class<?>[] args = method.getParameterTypes();
			if (args.length != arguments.length) {
				continue;
			}
			if (Reflections.hasSameArguments(arguments, args)) {
				putMethod(base + current, method);
				current++;
			}
		}
		return this;
	}

	/*
	 * 
	 */

	public AbstractReflect searchField(String name, String fieldName) {
		if (containsField(name)) {
			return this;
		}
		Field field = null;
		try {
			field = owner.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
		}
		if (field == null) {
			try {
				field = owner.getField(fieldName);
			} catch (NoSuchFieldException | SecurityException e) {
			}
		}
		if (field != null) {
			fields.put(name, field);
		}
		return this;
	}

	public AbstractReflect searchFields(String name, String fieldName) {
		if (containsField(name)) {
			return this;
		}
		Field[] searching = owner.getFields();
		int current = 0;
		for(Field field : searching) {
			if(field.getName().startsWith(fieldName)) {
				fields.put(name + current, field);
				current++;
			}
		}
		return this;
	}

}
