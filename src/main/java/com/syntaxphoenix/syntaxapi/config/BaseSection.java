package com.syntaxphoenix.syntaxapi.config;

import java.util.HashMap;

import org.eclipse.jdt.annotation.Nullable;

import com.syntaxphoenix.syntaxapi.utils.config.ConfigSerializer;

/**
 * @author Lauriichen
 *
 */
public abstract class BaseSection {

	private final HashMap<String, Object> values = new HashMap<>();
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
	public HashMap<String, Object> getValues() {
		return values;
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
	 * @param String {path}
	 * @param Object {default value}
	 * @return Object {section value}
	 */
	public Object check(String path, Object value) {
		Object current = get(path);
		if(current != null) {
			return current;
		}
		set(path, value);
		return value;
	}

	/**
	 * @param String {path}
	 * @return Object (Null) {section value}
	 */
	@Nullable
	public Object get(String path) {
		if (!path.isEmpty()) {
			return get(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param String[] {path}
	 * @return Object (Null) {section value}
	 */
	@Nullable
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
	@Nullable
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
	@Nullable
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
	@Nullable
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
	@Nullable
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
