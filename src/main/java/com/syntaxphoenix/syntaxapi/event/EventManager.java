package com.syntaxphoenix.syntaxapi.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.utils.general.Status;

/**
 * 
 * @author Lauriichen
 *
 */

public class EventManager {

	private final LinkedHashMap<Class<? extends Event>, ArrayList<EventExecutor>> listeners = new LinkedHashMap<>();
	private final ILogger logger;

	public EventManager() {
		this.logger = null;
	}

	public EventManager(ILogger logger) {
		this.logger = logger;
	}

	/*
	 * 
	 */

	public boolean hasLogger() {
		return logger != null;
	}

	public ILogger getLogger() {
		return logger;
	}

	/*
	 * 
	 */

	public EventCall generateCall(Event event) {
		return new EventCall(this, event, getExecutors(event.getClass(), true));
	}

	public Status call(Event event) {
		return call(generateCall(event));
	}

	public Status call(EventCall call) {
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

	@SuppressWarnings("unchecked")
	public List<EventExecutor> getExecutors(Class<? extends Event> event, boolean allowAssignableClasses) {
		if (!allowAssignableClasses) {
			if (!listeners.containsKey(event)) {
				return new ArrayList<>();
			}
			return (List<EventExecutor>) listeners.get(event).clone();
		}
		ArrayList<EventExecutor> executors = new ArrayList<>();
		Set<Class<? extends Event>> keys = listeners.keySet();
		for(Class<? extends Event> assign : keys) {
			if(assign.isAssignableFrom(event)) {
				executors.addAll(listeners.get(assign));
			}
		}
		return (List<EventExecutor>) executors.clone();
	}

}
