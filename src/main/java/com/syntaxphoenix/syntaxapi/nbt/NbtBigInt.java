package com.syntaxphoenix.syntaxapi.nbt;

import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class NbtBigInt extends NbtString {
	
	private BigInteger bigInteger;
	
	public NbtBigInt() {
		
	}
	
	public NbtBigInt(String value) {
		super(Strings.isNumeric(value) ? value : "0");
	}
	
	public NbtBigInt(BigInteger integer) {
		super(integer.toString());
		this.bigInteger = integer;
	}
	
	@Override
	public boolean isBigInteger() {
		return true;
	}
	
	public BigInteger getInteger() {
		return bigInteger;
	}
	
	@Override
	public String getValue() {
		return super.getValue();
	}
	
	@Override
	public void setValue(String value) {
		if(!Strings.isNumeric(value)) {
			return;
		}
		setValue(new BigInteger(value));
	}
	
	public void setValue(BigInteger integer) {
		super.setValue((bigInteger = integer).toString());
	}
	
	@Override
	public NbtString clone() {
		return new NbtBigInt();
	}
	
}
