package com.syntaxphoenix.syntaxapi.utils.java;

public class Numbers {

	public static Integer random(Integer limit) {
		return Math.round(Math.round(Math.random() * limit));
	}

	public static Integer random(Integer start, Integer limit) {
		Integer back = random(limit);
		while (back < start) {
			back = random(limit);
		}
		return back;
	}

	public static Integer toPlus(Integer in) {
		if (isNegative(in)) {
			return changeSign(in);
		}
		return in;
	}

	public static Integer changeSign(Integer in) {
		return in * (-1);
	}

	public static boolean isOddNumber(Integer in) {
		String st = in.toString();
		if (st.endsWith("0")) {
			return false;
		}
		if (st.endsWith("2")) {
			return false;
		}
		if (st.endsWith("4")) {
			return false;
		}
		if (st.endsWith("6")) {
			return false;
		}
		if (st.endsWith("8")) {
			return false;
		}
		return true;
	}

	public static boolean isNegative(Integer in) {
		String msg = "" + in;
		if (msg.startsWith("-")) {
			return true;
		} else {
			return false;
		}
	}

	public static Double toPlus(Double in) {
		if (isNegative(in)) {
			return changeSign(in);
		}
		return in;
	}

	public static boolean isNegative(Double in) {
		String msg = "" + in;
		if (msg.startsWith("-")) {
			return true;
		} else {
			return false;
		}
	}

	public static Double changeSign(Double in) {
		return in * (-1);
	}

}
