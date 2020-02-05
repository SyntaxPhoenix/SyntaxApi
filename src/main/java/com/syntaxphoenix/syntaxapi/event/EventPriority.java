package com.syntaxphoenix.syntaxapi.event;

import com.syntaxphoenix.syntaxapi.utils.priority.Priority;

public enum EventPriority implements Priority<EventPriority> {
	NORMAL
	;

	@Override
	public boolean isHigher(Priority<EventPriority> priority) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSame(Priority<EventPriority> priority) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Priority<EventPriority> getDefault() {
		return null;
	}

}
