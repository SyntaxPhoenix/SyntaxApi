package com.syntaxphoenix.syntaxapi.version;

import static com.syntaxphoenix.syntaxapi.version.VersionState.NOT_COMPATIBLE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

/**
 * 
 * @author Lauriichan
 *
 */
@SuppressWarnings("unchecked")
public class VersionManager<V extends Version> {

    protected final EnumMap<VersionState, ArrayList<V>> versions = new EnumMap<>(VersionState.class);

    protected final VersionState unknown, higher, lower;

    public VersionManager() {
        this(NOT_COMPATIBLE, NOT_COMPATIBLE, NOT_COMPATIBLE);
    }

    public VersionManager(VersionState unknown, VersionState higher, VersionState lower) {
        versions.put(VersionState.SUPPORTED, new ArrayList<>());
        versions.put(VersionState.NOT_SUPPORTED, new ArrayList<>());
        versions.put(VersionState.NOT_TESTED, new ArrayList<>());
        versions.put(VersionState.NOT_COMPATIBLE, new ArrayList<>());

        this.unknown = unknown;
        this.higher = higher;
        this.lower = lower;
    }

    /*
     * 
     */

    public VersionState getDefaultStateForUnknownVersions() {
        return unknown;
    }

    public VersionState getDefaultStateForHigherVersions() {
        return higher;
    }

    public VersionState getDefaultStateForLowerVersions() {
        return lower;
    }

    /*
     * 
     */

    public VersionManager<V> sortAll() {
        for (VersionState state : VersionState.values()) {
            sort(state);
        }
        return this;
    }

    public VersionManager<V> sort(VersionState state) {
        ArrayList<V> versions = this.versions.get(state);
        if (versions.isEmpty()) {
            return this;
        }
        Collections.sort(versions);
        return this;
    }

    /*
     * 
     */

    public VersionManager<V> clearAll() {
        for (VersionState state : VersionState.values()) {
            clear(state);
        }
        return this;
    }

    public VersionManager<V> clear(VersionState state) {
        ArrayList<V> versions = this.versions.get(state);
        if (versions.isEmpty()) {
            return this;
        }
        versions.clear();
        return this;
    }

    /*
     * 
     */

    public VersionManager<V> setAll(VersionState state, V... versions) {
        for (V version : versions) {
            set(state, version, false);
        }
        sort(state);
        return this;
    }

    public VersionManager<V> set(VersionState state, V version) {
        return set(state, version, true);
    }

    public VersionManager<V> set(VersionState state, V version, boolean sort) {
        if (state == null) {
            VersionState remove = getState(version);
            if (remove != null) {
                versions.get(remove).remove(version);
            }
        } else {
            VersionState remove = getActualState(version);
            if (remove != state) {
                if (remove != null) {
                    versions.get(remove).remove(version);
                }
                versions.get(state).add(version);
            }
        }
        if (sort) {
            sort(state);
        }
        return this;
    }

    /*
     * 
     */

    public VersionManager<V> addAll(V... versions) {
        return setAll(unknown, versions);
    }

    public VersionManager<V> add(V version) {
        return set(unknown, version);
    }

    /*
     * 
     */

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
        return getActualState(version) != null;
    }

    public boolean contains(VersionState state, V version) {
        return versions.get(state).contains(version);
    }

    /*
     * 
     */

    public V getHighestVersion(VersionState state) {
        ArrayList<V> versions = this.versions.get(state);
        if (versions.isEmpty()) {
            return null;
        }
        return versions.get(versions.size() - 1);
    }

    public V getHighestVersionOf(VersionState... states) {
        if (states == null || states.length == 0) {
            return null;
        }
        if (states.length == 1) {
            return getLowestVersion(states[0]);
        }
        V highest = null;
        for (int index = 0; index < states.length; index++) {
            V current = getHighestVersion(states[index]);
            if (highest != null && !current.isHigher(highest)) {
                continue;
            }
            highest = current;
        }
        return highest;
    }

    public V getLowestVersion(VersionState state) {
        ArrayList<V> versions = this.versions.get(state);
        if (versions.isEmpty()) {
            return null;
        }
        return versions.get(0);
    }

    public V getLowestVersionOf(VersionState... states) {
        if (states == null || states.length == 0) {
            return null;
        }
        if (states.length == 1) {
            return getLowestVersion(states[0]);
        }
        V lowest = null;
        for (int index = 0; index < states.length; index++) {
            V current = getLowestVersion(states[index]);
            if (lowest != null && !current.isLower(lowest)) {
                continue;
            }
            lowest = current;
        }
        return lowest;
    }

    /*
     * 
     */

    public ArrayList<V> getVersions(VersionState state) {
        return (ArrayList<V>) this.versions.get(state).clone();
    }

    /*
     * 
     */

    public VersionState getActualState(V version) {
        for (VersionState state : VersionState.values()) {
            if (contains(state, version)) {
                return state;
            }
        }
        return null;
    }

    public VersionState getState(V version) {
        VersionState[] states = VersionState.values();
        for (int index = 0; index < states.length; index++) {
            if (contains(states[index], version)) {
                return states[index];
            }
        }
        V lowest = getLowestVersionOf(VersionState.SUPPORTED, VersionState.NOT_TESTED);
        if (lowest != null && version.isLower(lowest)) {
            return lower;
        }
        V highest = getHighestVersionOf(VersionState.SUPPORTED, VersionState.NOT_TESTED);
        if (highest != null && version.isHigher(highest)) {
            return higher;
        }
        return unknown;
    }

}
