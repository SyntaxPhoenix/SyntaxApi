package com.syntaxphoenix.syntaxapi.utils.java;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Times {

	public static String getTime(String x) {
		return new SimpleDateFormat("HH" + x + "mm" + x + "ss").format((Calendar.getInstance()).getTime());

	}

	public static String getDate(String x) {
		return new SimpleDateFormat("dd" + x + "MM" + x + "yyyy").format((Calendar.getInstance()).getTime());

	}

	public static String getDateAfter(String x, int days) {
		Calendar calendar = (Calendar) Calendar.getInstance().clone();
		calendar.add(Calendar.DATE, days);
		return new SimpleDateFormat("dd" + x + "MM" + x + "yyyy").format(calendar.getTime());
	}

}
