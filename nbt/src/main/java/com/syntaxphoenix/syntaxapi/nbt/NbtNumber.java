package com.syntaxphoenix.syntaxapi.nbt;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NbtNumber {
	
	public Number getValue();
	
	public void setValue(Number number);
	
	public default byte asByte() {
		return getValue().byteValue();
	}
	
	public default short asShort() {
		return getValue().shortValue();
	}
	
	public default int asInt() {
		return getValue().intValue();
	}
	
	public default float asFloat() {
		return getValue().floatValue();
	}
	
	public default long asLong() {
		return getValue().longValue();
	}
	
	public default double asDouble() {
		return getValue().doubleValue();
	}
	
	public default BigInteger asBigInteger() {
		return new BigInteger(getValue().toString());
	}
	
	public default BigDecimal asBigDecimal() {
		return new BigDecimal(getValue().toString());
	}

	public static NbtTag fromNumber(Number raw) {
		if(raw instanceof Byte) {
			return new NbtByte(raw.byteValue());
		} else if(raw instanceof Short) {
			return new NbtShort(raw.shortValue());
		} else if(raw instanceof Integer) {
			return new NbtInt(raw.intValue());
		} else if(raw instanceof Float) {
			return new NbtFloat(raw.floatValue());
		} else if(raw instanceof Long) {
			return new NbtLong(raw.longValue());
		} else if(raw instanceof Double) {
			return new NbtDouble(raw.doubleValue());
		} else if(raw instanceof BigInteger) {
			return new NbtBigInt((BigInteger) raw);
		} else if(raw instanceof BigDecimal) {
			return new NbtBigDecimal((BigDecimal) raw);
		} else {
			return new NbtInt(raw.intValue());
		}
	}

}
