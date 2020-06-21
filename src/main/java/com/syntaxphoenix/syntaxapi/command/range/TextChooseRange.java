package com.syntaxphoenix.syntaxapi.command.range;

import java.util.Arrays;
import java.util.List;

import com.syntaxphoenix.syntaxapi.command.ArgumentRangeSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.RangeType;

public class TextChooseRange extends BaseArgumentRange {

	private final boolean blacklist;
	private final List<CharSequence> values;

	public TextChooseRange(CharSequence... values) {
		this(false, values);
	}

	public TextChooseRange(boolean blacklist, CharSequence... values) {
		this(blacklist, Arrays.asList(values));
	}

	public TextChooseRange(List<CharSequence> values) {
		this(false, values);
	}

	public TextChooseRange(boolean blacklist, List<CharSequence> values) {
		this.blacklist = blacklist;
		this.values = values;
	}

	/*
	 * 
	 */

	public boolean isBlacklist() {
		return blacklist;
	}

	public List<CharSequence> getValues() {
		return values;
	}

	/*
	 * 
	 */

	@Override
	public RangeType getType() {
		return RangeType.TEXT_CHOOSE_RANGE;
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
		return values.contains((CharSequence) argument.asObject());
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
