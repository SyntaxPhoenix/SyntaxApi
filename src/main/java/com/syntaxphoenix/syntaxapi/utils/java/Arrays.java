package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.List;

public class Arrays {

	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <E> E[] merge(E[] cmp1, E... cmp2) {
		List<E> lv = Lists.asList(cmp1);
		lv.addAll(Lists.asList(cmp2));
		return (E[]) lv.toArray();
	}

	public static String[] subArray(String[] args, int index) {
		if(index < 0 || index >= args.length) {
			return new String[0];
		}
		int out = 0;
		String[] output = new String[args.length - index];
		for(; index < args.length; index++, out++) {
			output[out] = args[index];
		}
		return output;
	}

}
