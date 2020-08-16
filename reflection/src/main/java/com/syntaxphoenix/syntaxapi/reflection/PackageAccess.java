package com.syntaxphoenix.syntaxapi.reflection;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class PackageAccess {

	public static final Class<?>[] EMPTY = new Class[0];

	private static final HashMap<String, PackageAccess> PACKAGES = new HashMap<>();

	public static PackageAccess of(String packageName) {
		if (PACKAGES.containsKey(packageName))
			return PACKAGES.get(packageName);
		PackageAccess access = new PackageAccess(packageName);
		PACKAGES.put(packageName, access);
		return access;
	}

	/*
	 * 
	 */

	protected final HashMap<String, Class<?>> classes = new HashMap<>();
	protected final String packageName;

	private PackageAccess(String packageName) {
		this.packageName = packageName;
		scan();
	}

	/*
	 * Scanning for classes
	 */

	private void scan() {
		File[] files = null;
		try {
			files = ReflectionTools.getPackageClassFiles(packageName);
		} catch (IOException e) {
		}
		if (files == null || files.length == 0)
			return;

		for (int index = 0; index < files.length; index++) {
			String name = files[index].getName().split("\\.")[0];
			classes.put(name, ClassCache.getClass(packageName + '.' + name));
		}
	}

	/*
	 * 
	 */

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
