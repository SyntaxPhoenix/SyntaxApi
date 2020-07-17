package com.syntaxphoenix.syntaxapi.utils.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class ValueIdentifier {

	public static Object identify(String value) {
		if (value.length() == 1)
			return value.charAt(0);
		if (Strings.isBoolean(value)) {
			return Boolean.valueOf(value);
		}
		if (Strings.isNumeric(value)) {
			try {
				return Byte.parseByte(value);
			} catch (NumberFormatException e) {
			}
			try {
				return Short.parseShort(value);
			} catch (NumberFormatException e) {
			}
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
			}
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
			}
			return new BigInteger(value);
		}
		if (Strings.isDecimal(value)) {
			String number = value.split("\\.")[0];
			try {
				Integer.parseInt(number);
				return Float.parseFloat(value);
			} catch (NumberFormatException e) {
			}
			try {
				Long.parseLong(number);
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
			}
			return new BigDecimal(value);
		}
		return value;
	}

}
