package com.syntaxphoenix.syntaxapi.addon;

import java.io.File;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.syntaxphoenix.syntaxapi.addon.AddonState;
import com.syntaxphoenix.syntaxapi.config.json.JsonConfig;
import com.syntaxphoenix.syntaxapi.reflections.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflections.Reflect;

public final class Addon<E extends BaseAddon> {

	private static final AbstractReflect ADDON = new Reflect(Addon.class).searchField("c1", "classes")
			.searchField("c2", "mainClass").searchField("c3", "addonInfo").searchField("c4", "addon")
			.searchField("c5", "addonFile").searchField("c6", "classLoader");
	private static final AbstractReflect CLASSLOADER = new Reflect(ClassLoader.class).searchField("vector", "classes");

	private final Map<String, Class<?>> classes = Collections.synchronizedMap(new HashMap<String, Class<?>>());
	private final Class<? extends E> mainClass;
	private final URLClassLoader classLoader;
	private final JsonConfig addonInfo;
	private final File addonFile;
	private final E addon;
	
	private AddonState state;

	public Addon(Class<? extends E> mainClass, URLClassLoader classLoader, E addon, JsonConfig addonInfo, File addonFile) {
		this.classLoader = classLoader;
		this.mainClass = mainClass;
		this.addonInfo = addonInfo;
		this.addonFile = addonFile;
		this.addon = addon;
		this.state = AddonState.LOADED;
		addon.setAddon(this);
	}
	
	public final URLClassLoader getClassLoader() {
		return classLoader;
	}

	public final JsonConfig getAddonInfo() {
		return addonInfo;
	}

	public final E getAddon() {
		return addon;
	}

	public final Class<? extends E> getMainClass() {
		return mainClass;
	}

	public final File getAddonFile() {
		return addonFile;
	}
	
	public AddonState getState() {
		return state;
	}

	/*
	 * 
	 */

	Map<String, Class<?>> classes() {
		return classes;
	}

	void delete() {
		state = AddonState.INVALID;
		
		classes.clear();
		addonInfo.clear();
		
		((Vector<?>) CLASSLOADER.getFieldValue("vector", classLoader)).removeAllElements();
		
		ADDON.setFieldValue(this, "c1", null);
		ADDON.setFieldValue(this, "c2", null);
		ADDON.setFieldValue(this, "c3", null);
		ADDON.setFieldValue(this, "c4", null);
		ADDON.setFieldValue(this, "c5", null);
		
	}

}
