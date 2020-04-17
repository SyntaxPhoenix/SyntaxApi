package com.syntaxphoenix.syntaxapi.command.range;

import com.syntaxphoenix.syntaxapi.command.ArgumentRangeSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.RangeType;

public class TextSizeRange extends BaseArgumentRange {

	private final int minimum;
	private final int maximum;

	public TextSizeRange(int minimum, int maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public TextSizeRange(NumberValueRange number) {
		this.minimum = number.getMinimum().intValue();
		this.maximum = number.getMaximum().intValue();
	}

	/*
	 * 
	 */

	public int getMinimum() {
		return minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	/*
	 * 
	 */

	@Override
	public RangeType getType() {
		return RangeType.TEXT_SIZE_RANGE;
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
		int length = ((CharSequence) argument.asObject()).length();
		return length >= minimum && length <= maximum;
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
