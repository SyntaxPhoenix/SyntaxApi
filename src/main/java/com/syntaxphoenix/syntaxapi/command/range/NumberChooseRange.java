package com.syntaxphoenix.syntaxapi.command.range;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.syntaxphoenix.syntaxapi.command.ArgumentRangeSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.RangeType;

public class NumberChooseRange extends BaseArgumentRange {

	private final boolean blacklist;
	private final List<BigDecimal> values;

	public NumberChooseRange(Object... values) {
		this(false, values);
	}

	public NumberChooseRange(boolean blacklist, Object... values) {
		this.blacklist = blacklist;
		this.values = asBigDecimal(values);
	}

	public NumberChooseRange(List<Object> values) {
		this(false, values);
	}

	public NumberChooseRange(boolean blacklist, List<Object> values) {
		this.blacklist = blacklist;
		this.values = asBigDecimal(values);
	}

	/*
	 * 
	 */

	public boolean isBlacklist() {
		return blacklist;
	}

	public List<BigDecimal> getValues() {
		return values;
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
		BigDecimal compare = new BigDecimal(argument.asObject().toString());
		return values.stream().anyMatch(value -> value.compareTo(compare) == 0);
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

	/*
	 * 
	 */

	public static List<BigDecimal> asBigDecimal(Object... objects) {
		ArrayList<BigDecimal> list = new ArrayList<>();
		if (objects == null || objects.length == 0)
			return list;
		for (Object object : objects) {
			try {
				list.add(new BigDecimal(object.toString()));
			} catch (NumberFormatException ignore) {
				continue;
			}
		}
		return list;
	}

	public static List<BigDecimal> asBigDecimal(List<Object> objects) {
		ArrayList<BigDecimal> list = new ArrayList<>();
		if (objects == null || objects.isEmpty())
			return list;
		for (Object object : objects) {
			try {
				list.add(new BigDecimal(object.toString()));
			} catch (NumberFormatException ignore) {
				continue;
			}
		}
		return list;
	}
}
