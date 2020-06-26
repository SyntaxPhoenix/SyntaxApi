package com.syntaxphoenix.syntaxapi.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.utils.general.Status;
import com.syntaxphoenix.syntaxapi.utils.java.Collect;

/**
 * 
 * @author Lauriichen
 *
 */

public class EventManager {

	private final LinkedHashMap<Class<? extends Event>, ArrayList<EventExecutor>> listeners = new LinkedHashMap<>();
	private final ILogger logger;

	private final ExecutorService service;

	public EventManager() {
		this(null, null);
	}

	public EventManager(ExecutorService service) {
		this(null, service);
	}

	public EventManager(ILogger logger) {
		this(logger, null);
	}

	public EventManager(ILogger logger, ExecutorService service) {
		this.logger = logger;
		this.service = service;
	}

	/*
	 * Getter / Infos
	 */

	public boolean hasLogger() {
		return logger != null;
	}

	public ILogger getLogger() {
		return logger;
	}

	public boolean isAsync() {
		return service != null;
	}

	public ExecutorService getExecutorService() {
		return service;
	}

	/*
	 * Event calls
	 */

	public EventCall generateCall(Event event) {
		return new EventCall(this, event, getExecutorsForEvent(event.getClass(), true));
	}

	public Status call(Event event) {
		return call(generateCall(event));
	}

	public Status call(EventCall call) {
		if (isAsync())
			return call.executeAsync(service);
		else
			return call.execute();
	}

	public Status callAsync(Event event, ExecutorService service) {
		return callAsync(generateCall(event), service);
	}

	public Status callAsync(EventCall call, ExecutorService service) {
		return call.executeAsync(service);
	}

	/*
	 * Registration
	 */

	// Register

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

	// Unregister

	public EventManager unregisterEvent(Class<? extends Event> event) {
		listeners.remove(event);
		return this;
	}

	public EventManager unregisterEvents(Class<? extends EventListener> listener) {
		return unregisterExecutors(getExecutorsFromOwner(listener));
	}

	public EventManager unregisterEvents(EventListener listener) {
		return unregisterExecutors(getExecutorsFromOwner(listener));
	}

	public EventManager unregisterExecutors(Iterable<EventExecutor> executors) {
		return unregisterExecutors(executors.iterator());
	}

	public EventManager unregisterExecutors(Iterator<EventExecutor> executors) {
		while (executors.hasNext())
			unregisterExecutor(executors.next());
		return this;
	}

	public EventManager unregisterExecutors(EventExecutor... executors) {
		for (EventExecutor executor : executors)
			unregisterExecutor(executor);
		return this;
	}

	public EventManager unregisterExecutor(EventExecutor executor) {
		ArrayList<EventExecutor> list = listeners.get(executor.getEvent());
		if (list != null && list.contains(executor))
			list.remove(executor);
		return this;
	}

	/*
	 * Owners
	 */

	public List<? extends EventListener> getOwners() {
		return getExecutors().stream().collect(Collect.collectList((output, input) -> {
			if (!output.contains(input.getListener()))
				output.add(input.getListener());
		}));
	}

	public List<Class<? extends EventListener>> getOwnerClasses() {
		return getOwners().stream().collect(Collect.collectList((output, input) -> output.add(input.getClass())));
	}

	/*
	 * Events
	 */

	public List<Class<? extends Event>> getEvents() {
		return listeners.keySet().stream().collect(Collectors.toList());
	}

	/*
	 * Executors
	 */

	public List<EventExecutor> getExecutors() {
		return listeners.values().stream().collect(Collect.combineList());
	}

	public List<EventExecutor> getExecutorsFromOwner(EventListener listener) {
		return getExecutors().stream().filter(executor -> executor.getListener() == listener)
				.collect(Collectors.toList());
	}

	public List<EventExecutor> getExecutorsFromOwner(Class<? extends EventListener> listener) {
		return getExecutors().stream().filter(executor -> executor.getListener().getClass() == listener)
				.collect(Collectors.toList());
	}

	public List<EventExecutor> getExecutorsForEvent(Class<? extends Event> event) {
		return getExecutorsForEvent(event, false);
	}

	@SuppressWarnings("unchecked")
	public List<EventExecutor> getExecutorsForEvent(Class<? extends Event> event, boolean allowAssignableClasses) {
		if (!allowAssignableClasses) {
			if (!listeners.containsKey(event)) {
				return new ArrayList<>();
			}
			return (List<EventExecutor>) listeners.get(event).clone();
		}
		ArrayList<EventExecutor> executors = new ArrayList<>();
		Set<Class<? extends Event>> keys = listeners.keySet();
		for (Class<? extends Event> assign : keys) {
			if (assign.isAssignableFrom(event)) {
				executors.addAll(listeners.get(assign));
			}
		}
		return (List<EventExecutor>) executors.clone();
	}

}
