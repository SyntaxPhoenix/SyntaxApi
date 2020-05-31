package com.syntaxphoenix.syntaxapi.net.http;

import com.syntaxphoenix.syntaxapi.event.EventManager;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

public class EventRequestHandler implements RequestHandler {
	
	private final EventManager manager;
	
	/*
	 * 
	 */
	
	public EventRequestHandler() {
		this(new EventManager());
	}
	
	public EventRequestHandler(ILogger logger) {
		this(new EventManager(logger));
	}
	
	public EventRequestHandler(EventManager manager) {
		super();
		this.manager = manager;
	}
	
	/*
	 * 
	 */
	
	public final EventManager getEventManager() {
		return manager;
	}
	
	/*
	 * 
	 */
	
	public void handleRequest() {
		
	}

}
