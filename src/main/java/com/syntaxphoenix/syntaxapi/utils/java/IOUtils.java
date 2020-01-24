package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {

	public static List<Class<?>> fromPackage(String name) {
		List<Class<?>> classes = new ArrayList<>();
		File f = null;
		try {
			f = new File(IOUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		List<File> fls = new ArrayList<>();
		if (f.isDirectory()) {
			fls.addAll(listFiles(f, ".class", "$", name));
		}
		for (File fx : fls) {
			Class<?> clz = null;
			try {
				clz = Class.forName(shrimPath(fx, ".").replace(".class", ""), false, IOUtils.class.getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			classes.add(clz);
		}
		return classes;
	}

	public static List<File> listFiles(File dict) {
		List<File> list = new ArrayList<>();
		for (File f : dict.listFiles()) {
			if (f.isDirectory()) {
				list.addAll(listFiles(f));
			} else {
				list.add(f);
			}
		}
		return list;
	}

	public static List<String> listFileNames(File dict, String end) {
		List<String> list = new ArrayList<>();
		for (File f : dict.listFiles()) {
			if (f.isDirectory()) {
				list.addAll(listFileNames(f, end));
			} else {
				if (f.getName().endsWith(end)) {
					list.add(f.getName().replace(end, ""));
				}
			}
		}
		return list;
	}

	@SafeVarargs
	public static List<String> merge(List<String>... lists) {
		List<String> end = new ArrayList<>();
		for (List<String> lv : lists) {
			end.addAll(lv);
		}
		return end;
	}

	public static List<String> listFileNames(String end, File... dict) {
		List<String> list = new ArrayList<>();
		for (File f : dict) {
			list.addAll(listFileNames(f, end));
		}
		return list;
	}

	public static List<File> listFiles(File dict, String end) {
		List<File> list = new ArrayList<>();
		for (File f : dict.listFiles()) {
			if (f.isDirectory()) {
				list.addAll(listFiles(f, end));
			} else {
				if (f.getName().endsWith(end)) {
					list.add(f);
				}
			}
		}
		return list;
	}

	public static List<File> listFilesC(File dict, String contains) {
		List<File> list = new ArrayList<>();
		for (File f : dict.listFiles()) {
			if (f.isDirectory()) {
				list.addAll(listFiles(f, contains));
			} else {
				if (f.getName().contains(contains)) {
					list.add(f);
				}
			}
		}
		return list;
	}

	public static List<File> listFiles(File dict, String end, String containsNot) {
		List<File> list = new ArrayList<>();
		for (File f : dict.listFiles()) {
			if (f.isDirectory()) {
				list.addAll(listFiles(f, end, containsNot));
			} else {
				if (f.getName().endsWith(end)) {
					if (!f.getName().contains(containsNot)) {
						list.add(f);
					}
				}
			}
		}
		return list;
	}

	public static List<File> listFiles(File dict, String end, String containsNot, String inPackage) {
		String pcg = inPackage.replace(".", "/");
		List<File> list = new ArrayList<>();
		for (File f : dict.listFiles()) {
			if (f.isDirectory()) {
				list.addAll(listFiles(f, end, containsNot, inPackage));
			} else {
				if (f.getName().endsWith(end)) {
					if (!f.getName().contains(containsNot)) {
						if (checkPath(f, pcg)) {
							list.add(f);
						}
					}
				}
			}
		}
		return list;
	}

	private static String shrimPath(File f, String x) {
		return f.getPath().replace("\\", x).split(x + "bin" + x)[1];
	}

	private static boolean checkPath(File f, String pckg) {
		String path = shrimPath(f, "/");
		return path.startsWith(pckg);
	}
}
