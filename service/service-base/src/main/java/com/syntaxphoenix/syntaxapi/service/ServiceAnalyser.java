package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ServiceAnalyser {

	static ArrayList<Field> findFields(boolean flag, Class<?> clazz) {
		ArrayList<Field> output = new ArrayList<>();

		Field[] fields = clazz.getFields();
		if (fields.length == 0)
			return output;

		for (Field field : fields) {
			if (field.getAnnotation(SubscribeService.class) == null)
				continue;

			boolean static0 = Modifier.isStatic(field.getModifiers());
			if (flag ? !static0 : static0)
				continue;

			output.add(field);
		}

		return output;
	}

	static ArrayList<Method> findMethods(boolean flag, Class<?> clazz) {
		ArrayList<Method> output = new ArrayList<>();

		Method[] methods = clazz.getMethods();
		if (methods.length == 0)
			return output;

		for (Method method : methods) {

			if (method.getParameterCount() != 0)
				continue;

			if (method.getAnnotation(SubscribeService.class) == null)
				continue;

			boolean static0 = Modifier.isStatic(method.getModifiers());
			if (flag ? !static0 : static0)
				continue;

			output.add(method);
		}

		return output;
	}

}
