package com.syntaxphoenix.syntaxapi.event;

import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.utils.priority.PrioritisedList;

/**
 * 
 * @author Lauriichen
 *
 */

public class EventManager {

	private HashMap<Class<? extends Event>, PrioritisedList<EventPriority, EventListener>> listeners = new HashMap<>();

	public EventCall generateCall(Event event) {
		return new EventCall(event, getCopy(event.getClass()));
	}

	private PrioritisedList<EventPriority, EventListener> getCopy(Class<? extends Event> clazz) {
		if (listeners.containsKey(clazz)) {
			return listeners.get(clazz).copy();
		} else {
			PrioritisedList<EventPriority, EventListener> list = new PrioritisedList<>(EventPriority.NORMAL);
			listeners.put(clazz, list);
			return list.copy();
		}
	}

}
