package com.syntaxphoenix.syntaxapi.nbt;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NbtNumber {
	
	public Number asNumber();
	
	public void setValue(Number number);
	
	public default byte asByte() {
		return asNumber().byteValue();
	}
	
	public default short asShort() {
		return asNumber().shortValue();
	}
	
	public default int asInt() {
		return asNumber().intValue();
	}
	
	public default float asFloat() {
		return asNumber().floatValue();
	}
	
	public default long asLong() {
		return asNumber().longValue();
	}
	
	public default double asDouble() {
		return asNumber().doubleValue();
	}
	
	public default BigInteger asBigInteger() {
		return new BigInteger(asNumber().toString());
	}
	
	public default BigDecimal asBigDecimal() {
		return new BigDecimal(asNumber().toString());
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
