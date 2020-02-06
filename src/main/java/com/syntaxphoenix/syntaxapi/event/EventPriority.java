package com.syntaxphoenix.syntaxapi.event;

import java.util.List;

import com.google.common.collect.ImmutableList;

public enum EventPriority {

	LOWEST(-2), LOW(-1), NORMAL(0), HIGH(1), HIGHEST(2);
	
	/*
	 * 
	 */
	
	public static final List<EventPriority> ORDERED_VALUES = ImmutableList.of(HIGHEST, HIGH, NORMAL, LOW, LOWEST);
	
	/*
	 * 
	 */

	private int priority;

	private EventPriority(int priority) {
		this.priority = priority;
	}
	
	public int priority() {
		return priority;
	}

}
