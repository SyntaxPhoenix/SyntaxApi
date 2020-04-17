package com.syntaxphoenix.syntaxapi.command;

import java.util.List;

import com.syntaxphoenix.syntaxapi.command.range.*;
import com.syntaxphoenix.syntaxapi.utils.java.lang.StringBuilder;

public class DefaultArgumentRangeSerializer extends ArgumentRangeSerializer {

	public static final String SIZE_FORMAT = "%s[%s,%s]";
	public static final String COLLECTION_SIZE_FORMAT = "%s::%s[%s,%s]";

	public static final String CHOOSE_FORMAT = "%s[%s:%s]";

	/*
	 * 
	 */

	@Override
	public String toString(CollectionSizeRange<?> range) {
		return String.format(COLLECTION_SIZE_FORMAT, "collection0", range.getInputType().toString(), range.getMinimum(),
				range.getMaximum());
	}

	@Override
	public String toString(NumberValueRange range) {
		return String.format(SIZE_FORMAT, "number0", range.getMinimum().toString(), range.getMaximum().toString());
	}

	@Override
	public String toString(NumberChooseRange range) {
		return String.format(CHOOSE_FORMAT, "number1", range.isBlacklist(), toString(range.getValues()));
	}

	@Override
	public String toString(StateRange range) {
		return String.format(CHOOSE_FORMAT, "state0", range.isEnabled(), range.getDisallowed());
	}

	@Override
	public String toString(TextSizeRange range) {
		return String.format(SIZE_FORMAT, "text0", range.getMinimum(), range.getMaximum());
	}

	@Override
	public String toString(TextChooseRange range) {
		return String.format(CHOOSE_FORMAT, "text1", range.isBlacklist(), toString(range.getValues()));
	}

	/*
	 * 
	 */

	@Override
	public String[] asStringArray(BaseArgumentRange... ranges) {
		if (ranges == null || ranges.length == 0) {
			return new String[0];
		}
		int length = ranges.length;
		String[] array = new String[length];
		for (int index = 0; index < length; index++) {
			array[index] = ranges[index].toString(this);
		}
		return array;
	}

	/*
	 * 
	 */

	private String toString(List<?> values) {
		StringBuilder builder = new StringBuilder();
		if (!values.isEmpty()) {
			for (Object value : values) {
				builder.append(value.toString());
				builder.append(',');
			}
		}
		String built = builder.toStringClear();
		return built.substring(0, built.length() - 1);
	}

}
