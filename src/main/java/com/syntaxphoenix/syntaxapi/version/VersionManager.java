package com.syntaxphoenix.syntaxapi.version;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class VersionManager<V extends Version> {

	private final EnumMap<VersionState, List<V>> versions = new EnumMap<>(VersionState.class);

	public VersionManager() {
		versions.put(VersionState.SUPPORTED, new ArrayList<>());
		versions.put(VersionState.NOT_SUPPORTED, new ArrayList<>());
		versions.put(VersionState.NOT_TESTED, new ArrayList<>());
		versions.put(VersionState.NOT_COMPATIBLE, new ArrayList<>());
	}

	/*
	 * 
	 */

	public VersionManager<V> setAll(VersionState state, V... versions) {
		for (V version : versions) {
			set(state, version);
		}
		return this;
	}

	public VersionManager<V> set(VersionState state, V version) {
		if (state == null) {
			VersionState remove = getState(version);
			if (remove != null) {
				versions.get(remove).remove(version);
			}
		} else {
			VersionState remove = getState(version);
			if (remove != state) {
				if (remove != null) {
					versions.get(remove).remove(version);
				}
				versions.get(state).add(version);
			}
		}
		return this;
	}

	public VersionManager<V> addAll(V... versions) {
		return setAll(VersionState.NOT_SUPPORTED, versions);
	}

	public VersionManager<V> add(V version) {
		return set(VersionState.NOT_SUPPORTED, version);
	}

	public VersionManager<V> removeAll(V... versions) {
		return setAll(null, versions);
	}

	public VersionManager<V> remove(V version) {
		return set(null, version);
	}

	/*
	 * 
	 */

	public boolean contains(V version) {
		return getState(version) != null;
	}

	public boolean contains(VersionState state, V version) {
		return versions.get(state).contains(version);
	}

	public VersionState getState(V version) {
		for (VersionState state : VersionState.values()) {
			if (contains(state, version)) {
				return state;
			}
		}
		return null;
	}

}
