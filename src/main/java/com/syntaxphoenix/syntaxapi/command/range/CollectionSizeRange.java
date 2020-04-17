package com.syntaxphoenix.syntaxapi.command.range;

import java.util.Collection;

import com.syntaxphoenix.syntaxapi.command.ArgumentRangeSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.RangeType;

public class CollectionSizeRange<T> extends BaseArgumentRange {
	
	private final int minimum;
	private final int maximum;
	
	private final Class<T> type;

	public CollectionSizeRange(int minimum, int maximum, Class<T> type) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.type = type;
	}
	
	public CollectionSizeRange(NumberValueRange number, Class<T> type) {
		this.minimum = number.getMinimum().intValue();
		this.maximum = number.getMaximum().intValue();
		this.type = type;
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
		return RangeType.COLLECTION_SIZE_RANGE;
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
		Collection<?> collection = ((Collection<?>) argument.asObject());
		int length = collection.size();
		if(length != 0) {
			Object object = collection.iterator().next();
			if(!type.isInstance(object)) {
				return false;
			}
		}
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
