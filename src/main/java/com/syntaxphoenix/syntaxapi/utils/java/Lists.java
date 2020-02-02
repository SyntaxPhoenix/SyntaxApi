package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Lists {

	@SuppressWarnings("unchecked")
	public static <E> ArrayList<E> asList(E... array) {
		ArrayList<E> list = new ArrayList<E>();
		for (int index = 0; index < array.length; index++) {
			list.add(array[index]);
		}
		return list;
	}

	public static <E> ArrayList<E> asList(Collection<E> collection) {
		ArrayList<E> output = new ArrayList<E>();
		Iterator<E> iterator = collection.iterator();
		while (iterator.hasNext()) {
			output.add(iterator.next());
		}
		return output;
	}

	public static boolean containsEqualsIgnoreCase(String equals, ArrayList<String> list) {
		if(list.isEmpty()) return false;
		for (String input : list) {
			if (input.equalsIgnoreCase(equals)) {
				return true;
			}
		}
		return false;
	}

	public static String[] toStringArray(Collection<String[]> collection) {
		ArrayList<String> output = new ArrayList<>();
		collection.forEach(new Consumer<String[]>() {
			@Override
			public void accept(String[] array) {
				for (int index = 0; index < array.length; index++) {
					output.add(array[index]);
				}
			}
		});
		return output.toArray(new String[0]);
	}

	public static <E> ArrayList<ArrayList<E>> partition(ArrayList<E> list, int amount) {
		ArrayList<ArrayList<E>> parition = new ArrayList<>();
		ArrayList<E> current = new ArrayList<>();
		for (int index = 0; index < list.size(); index++) {
			if (current.size() == amount) {
				parition.add(current);
				current = new ArrayList<>();
			}
			current.add(list.get(index));
		}
		if (!current.isEmpty()) {
			parition.add(current);
		}
		return parition;
	}

	@SafeVarargs
	public static ArrayList<String> merge(ArrayList<String>... lists) {
		ArrayList<String> output = new ArrayList<>();
		for (ArrayList<String> list : lists) {
			output.addAll(list);
		}
		return output;
	}
	public static List<File> listFiles(File directory) {
		List<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file));
			} else {
				list.add(file);
			}
		}
		return list;
	}

	public static List<String> listFileNames(File directory, String fileEnd) {
		List<String> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFileNames(file, fileEnd));
			} else {
				if (file.getName().endsWith(fileEnd)) {
					list.add(file.getName().replace(fileEnd, ""));
				}
			}
		}
		return list;
	}

	public static List<String> listFileNames(String fileEnd, File... directories) {
		List<String> list = new ArrayList<>();
		for (File file : directories) {
			list.addAll(listFileNames(file, fileEnd));
		}
		return list;
	}

	public static List<File> listFiles(File directory, String fileEnd) {
		List<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file, fileEnd));
			} else {
				if (file.getName().endsWith(fileEnd)) {
					list.add(file);
				}
			}
		}
		return list;
	}

	public static List<File> listFilesC(File directory, String contains) {
		List<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file, contains));
			} else {
				if (file.getName().contains(contains)) {
					list.add(file);
				}
			}
		}
		return list;
	}

	public static List<File> listFiles(File directory, String fileEnd, String containsNot) {
		List<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file, fileEnd, containsNot));
			} else {
				if (file.getName().endsWith(fileEnd)) {
					if (!file.getName().contains(containsNot)) {
						list.add(file);
					}
				}
			}
		}
		return list;
	}

	public static <E> ArrayList<E> createWithType(Class<E> sample) {
		return new ArrayList<>();
	}
	
	public static <E> ArrayList<E> createWithType(E sample) {
		return new ArrayList<>();
	}

}
