package com.syntaxphoenix.syntaxapi.config.nbt;

import java.util.Map.Entry;

import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtParser;
import com.syntaxphoenix.syntaxapi.nbt.NbtByte;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.utils.config.ConfigSerializer;

public class NbtConfigSection extends BaseSection {

	/**
	 * 
	 */
	public NbtConfigSection() {
		super("");
	}

	/**
	 * 
	 */
	public NbtConfigSection(String name) {
		super(name);
	}

	/**
	 * @return NBTCompound {section values}
	 */
	public NbtCompound asCompound() {
		NbtCompound data = new NbtCompound();
		if (!values.isEmpty()) {
			for (Entry<String, Object> entry : values.entrySet()) {
				Object raw = entry.getValue();
				if (raw instanceof NbtConfigSection) {
					data.set(entry.getKey(), ((NbtConfigSection) raw).asCompound());
				} else if (raw instanceof NbtTag) {
					data.set(entry.getKey(), (NbtTag) raw);
				} else {
					NbtTag tag = NbtParser.fromObject(raw);
					if(tag != null) {
						data.set(entry.getKey(), tag);
					}
				}
			}
		}
		data.set("section", new NbtByte((byte) 0));
		return data;
	}

	/**
	 * @param NbtCompound {data values}
	 */
	public void fromCompound(NbtCompound data) {
		values.clear();
		for (String key : data.getKeys()) {
			NbtTag base = data.getTag(key);
			if (base.getType() == NbtType.COMPOUND) {
				NbtCompound compound = (NbtCompound) base;
				if (compound.hasKey("section")) {
					NbtTag section;
					if ((section = compound.getTag("section")).getType() == NbtType.BYTE) {
						if (((NbtByte) section).getByteValue() == (byte) 0) {
							saveSection(initSection(key, compound));
							continue;
						}
					}
				}
				set(key, compound);
			} else {
				if (key.equals("section")) {
					if (base.getType() == NbtType.BYTE) {
						if (((NbtByte) base).getByteValue() == (byte) 0) {
							continue;
						}
					}
				}
				set(key, base);
			}
		}
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
	 * @param NbtTag {default value}
	 * @return NBTTag {section value}
	 */
	public NbtTag check(String path, NbtTag value) {
		NbtTag current = get(path);
		if (current != null) {
			return current;
		}
		set(path, value);
		return value;
	}

	/**
	 * @param String {path}
	 * @return NBTTag (Null) {section value}
	 */

	public NbtTag get(String path) {
		if (!path.isEmpty()) {
			return get(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param String[] {path}
	 * @return NBTTag (Null) {section value}
	 */

	private NbtTag get(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					NbtConfigSection section;
					if ((section = getSection(key[0])) != null) {
						return section.get(ConfigSerializer.getNextKeys(key));
					}
				} else {
					return (NbtTag) values.get(key[0]);
				}
			}
		}
		return null;
	}

	/**
	 * @param String {path}
	 * @return JsonSection (Null) {section value}
	 */

	public NbtConfigSection getSection(String path) {
		if (!path.isEmpty()) {
			return getSection(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param String[] {path}
	 * @return JsonSection (Null) {section value}
	 */

	private NbtConfigSection getSection(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					NbtConfigSection section;
					if ((section = getSection(key[0])) != null) {
						return section.getSection(ConfigSerializer.getNextKeys(key));
					}
				} else {
					Object uncasted;
					if ((uncasted = values.get(key[0])) instanceof NbtConfigSection) {
						return (NbtConfigSection) uncasted;
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

	public NbtConfigSection createSection(String path) {
		if (!path.isEmpty()) {
			return createSection(ConfigSerializer.getKeys(path));
		}
		return null;
	}

	/**
	 * @param String[] {path}
	 * @return JsonSection (Null) {old / new section}
	 */

	private NbtConfigSection createSection(String[] key) {
		if (key.length != 0) {
			if (values.containsKey(key[0])) {
				if (key.length > 1) {
					NbtConfigSection section;
					if ((section = getSection(key[0])) == null) {
						section = saveSection(initSection(key[0]));
					}
					return section.createSection(ConfigSerializer.getNextKeys(key));
				} else {
					Object uncasted;
					if (!((uncasted = get(key[0])) instanceof NbtConfigSection)) {
						uncasted = saveSection(initSection(key[0]));
					}
					return (NbtConfigSection) uncasted;
				}
			} else {
				NbtConfigSection section = saveSection(initSection(key[0]));
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
	 * @param String {path}
	 * @param NbtTag {value}
	 */
	public void set(String path, NbtTag value) {
		String[] keys = ConfigSerializer.getKeys(path);
		String key = ConfigSerializer.getLastKey(keys);
		set(key, ConfigSerializer.getKeysWithout(keys, key), value);
	}

	/**
	 * @param String[] {path}
	 * @param NbtTag   {value}
	 */
	public void set(String key, String[] path, NbtTag value) {
		if (path.length == 0) {
			values.put(key, value);
		} else {
			NbtConfigSection section = getSection(path);
			if (section == null) {
				section = createSection(path);
			}
			section.set(key, value);
		}
	}

	/**
	 * @param String {path}
	 * @return NBTConfigSection {new section}
	 */
	protected NbtConfigSection initSection(String name) {
		return new NbtConfigSection(name);
	}

	/**
	 * @param String {path}
	 * @return NBTConfigSection {new section}
	 */
	protected NbtConfigSection initSection(String name, NbtCompound data) {
		NbtConfigSection section = new NbtConfigSection(name);
		section.fromCompound(data);
		return section;
	}

	/**
	 * @param NbtConfigSection {saveable section}
	 */
	private NbtConfigSection saveSection(NbtConfigSection section) {
		set(section.getName(), section.asCompound());
		return section;
	}
	
	/**
	 * @return String {Compound as String}
	 */
	@Override
	public String toString() {
		return asCompound().toMSONString();
	}

}