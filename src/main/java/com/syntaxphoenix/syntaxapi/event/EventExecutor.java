package com.syntaxphoenix.syntaxapi.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public final class EventExecutor implements Comparable<EventExecutor> {

	private final EnumMap<EventPriority, ArrayList<EventMethod>> methods = new EnumMap<>(EventPriority.class);
	private final Class<? extends Event> event;
	private final EventListener listener;
	private final EventManager manager;
	
	public EventExecutor(EventManager manager, EventListener listener, Class<? extends Event> event) {
		this.listener = listener;
		this.manager = manager;
		this.event = event;
	}
	
	/*
	 * 
	 */
	
	public final EventManager getManager() {
		return manager;
	}
	
	public final EventListener getListener() {
		return listener;
	}
	
	public final Class<? extends Event> getEvent() {
		return event;
	}
	
	/*
	 * 
	 */
	
	protected EventExecutor add(EventPriority priority, EventMethod method) {
		ArrayList<EventMethod> list = methods.get(priority);
		if(list == null) {
			methods.put(priority, list = new ArrayList<>());
		} else if(list.contains(method)) {
			return this;
		}
		list.add(method);
		return this;
	}
	
	public List<EventMethod> getMethodsByPriority(EventPriority priority) {
		if(!methods.containsKey(priority)) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(methods.get(priority));
	}
	
	public List<EventMethod> getMethods() {
		ArrayList<EventMethod> output = new ArrayList<>();
		methods.forEach((priority, list) -> output.addAll(list));
		return output;
	}
	
	/*
	 * 
	 */

	@Override
	public int compareTo(EventExecutor o) {
		Class<? extends Event> other = o.getEvent();
		if(event.equals(other))
			return 0;
		if(event.isAssignableFrom(other))
			return -1;
		return 1;
	}
	
}
