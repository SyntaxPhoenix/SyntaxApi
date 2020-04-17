package com.syntaxphoenix.syntaxapi.command.range;

import com.syntaxphoenix.syntaxapi.command.ArgumentRangeSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.RangeType;

public class StateRange extends BaseArgumentRange {

	private final boolean enabled;
	private final boolean disallowed;

	public StateRange() {
		this.enabled = false;
		this.disallowed = false;
	}

	public StateRange(boolean disallowed) {
		this.enabled = true;
		this.disallowed = disallowed;
	}
	
	/*
	 * 
	 */
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean getDisallowed() {
		return disallowed;
	}
	
	/*
	 * 
	 */

	@Override
	public RangeType getType() {
		return RangeType.STATE_RANGE;
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
		if (!enabled)
			return true;
		return ((boolean) argument.asObject() != disallowed);
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
