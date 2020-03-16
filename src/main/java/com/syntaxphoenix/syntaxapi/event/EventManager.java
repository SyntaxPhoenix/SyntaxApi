package com.syntaxphoenix.syntaxapi.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.syntaxphoenix.syntaxapi.logging.SynLogger;

/**
 * 
 * @author Lauriichen
 *
 */

public class EventManager {

	private final LinkedHashMap<Class<? extends Event>, ArrayList<EventExecutor>> listeners = new LinkedHashMap<>();
	private final SynLogger logger;

	public EventManager() {
		this.logger = null;
	}

	public EventManager(SynLogger logger) {
		this.logger = logger;
	}

	/*
	 * 
	 */

	public boolean hasLogger() {
		return logger != null;
	}

	public SynLogger getLogger() {
		return logger;
	}

	/*
	 * 
	 */

	public EventCall generateCall(Event event) {
		return new EventCall(this, event, getExecutors(event.getClass(), true));
	}

	public EventResult call(Event event) {
		return call(generateCall(event));
	}

	public EventResult call(EventCall call) {
		return call.execute();
	}

	/*
	 * 
	 */

	public EventManager registerEvents(EventListener listener) {
		EventAnalyser analyser = new EventAnalyser(listener);
		analyser.registerEvents(this);
		return this;
	}

	public EventManager registerEvent(EventMethod method) {
		if (!method.isValid()) {
			return this;
		}
		EventExecutor executor = new EventExecutor(this, method.getListener(), method.getEvent());
		executor.add(method.hasEventHandler() ? method.getHandler().priority() : EventPriority.NORMAL, method);
		registerExecutor(executor);
		return this;
	}

	/*
	 * 
	 */

	public EventManager registerExecutors(Collection<EventExecutor> executors) {
		executors.forEach(executor -> registerExecutor(executor));
		return this;
	}

	public EventManager registerExecutor(EventExecutor executor) {
		if (executor == null || executor.getMethods().isEmpty()) {
			return this;
		}
		Class<? extends Event> event = executor.getEvent();
		if (listeners.containsKey(event)) {
			ArrayList<EventExecutor> executors = listeners.get(event);
			if (!executors.contains(executor)) {
				executors.add(executor);
			}
		} else if (!listeners.containsKey(event)) {
			ArrayList<EventExecutor> executors = new ArrayList<>();
			executors.add(executor);
			listeners.put(event, executors);
		}
		return this;
	}

	/*
	 * 
	 */

	public List<EventExecutor> getExecutors(Class<? extends Event> event) {
		return getExecutors(event, false);
	}

	public List<EventExecutor> getExecutors(Class<? extends Event> event, boolean allowAssignableClasses) {
		if (!allowAssignableClasses) {
			if (!listeners.containsKey(event)) {
				return ImmutableList.of();
			}
			return ImmutableList.copyOf(listeners.get(event));
		}
		ArrayList<EventExecutor> executors = new ArrayList<>();
		Set<Class<? extends Event>> keys = listeners.keySet();
		for(Class<? extends Event> assign : keys) {
			if(assign.isAssignableFrom(event) || assign.equals(event)) {
				executors.addAll(listeners.get(assign));
			}
		}
		return ImmutableList.copyOf(executors);
	}

}
