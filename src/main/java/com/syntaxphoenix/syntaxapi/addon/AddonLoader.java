package com.syntaxphoenix.syntaxapi.addon;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import com.syntaxphoenix.syntaxapi.config.json.JsonConfig;
import com.syntaxphoenix.syntaxapi.exceptions.AddonException;
import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.utils.java.Files;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

/**
 * @author Lauriichen
 *
 */
public class AddonLoader<E extends BaseAddon> {
	
	private final AddonManager<E> manager;
	private final ClassLoader loader;
	private final SynLogger logger;

	AddonLoader(AddonManager<E> manager, ClassLoader loader) {
		this.logger = manager.getLogger();
		this.manager = manager;
		this.loader = loader;
	}

	public AddonManager<E> getManager() {
		return manager;
	}

	public ClassLoader getLoader() {
		return loader;
	}

	/*
	 * 
	 */

	int loadAddons(File directory) {
		List<File> jarFiles = Files.listFiles(directory, ".jar");
		int loaded = jarFiles.size();
		if (loaded == 0) {
			return loaded;
		}
		int size = loaded;
		for (int index = 0; index < size; index++) {
			Addon<E> addon;
			try {
				addon = loadAddon(jarFiles.get(index));
			} catch (Throwable throwable) {
				logger.log((throwable instanceof AddonException) ? throwable : new AddonException(throwable));
				loaded--;
				continue;
			}
			try {
				manager.register(addon);
			} catch (AddonException e) {
				addon.delete();
				logger.log(e);
			}
		}
		return loaded;
	}
	
	Addon<E> loadAddon(File file) throws Throwable {

		JarInputStream input = new JarInputStream(new FileInputStream(file));
		JarFile jar = new JarFile(file);
		JarEntry entry;

		HashMap<String, JarEntry> entries = new HashMap<>();
		while ((entry = input.getNextJarEntry()) != null) {
			entries.put(entry.getName().replace('/', '.'), entry);
		}

		if (!entries.containsKey("addon.json")) {
			jar.close();
			input.close();
			throw new AddonException(file.getName() + " does not contain a addon.json");
		}

		String content = Streams.toString(jar.getInputStream(entries.get("addon.json")));
		JsonConfig config = new JsonConfig();
		config.fromJsonString(content);

		String mainPath = config.get("main", String.class);
		if (mainPath == null) {
			config.clear();
			jar.close();
			input.close();
			throw new AddonException(file.getName() + " contains an invalid addon.json (main class path is missing)");
		}
		if (!entries.containsKey(mainPath + ".class")) {
			config.clear();
			jar.close();
			input.close();
			throw new AddonException(file.getName() + " -> main class (\"" + mainPath + "\") not found");
		}

		E baseAddon;
		Class<? extends E> mainClass;

		try {
			Class<?> rawClass = loader.loadClass(mainPath);
			if (rawClass.isAssignableFrom(manager.getAddonClass())) {
				mainClass = rawClass.asSubclass(manager.getAddonClass());
				try {
					baseAddon = mainClass.newInstance();
				} catch (Throwable throwable) {
					config.clear();
					jar.close();
					input.close();
					throw new AddonException(file.getName() + " -> cannot create an instanceof main class!", throwable);
				}
			} else {
				config.clear();
				jar.close();
				input.close();
				throw new AddonException(file.getName() + " -> main class does not implement BaseAddon!");
			}
		} catch (Throwable throwable) {
			if (throwable instanceof AddonException) {
				throw throwable;
			} else {
				config.clear();
				jar.close();
				input.close();
				throw new AddonException(file.getName() + " -> failed to load main class", throwable);
			}
		}

		Addon<E> addon = new Addon<>(mainClass, baseAddon, config, file);
		Map<String, Class<?>> classes = addon.classes();
		
		Set<String> keySet = entries.keySet();
		for (String key : keySet) {
			if (!key.endsWith(".class")) {
				continue;
			}
			key = key.substring(0, key.length() - 6);
			try {
				classes.put(key, loader.loadClass(key));
			} catch (ClassNotFoundException throwable) {
				classes.clear();
				config.clear();
				jar.close();
				input.close();
				keySet.clear();
				addon.delete();
				throw new AddonException(file.getName() + " -> failed to find class \"" + key + "\"", throwable);
			}
		}
		keySet.clear();
		entries.clear();

		try {
			jar.close();
			input.close();
		} catch (Throwable throwable) {
			logger.log(throwable);
		}

		return addon;

	}

}
