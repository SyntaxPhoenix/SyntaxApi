package com.syntaxphoenix.syntaxapi.reflection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.function.Predicate;

public class ReflectionTools {

	public static Object getValue(Field field, Object source) {
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

	public static Object getValue(Field field) {
		return getValue(field, null);
	}

	public static boolean hasSameArguments(Class<?>[] compare1, Class<?>[] compare2) {
		if (compare1.length == 0 && compare2.length == 0) {
			return true;
		} else if (compare1.length != compare2.length) {
			return false;
		}
		for (Class<?> arg1 : compare1) {
			boolean found = true;
			for (Class<?> arg2 : compare2) {
				if (!arg1.isAssignableFrom(arg2)) {
					found = false;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 
	 */

	public static File[] getPackageClassFiles(String packageName) throws IOException {
		ArrayList<File> list = new ArrayList<>();
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName);
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			list.addAll(getDirectoryContent(new File(url.getFile()), file -> {
				if (!file.getName().contains("."))
					return false;
				String[] filePath = file.getName().split("\\.");
				switch (filePath[filePath.length - 1]) {
				case "java":
				case "class":
					return true;
				default:
					return false;
				}
			}));
		}
		return list.toArray(new File[0]);
	}

	private static ArrayList<File> getDirectoryContent(File folder, Predicate<File> filter) {
		ArrayList<File> output = new ArrayList<>();
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (int index = 0; index < files.length; index++) {
				File file = files[index];
				if (filter.test(file))
					output.add(file);
			}
		} else
			output.add(folder);
		return output;
	}

	/*
	 * 
	 */

	public static Object execute(Object source, Method method, Object... arguments) {
		if (method != null) {
			boolean access;
			if (!(access = method.isAccessible())) {
				method.setAccessible(true);
			}
			try {
				return method.invoke(source, arguments);
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

	public static int countSuperTypesTill(Class<?> type, Class<?> superType) {
		int count = 0;
		Class<?> currentType = type;
		while (!superType.equals(currentType)) {
			currentType = currentType.getSuperclass();
			count++;
			if (currentType == null)
				break;
		}
		return count;
	}

}
