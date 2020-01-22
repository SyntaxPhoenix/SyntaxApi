package com.syntaxphoenix.syntaxapi.utils.alias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AliasMap<A> extends HashMap<Alias, A> {

	private static final long serialVersionUID = 8244987875156828827L;
	private Set<Entry<Alias, A>> entries;
	
	@Override
	public A put(Alias key, A value) {
		A out = super.put(key, value);
		refresh();
		return out;
	}
	
	@Override
	public void putAll(Map<? extends Alias, ? extends A> m) {
		super.putAll(m);
		refresh();
	}
	
	@Override
	public A putIfAbsent(Alias key, A value) {
		A out = super.putIfAbsent(key, value);
		entries = entrySet();
		return out;
	}
	
	@Override
	public A remove(Object key) {
		A out = super.remove(key);
		refresh();
		return out;
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		boolean out = super.remove(key, value);
		refresh();
		return out;
	}
	
	public boolean containsKey(String key) {
		return containsKey((Object) key);
	}

	@Override
	public boolean containsKey(Object key) {
		if(entries == null || entries.isEmpty()) {
			return false;
		}
		if (key instanceof String) {
			return getEntryFromString((String) key) != null;
		} else if (key instanceof Alias) {
			return super.containsKey(key);
		} else {
			return false;
		}
	}
	
	public A get(String key) {
		return get((Object) key);
	}
	
	@Override
	public A get(Object key) {
		if(entries == null || entries.isEmpty()) {
			return null;
		}
		if (key instanceof String) {
			return getEntryFromString((String) key).getValue();
		} else if (key instanceof Alias) {
			return super.get(key);
		} else {
			return null;
		}
	}
	
	private Entry<Alias, A> getEntryFromString(String key) {
		for(Entry<Alias, A> entry : entries) {
			if(entry.getKey().isLabel(key)) {
				return entry;
			}
		}
		return null;
	}
	
	private void refresh() {
		entries.clear();
		entries = null;
		entries = entrySet();
	}

	public ArrayList<String> hasConflict(Alias alias) {
		ArrayList<String> conflict = new ArrayList<>();
		if(containsKey(alias.getName())) {
			conflict.add(alias.getName());
		}
		if(alias.hasAliases()) {
			for(String current : alias.getAliases()) {
				if(containsKey(current)) {
					conflict.add(current);
				}
			}
		}
		return conflict;
	}

}
