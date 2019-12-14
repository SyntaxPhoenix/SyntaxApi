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

}
