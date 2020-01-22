package com.syntaxphoenix.syntaxapi.addon;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Addon {
	
	private final Map<String, Class<?>> classes = Collections.synchronizedMap(new HashMap<String, Class<?>>());
	private final Class<? extends BaseAddon> mainClass;
	private final BaseAddon addon;
	
	public Addon(Class<? extends BaseAddon> mainClass, BaseAddon addon) {
		this.mainClass = mainClass;
		this.addon = addon;
	}
	
	public final BaseAddon getAddon() {
		return addon;
	}

	public final Class<? extends BaseAddon> getMainClass() {
		return mainClass;
	}
	
	/*
	 * 
	 */
	
	Map<String, Class<?>> classes() {
		return classes;
	}

}
