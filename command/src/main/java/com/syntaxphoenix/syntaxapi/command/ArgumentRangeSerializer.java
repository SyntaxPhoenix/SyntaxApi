package com.syntaxphoenix.syntaxapi.command;

import com.syntaxphoenix.syntaxapi.command.range.*;

public abstract class ArgumentRangeSerializer {

	public static final ArgumentRangeSerializer DEFAULT = new DefaultArgumentRangeSerializer();

	public String toString(BaseArgumentRange range) {
		RangeType type = range.getType();
		String output = "";
		switch (type) {
		case TEXT_SIZE_RANGE:
			output = toString(range.asTextSize());
			break;
		case TEXT_CHOOSE_RANGE:
			output = toString(range.asTextChoose());
			break;
		case STATE_RANGE:
			output = toString(range.asState());
			break;
		case COLLECTION_SIZE_RANGE:
			output = toString(range.asCollectionSize());
			break;
		case NUMBER_VALUE_RANGE:
			output = toString(range.asNumberValue());
			break;
		case NUMBER_CHOOSE_RANGE:
			output = toString(range.asNumberChoose());
			break;
		case CUSTOM:
			output = range.toString();
			break;
		}
		return output;
	}

	public abstract String toString(CollectionSizeRange<?> range);

	public abstract String toString(NumberValueRange range);

	public abstract String toString(NumberChooseRange range);

	public abstract String toString(StateRange range);

	public abstract String toString(TextSizeRange range);

	public abstract String toString(TextChooseRange range);

	/**
	 * @param ranges array of complex ranges
	 * @return array of raw ranges
	 */
	public abstract String[] asStringArray(BaseArgumentRange... ranges);

}
