package com.syntaxphoenix.syntaxapi.event;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
		return new EventCall(this, event, getExecutors(event.getClass()));
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
	
	public List<EventExecutor> getExecutors(Class<? extends Event> event) {
		if(!listeners.containsKey(event)) {
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(listeners.get(event));
	}

}
