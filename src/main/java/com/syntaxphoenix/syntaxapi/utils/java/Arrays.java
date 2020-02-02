package com.syntaxphoenix.syntaxapi.utils.java;

import java.lang.reflect.Array;
import java.util.List;

public class Arrays {

	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <E> E[] merge(E[] array1, E... array2) {
		List<E> list = Lists.asList(array1);
		list.addAll(Lists.asList(array2));
		return (E[]) list.toArray();
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

	public static void copy1D(Object input1, Object output1) {
		System.arraycopy(input1, 0, output1, 0, Array.getLength(output1));
	}

	public static void copy2D(Object input1, Object output1) {
		int size1 = Array.getLength(input1);
		System.arraycopy(input1, 0, output1, 0, Array.getLength(output1));
		for (int index1 = 0; index1 < size1; index1++) {
			Object input2 = Array.get(input1, index1);
			Object output2 = Array.get(output1, index1);
			System.arraycopy(input2, 0, output2, 0, Array.getLength(output2));
		}
	}

	public static void copy3D(Object input1, Object output1) {
		int size1 = Array.getLength(input1);
		System.arraycopy(input1, 0, output1, 0, Array.getLength(output1));
		for (int index1 = 0; index1 < size1; index1++) {
			Object input2 = Array.get(input1, index1);
			Object output2 = Array.get(output1, index1);
			int size2 = Array.getLength(input2);
			System.arraycopy(input2, 0, output2, 0, Array.getLength(output2));
			for (int index2 = 0; index2 < size2; index2++) {
				Object input3 = Array.get(input2, index2);
				Object output3 = Array.get(output2, index2);
				System.arraycopy(input3, 0, output3, 0, Array.getLength(output3));
			}
		}
	}

	public static void extendCopy1D(Object input1, int size1) {
		System.arraycopy(input1, 0, input1, 0, size1);
	}

	public static void extendCopy2D(Object input1, int size1, int size2) {
		System.arraycopy(input1, 0, input1, 0, size1);
		for (int index1 = 0; index1 < size1; index1++) {
			Object input2 = Array.get(input1, index1);
			System.arraycopy(input2, 0, input2, 0, size2);
		}
	}

	public static void extendCopy3D(Object input1, int size1, int size2, int size3) {
		System.arraycopy(input1, 0, input1, 0, size1);
		for (int index1 = 0; index1 < size1; index1++) {
			Object input2 = Array.get(input1, index1);
			System.arraycopy(input2, 0, input2, 0, size2);
			for (int index2 = 0; index2 < size2; index2++) {
				Object input3 = Array.get(input2, index2);
				System.arraycopy(input3, 0, input3, 0, size3);
			}
		}
	}

}
