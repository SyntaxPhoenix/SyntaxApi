package com.syntaxphoenix.syntaxapi.event;

import java.util.ArrayList;

import com.syntaxphoenix.syntaxapi.utils.priority.PrioritisedList;

public class EventCall {

	private final PrioritisedList<EventPriority, EventListener> listeners;
	private final Event event;
	
	public EventCall(Event event, PrioritisedList<EventPriority, EventListener> listeners) {
		this.listeners = listeners;
		this.event = event;
	}
	
	public final Event getEvent() {
		return event;
	}
	
	public final PrioritisedList<EventPriority, EventListener> getListeners() {
		return listeners;
	}
	
	public final ArrayList<EventListener> getListenersOfPriority(EventPriority priority) {
		return listeners.getValuesOfPriority(priority);
	}

}
