package com.syntaxphoenix.syntaxapi.config.toml;

import java.io.IOException;
import java.util.Set;
import java.util.HashMap;
import java.util.Map.Entry;

import com.electronwill.toml.Toml;
import com.syntaxphoenix.syntaxapi.config.BaseSection;

public class TomlConfigSection extends BaseSection {

	public TomlConfigSection() {
		super("");
	}

	public TomlConfigSection(String name) {
		super(name);
	}

	@Override
	protected BaseSection initSection(String name) {
		return new TomlConfigSection(name);
	}
	
	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseSection#isSectionInstance(com.syntaxphoenix.syntaxapi.config.BaseSection)
	 */
	@Override
	protected boolean isSectionInstance(BaseSection section) {
		return section instanceof TomlConfigSection;
	}
	
	/*
	 * 
	 * TO TOML
	 * 
	 */
	
	public String toTomlString() {
		try {
			return Toml.writeToString(toMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * 
	 * FROM TOML
	 * 
	 */
	
	public void fromTomlString(String toml) {
		fromMap((HashMap<String, Object>) Toml.read(toml));
	}
	
	@SuppressWarnings("unchecked")
	public void fromMap(HashMap<String, Object> input) {
		clear();
		Set<Entry<String, Object>> set = input.entrySet();
		if(set.isEmpty()) {
			return;
		}
		for(Entry<String, Object> entry : set) {
			Object obj = entry.getValue();
			if(obj instanceof HashMap) {
				((TomlConfigSection) createSection(entry.getKey())).fromMap((HashMap<String, Object>) obj);
			} else {
				set(entry.getKey(), obj);
			}
		}
	}

}
