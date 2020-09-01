package com.syntaxphoenix.syntaxapi.reflection;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

public class PackageAccess {

	public static final Class<?>[] EMPTY = new Class[0];

	public static PackageAccess of(String packageName) {
		return ClasspathAccess.of(packageName).getPackage(packageName);
	}

	protected final HashMap<String, Class<?>> classes = new HashMap<>();
	protected final ClasspathAccess classpath;
	protected final String packageName;

	PackageAccess(ClasspathAccess classpath, String packageName, File externalPackage) {
		this.classpath = classpath;
		this.packageName = packageName;
		scan(externalPackage);
	}

	/*
	 * Scanning for classes
	 */

	private void scan(File exteneralPackage) {
		if (exteneralPackage == null)
			return;
		File[] files = exteneralPackage.listFiles(file -> file.getName().endsWith(".class"));
		for (int index = 0; index < files.length; index++) {
			String name = files[index].getName().split("\\.")[0];
			Optional<Class<?>> option = ClassCache.getOptionalClass(packageName + '.' + name);
			if (option.isPresent())
				classes.put(name, option.get());
		}
	}

	/*
	 * 
	 */

	public ClasspathAccess getClasspath() {
		return classpath;
	}

	public boolean hasClasses() {
		return !classes.isEmpty();
	}

	public Class<?>[] getClasses() {
		return classes.isEmpty() ? EMPTY : classes.values().toArray(new Class[0]);
	}

	public Class<?> getClass(String name) {
		return classes.get(name);
	}

	public Optional<Class<?>> getOptionalClass(String name) {
		return Optional.ofNullable(classes.get(name));
	}

}
