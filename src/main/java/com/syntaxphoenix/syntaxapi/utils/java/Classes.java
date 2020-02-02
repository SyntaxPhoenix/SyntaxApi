package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Classes {

	public static List<Class<?>> fromPackage(String name) {
		List<Class<?>> classes = new ArrayList<>();
		File file = null;
		try {
			file = new File(Classes.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		List<File> files = new ArrayList<>();
		if (file.isDirectory()) {
			files.addAll(listPackageFiles(file, ".class", "$", name));
		}
		for (File currentFile : files) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(shrimPath(currentFile, ".").replace(".class", ""), false,
						Classes.class.getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			classes.add(clazz);
		}
		return classes;
	}

	public static List<File> listPackageFiles(File directory, String fileEnd, String containsNot, String inPackage) {
		String packagePath = inPackage.replace(".", "/");
		List<File> list = new ArrayList<>();
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
	
}
