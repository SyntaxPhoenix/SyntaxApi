package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class Lists {
	
	public static String getEqualsIgnoreCase(String equals, List<String> list) {
		if(list.isEmpty()) return equals;
		for(String input : list) {
			if(input.equalsIgnoreCase(equals)) {
				return input;
			}
		}
		return equals;
	}

	public static boolean containsEqualsIgnoreCase(String equals, List<String> list) {
		if(list.isEmpty()) return false;
		for (String input : list) {
			if (input.equalsIgnoreCase(equals)) {
				return true;
			}
		}
		return false;
	}

	public static String[] toStringArray(Collection<String[]> collection) {
		List<String> output = new ArrayList<>();
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

	public static <E> List<List<E>> partition(List<E> list, int amount) {
		List<List<E>> parition = new ArrayList<>();
		List<E> current = new ArrayList<>();
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
	public static ArrayList<String> merge(List<String>... lists) {
		ArrayList<String> output = new ArrayList<>();
		for (List<String> list : lists) {
			output.addAll(list);
		}
		return output;
	}

	public static <E> List<E> createWithType(Class<E> sample) {
		return new ArrayList<>();
	}
	
	public static <E> List<E> createWithType(E sample) {
		return new ArrayList<>();
	}

}
