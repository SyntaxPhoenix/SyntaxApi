package com.syntaxphoenix.syntaxapi.command.range;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.command.ArgumentRangeSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentSuperType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.RangeType;

public class NumberChooseRange extends BaseArgumentRange {

	private final BigDecimal minimum;
	private final BigDecimal maximum;

	public NumberChooseRange(int minimum, int maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(byte minimum, byte maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(short minimum, short maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(float minimum, float maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(double minimum, double maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(long minimum, long maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(BigInteger minimum, BigInteger maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(BigDecimal minimum, BigDecimal maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public NumberChooseRange(char minimum, char maximum) {
		this.minimum = new BigDecimal(minimum);
		this.maximum = new BigDecimal(maximum);
	}

	public NumberChooseRange(Number minimum, Number maximum) {
		this.minimum = new BigDecimal(minimum.toString());
		this.maximum = new BigDecimal(maximum.toString());
	}

	public NumberChooseRange(NumberChooseRange number) {
		this.minimum = number.getMinimum();
		this.maximum = number.getMaximum();
	}

	/*
	 * 
	 */

	public BigDecimal getMinimum() {
		return minimum;
	}

	public BigDecimal getMaximum() {
		return maximum;
	}

	/*
	 * 
	 */

	@Override
	public RangeType getType() {
		return RangeType.NUMBER_CHOOSE_RANGE;
	}

	@Override
	public Class<?> getInputType() {
		return getType().getInputType();
	}

	@Override
	public boolean hasType(BaseArgument argument) {
		return argument.getClassType().isAssignableFrom(getInputType());
	}

	@Override
	public boolean isInRange(BaseArgument argument) {
		if (!hasType(argument))
			return false;
		Number number = (Number) argument.asObject();
		BigDecimal decimal;
		if (!(number instanceof BigDecimal))
			decimal = new BigDecimal(number.toString());
		else
			decimal = (BigDecimal) number;
		return decimal.compareTo(minimum) >= 0 && decimal.compareTo(maximum) <= 0;
	}
	
	/*
	 * 
	 */

	@Override
	public String toString() {
		return toString(ArgumentRangeSerializer.DEFAULT);
	}
	
	@Override
	public String toString(ArgumentRangeSerializer serializer) {
		return serializer.toString(this);
	}

}
