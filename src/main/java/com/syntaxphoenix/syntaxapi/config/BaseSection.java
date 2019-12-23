package com.syntaxphoenix.syntaxapi.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.utils.config.ConfigSerializer;

/**
 * @author Lauriichen
 *
 */
public abstract class BaseSection {

	protected final HashMap<String, Object> values = new HashMap<>();
	private final String name;

	/**
	 * @param String {name}
	 */
	public BaseSection(String name) {
		this.name = name;
	}

	/**
	 * @return HashMap<String, Object> {section values}
	 */
	public Map<String, Object> getValues() {
		return values;
	}
	
	/**
	 * @return Set<String> {section keys}
	 */
	public Set<String> getKeys() {
		return values.keySet();
	}

	/**
	 * @return String {section name}
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return Boolean {name exists}
	 */
	public boolean isValid() {
		return !name.isEmpty();
	}
	
	/**
	 * 
	 */
	public void clear() {
		values.clear();
	}
	
	/**
	 * @param String {path}
	 * @return Boolean {value exists}
	 */
	public boolean contains(String path) {
		return get(path) != null;
	}
	
	/**
	 * @param <E>
	 * @param String {path}
	 * @param Object {object instance}
	 * @return Boolean {value is instance}
	 */
	public <E> boolean isInstance(String path, E value) {
		Object object = get(path);
		return object == null ? false : (value == null ? false : object.getClass().isAssignableFrom(value.getClass()));
	}
	
	/**
	 * @param <E>
	 * @param String {path}
	 * @param Class {object instance}
	 * @return Boolean {value is instance}
	 */
	public <E> boolean isInstance(String path, Class<E> value) {
		Object object = get(path);
		return object == null ? false : (value == null ? false : object.getClass().isAssignableFrom(value));
	}
	
	/**
	 * @param <E>
	 * @param String {path}
	 * @param Object {default value}
	 * @return Object {section value}
	 */
	@SuppressWarnings("unchecked")
	public <E> E check(String path, E value) {
		Object current = get(path);
		if (current != null) {
			return (E) current;
		}
		set(path, value);
		return value;
	}

	/**
	 * @param String {path}
	 * @return Object (Null) {section value}
	 */
	
	public Object get(String path) {
		if (!path.isEmpty()) {
			return get(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param <E>
	 * @param String {path}
	 * @param Class<E> {sample value type}
	 * @return Object (Null) {section value}
	 */
	@SuppressWarnings("unchecked")
	public <E> E get(String path, Class<E> sample) {
		if (!path.isEmpty()) {
			return (E) get(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param <E>
	 * @param String {path}
	 * @param E {sample value type}
	 * @return Object (Null) {section value}
	 */
	@SuppressWarnings("unchecked")
	public <E> E get(String path, E sample) {
		if (!path.isEmpty()) {
			return (E) get(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param String[] {path}
	 * @return Object (Null) {section value}
	 */
	
	private Object get(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					BaseSection section;
					if ((section = getSection(key[0])) != null) {
						return section.get(ConfigSerializer.getNextKeys(key));
					}
				} else {
					return values.get(key[0]);
				}
			}
		}
		return null;
	}

	/**
	 * @param String {path}
	 * @return JsonSection (Null) {section value}
	 */
	
	public BaseSection getSection(String path) {
		if (!path.isEmpty()) {
			return getSection(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param String[] {path}
	 * @return JsonSection (Null) {section value}
	 */
	
	private BaseSection getSection(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					BaseSection section;
					if ((section = getSection(key[0])) != null) {
						return section.getSection(ConfigSerializer.getNextKeys(key));
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

	/**
	 * @param String {path}
	 * @return JsonSection (Null) {old / new section}
	 */
	
	public BaseSection createSection(String path) {
		if (!path.isEmpty()) {
			return createSection(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param String[] {path}
	 * @return JsonSection (Null) {old / new section}
	 */
	
	private BaseSection createSection(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					BaseSection section;
					if ((section = getSection(key[0])) == null) {
						section = saveSection(initSection(key[0]));
					}
					return section.createSection(ConfigSerializer.getNextKeys(key));
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
					return section.createSection(ConfigSerializer.getNextKeys(key));
				} else {
					return section;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param BaseSection {saveable section}
	 */
	private BaseSection saveSection(BaseSection section) {
		set(section.getName(), section);
		return section;
	}

	/**
	 * @param String {path}
	 * @param Object {value}
	 */
	public void set(String path, Object value) {
		String[] keys = ConfigSerializer.getKeys(path);
		String key = ConfigSerializer.getLastKey(keys);
		set(key, ConfigSerializer.getKeysWithout(keys, key), value);
	}

	/**
	 * @param String[] {path}
	 * @param Object {value}
	 */
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

	/**
	 * @param String {path}
	 * @return BaseSection {new section}
	 */
	protected abstract BaseSection initSection(String name);
	
}
