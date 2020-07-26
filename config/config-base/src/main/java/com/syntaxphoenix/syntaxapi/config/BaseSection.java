package com.syntaxphoenix.syntaxapi.config;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author Lauriichen
 *
 */
public abstract class BaseSection implements IBaseSection {

	protected final SectionMap<String, Object> values = new SectionMap<>();;
	private final String name;
	
	public BaseSection(String name) {
		this.name = name;
	}

	public Map<String, Object> getValues() {
		return values;
	}
	
	public Set<String> getKeys() {
		return values.keySet();
	}

	public String getName() {
		return name;
	}
	
	public boolean isValid() {
		return !name.isEmpty();
	}
	
	public void clear() {
		values.clear();
	}
	
	public boolean contains(String path) {
		return get(path) != null;
	}
	
	public <E> boolean isInstance(String path, E value) {
		Object object = get(path);
		return object == null ? false : (value == null ? false : object.getClass().isAssignableFrom(value.getClass()));
	}
	
	public <E> boolean isInstance(String path, Class<E> value) {
		Object object = get(path);
		return object == null ? false : (value == null ? false : object.getClass().isAssignableFrom(value));
	}
	
	@SuppressWarnings("unchecked")
	public <E> E check(String path, E value) {
		Object current = get(path);
		if (current != null) {
			return (E) current;
		}
		set(path, value);
		return value;
	}
	
	public Object get(String path) {
		if (!path.isEmpty()) {
			return get(ConfigTools.getKeys(path));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <E> E get(String path, Class<E> sample) {
		if (!path.isEmpty()) {
			return (E) get(ConfigTools.getKeys(path));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <E> E get(String path, E sample) {
		if (!path.isEmpty()) {
			return (E) get(ConfigTools.getKeys(path));
		}
		return null;
	}

	private Object get(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					BaseSection section;
					if ((section = getSection(key[0])) != null) {
						return section.get(ConfigTools.getNextKeys(key));
					}
				} else {
					return values.get(key[0]);
				}
			}
		}
		return null;
	}
	
	public BaseSection getSection(String path) {
		if (!path.isEmpty()) {
			return getSection(ConfigTools.getKeys(path));
		}
		return null;
	}
	
	private BaseSection getSection(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					BaseSection section;
					if ((section = getSection(key[0])) != null) {
						return section.getSection(ConfigTools.getNextKeys(key));
					}
				} else {
					Object uncasted;
					if ((uncasted = values.get(key[0])) instanceof BaseSection) {
						return (BaseSection) uncasted;
					}
				}
			}
		}
		return null;
	}
	
	public BaseSection createSection(String path) {
		if (!path.isEmpty()) {
			return createSection(ConfigTools.getKeys(path));
		}
		return null;
	}
	
	private BaseSection createSection(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					BaseSection section;
					if ((section = getSection(key[0])) == null) {
						section = saveSection(initSection(key[0]));
					}
					return section.createSection(ConfigTools.getNextKeys(key));
				} else {
					Object uncasted;
					if (!((uncasted = get(key[0])) instanceof BaseSection)) {
						uncasted = saveSection(initSection(key[0]));
					}
					return (BaseSection) uncasted;
				}
			} else {
				BaseSection section = saveSection(initSection(key[0]));
				if (key.length > 1) {
					return section.createSection(ConfigTools.getNextKeys(key));
				} else {
					return section;
				}
			}
		}
		return null;
	}
	
	protected BaseSection saveSection(BaseSection section) {
		set(section.getName(), section);
		return section;
	}

	public void set(String path, Object value) {
		String[] keys = ConfigTools.getKeys(path);
		String key = ConfigTools.getLastKey(keys);
		set(key, ConfigTools.getKeysWithout(keys, key), value);
	}

	public void set(String key, String[] path, Object value) {
		if (path.length == 0) {
			values.put(key, value);
		} else {
			BaseSection section = getSection(path);
			if (section == null) {
				section = createSection(path);
			}
			section.set(key, value);
		}
	}
	
	public SectionMap<String, Object> toMap() {
		SectionMap<String, Object> output = new SectionMap<>();
		if(values.isEmpty()) {
			return output;
		}
		Set<Entry<String, Object>> set = values.entrySet();
		for(Entry<String, Object> entry : set) {
			Object out = entry.getValue();
			if(out instanceof BaseSection) {
				if(isSectionInstance((BaseSection) out)) {
					out = ((BaseSection) out).toMap();
				}
			}
			output.put(entry.getKey(), out);
		}
		return output;
	}
	
	@SuppressWarnings("unchecked")
	public void fromMap(SectionMap<String, Object> input) {
		clear();
		Set<Entry<String, Object>> set = input.entrySet();
		if(set.isEmpty()) {
			return;
		}
		for(Entry<String, Object> entry : set) {
			Object obj = entry.getValue();
			if(obj instanceof SectionMap) {
				createSection(entry.getKey()).fromMap((SectionMap<String, Object>) obj);
			} else {
				set(entry.getKey(), obj);
			}
		}
	}

	protected abstract BaseSection initSection(String name);
	
	protected abstract boolean isSectionInstance(BaseSection section);
	
}
