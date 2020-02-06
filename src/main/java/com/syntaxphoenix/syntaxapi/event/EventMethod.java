package com.syntaxphoenix.syntaxapi.event;

import java.lang.reflect.Method;

import com.syntaxphoenix.syntaxapi.utils.java.Reflections;

public class EventMethod {
	
	private final EventListener listener;
	private final boolean ignoreCancel;
	private final Method method;
	
	public EventMethod(EventListener listener, Method method, boolean ignoreCancel) {
		this.listener = listener;
		this.method = method;
		this.ignoreCancel = ignoreCancel;
	}
	
	public final boolean ignoresCancel() {
		return ignoreCancel;
	}
	
	public final Method getMethod() {
		return method;
	}
	
	public final EventListener getListener() {
		return listener;
	}

	public void execute(Event event) {
		Reflections.execute(listener, method, event);
	}

}
