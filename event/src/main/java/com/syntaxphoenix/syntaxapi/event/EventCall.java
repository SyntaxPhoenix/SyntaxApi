package com.syntaxphoenix.syntaxapi.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.syntaxphoenix.syntaxapi.utils.general.Status;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;

public final class EventCall {

	private final List<EventExecutor> executors;
	private final EventManager manager;
	private final Event event;

	public EventCall(EventManager manager, Event event, List<EventExecutor> executors) {
		this.executors = executors;
		this.manager = manager;
		this.event = event;

		Collections.sort(executors);
	}

	public final EventManager getManager() {
		return manager;
	}

	public final Event getEvent() {
		return event;
	}

	public final List<EventExecutor> getExecutors() {
		return executors;
	}

	public Status execute() {
		int count = 0;
		if (executors.isEmpty()) {
			return new Status(count);
		}
		LinkedHashMap<EventPriority, ArrayList<EventMethod>> listeners = new LinkedHashMap<>();
		for (EventPriority priority : EventPriority.values()) {
			ArrayList<EventMethod> methods = new ArrayList<>();
			for (EventExecutor executor : executors) {
				methods.addAll(executor.getMethodsByPriority(priority));
			}
			count += methods.size();
			listeners.put(priority, methods);
		}
		Status result = new Status(count);
		return event instanceof Cancelable ? callCancelable(result, listeners) : call(result, listeners);
	}

	public Status executeAsync(ExecutorService service) {
		int count = 0;
		if (executors.isEmpty()) {
			return new Status(count);
		}
		LinkedHashMap<EventPriority, ArrayList<EventMethod>> listeners = new LinkedHashMap<>();
		for (EventPriority priority : EventPriority.values()) {
			ArrayList<EventMethod> methods = new ArrayList<>();
			for (EventExecutor executor : executors) {
				methods.addAll(executor.getMethodsByPriority(priority));
			}
			count += methods.size();
			listeners.put(priority, methods);
		}

		Status result = new Status(count);
		service.submit(() -> {
			if (event instanceof Cancelable)
				callCancelable(result, listeners);
			else
				call(result, listeners);
		});
		return result;
	}

	private Status callCancelable(Status result, LinkedHashMap<EventPriority, ArrayList<EventMethod>> listeners) {
		Cancelable cancel = (Cancelable) event;
		for (EventPriority priority : EventPriority.ORDERED_VALUES) {
			ArrayList<EventMethod> methods = listeners.get(priority);
			if (methods.isEmpty()) {
				continue;
			}
			for (EventMethod method : methods) {
				if (cancel.isCancelled() && !method.ignoresCancel()) {
					result.cancel();
					continue;
				}
				try {
					method.execute(event);
					result.success();
				} catch (Throwable throwable) {
					result.failed();
					if (manager.hasLogger()) {
						manager.getLogger().log(throwable);
					} else {
						System.out.println(Exceptions.stackTraceToString(throwable));
					}
				}
			}
		}
		result.done();
		return result;
	}

	private Status call(Status result, LinkedHashMap<EventPriority, ArrayList<EventMethod>> listeners) {
		for (EventPriority priority : EventPriority.ORDERED_VALUES) {
			ArrayList<EventMethod> methods = listeners.get(priority);
			if (methods.isEmpty()) {
				continue;
			}
			for (EventMethod method : methods) {
				try {
					method.execute(event);
					result.success();
				} catch (Throwable throwable) {
					result.failed();
					if (manager.hasLogger()) {
						manager.getLogger().log(throwable);
					} else {
						System.out.println(Exceptions.stackTraceToString(throwable));
					}
				}
			}
		}
		result.done();
		return result;
	}

}
