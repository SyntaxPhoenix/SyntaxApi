package com.syntaxphoenix.syntaxapi.utils.priority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class PrioritisedList<P extends Priority<?>, V> {

	private final HashMap<P, ArrayList<V>> priorityMap = new HashMap<>();
	private final P priority;
	
	public PrioritisedList(P priority, HashMap<P, ArrayList<V>> priorityMap) {
		this.priorityMap.putAll(priorityMap);
		this.priority = priority;
	}
	
	public PrioritisedList(P priority) {
		this.priority = priority;
	}

	public void add(P priority, V value) {
		ArrayList<V> list = priorityMap.containsKey(priority) ? priorityMap.get(priority) : new ArrayList<>();
		list.add(value);
		priorityMap.put(priority, list);
	}

	public void remove(P priority, V value) {
		ArrayList<V> list = priorityMap.containsKey(priority) ? priorityMap.get(priority) : new ArrayList<>();
		list.remove(value);
		priorityMap.put(priority, list);
	}

	public ArrayList<V> getValuesOfPriority(P priority) {
		return (priorityMap.containsKey(priority) ? priorityMap.get(priority) : new ArrayList<>());
	}

	public boolean contains(P priority, V value) {
		return (priorityMap.containsKey(priority) ? priorityMap.get(priority).contains(value) : false);
	}

	public P getPriority(V value) {
		if (!priorityMap.isEmpty()) {
			Set<Entry<P, ArrayList<V>>> entries = priorityMap.entrySet();
			for (Entry<P, ArrayList<V>> entry : entries) {
				ArrayList<V> list = entry.getValue();
				if (list.isEmpty()) {
					continue;
				}
				for (V current : list) {
					if (value.equals(current)) {
						return entry.getKey();
					}
				}
			}
		}
		return priority;
	}

	public PrioritisedList<P, V> copy() {
		return new PrioritisedList<>(priority, priorityMap);
	}

}
