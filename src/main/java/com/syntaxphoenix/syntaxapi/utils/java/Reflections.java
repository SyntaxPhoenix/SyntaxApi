package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Reflections {

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

	public static ArrayList<Class<?>> fromPackage(String name) {
		ArrayList<Class<?>> classes = new ArrayList<>();
		File file = null;
		try {
			file = new File(Reflections.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ArrayList<File> files = new ArrayList<>();
		if (file.isDirectory()) {
			files.addAll(listPackageFiles(file, ".class", "$", name));
		}
		for (File currentFile : files) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(shrimPath(currentFile, ".").replace(".class", ""), false,
						Reflections.class.getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			classes.add(clazz);
		}
		return classes;
	}

	public static ArrayList<File> listPackageFiles(File directory, String fileEnd, String containsNot,
			String inPackage) {
		String packagePath = inPackage.replace(".", "/");
		ArrayList<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listPackageFiles(file, fileEnd, containsNot, inPackage));
			} else {
				if (file.getName().endsWith(fileEnd)) {
					if (!file.getName().contains(containsNot)) {
						if (checkPath(file, packagePath)) {
							list.add(file);
						}
					}
				}
			}
		}
		return list;
	}

	private static String shrimPath(File file, String replacement) {
		return file.getPath().replace("\\", replacement).split(replacement + "bin" + replacement)[1];
	}

	private static boolean checkPath(File file, String packagePath) {
		String path = shrimPath(file, "/");
		return path.startsWith(packagePath);
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
