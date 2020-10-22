package com.syntaxphoenix.syntaxapi.nbt;

import java.math.BigDecimal;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class NbtBigDecimal extends NbtString {

	private BigDecimal bigDecimal;

	public NbtBigDecimal() {

	}

	public NbtBigDecimal(String value) {
		super(Strings.isNumeric(value) ? value : "0");
	}

	public NbtBigDecimal(BigDecimal decimal) {
		super(decimal.toString());
		this.bigDecimal = decimal;
	}

	@Override
	public boolean isBigInteger() {
		return true;
	}

	public BigDecimal getDecimal() {
		return bigDecimal;
	}

	@Override
	public String getValue() {
		return super.getValue();
	}

	@Override
	public void setValue(String value) {
		if (!Strings.isNumeric(value)) {
			return;
		}
		setValue(new BigDecimal(value));
	}

	public void setValue(BigDecimal integer) {
		super.setValue((bigDecimal = integer).toString());
	}

	@Override
	public NbtString clone() {
		return new NbtBigInt();
	}

}
