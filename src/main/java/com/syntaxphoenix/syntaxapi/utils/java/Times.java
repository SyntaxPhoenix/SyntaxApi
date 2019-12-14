package com.syntaxphoenix.syntaxapi.utils.java;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Times {
	
	public static String getTime(String x) {

		Calendar cal = (Calendar) Calendar.getInstance().clone();
		SimpleDateFormat sdf = new SimpleDateFormat("HH" + x + "mm" + x + "ss");
		
		return sdf.format(cal.getTime());
		
	}

	public static String getDate(String x) {
		
		Calendar cal = (Calendar) Calendar.getInstance().clone();
		SimpleDateFormat sdf = new SimpleDateFormat("dd" + x + "MM" + x + "yyyy");
		
		return sdf.format(cal.getTime());
		
	}
	
	public static String getDateAfter(String x, int days) {

		Calendar cal = (Calendar) Calendar.getInstance().clone();
		SimpleDateFormat sdf = new SimpleDateFormat("dd" + x + "MM" + x + "yyyy");
		
		cal.add(Calendar.DATE, days);
		
		return sdf.format(cal.getTime());
		
	}

}
