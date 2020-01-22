package com.syntaxphoenix.syntaxapi.addon;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import com.syntaxphoenix.syntaxapi.config.json.JsonConfig;
import com.syntaxphoenix.syntaxapi.exceptions.AddonException;
import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.utils.file.Files;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

/**
 * @author Lauriichen
 *
 */
public class AddonLoader {

	private final AddonManager manager;
	private final ClassLoader loader;
	private final SynLogger logger;

	AddonLoader(AddonManager manager, ClassLoader loader) {
		this.logger = manager.getLogger();
		this.manager = manager;
		this.loader = loader;
	}

	public AddonManager getManager() {
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
			Addon addon;
			try {
				addon = loadAddon(jarFiles.get(index));
			} catch (Throwable throwable) {
				logger.log((throwable instanceof AddonException) ? throwable : new AddonException(throwable));
				loaded--;
				continue;
			}

		}

	}

	@SuppressWarnings("resource")
	Addon loadAddon(File file) throws Throwable {

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

		JarEntry jsonEntry = entries.get("addon.json");

		String content = Streams.toString(jar.getInputStream(entry));
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
		
		BaseAddon baseAddon;
		Class<?> mainClass;
		
		try {
			mainClass = loader.loadClass(mainPath);
			if (mainClass.isAssignableFrom(BaseAddon.class)) {
				
			} else {
				throw new AddonException(file.getName() + " -> main class does not implement BaseAddon!");
			}
		} catch (Throwable throwable) {
			config.clear();
			jar.close();
			input.close();
			if (throwable instanceof AddonException) {
				throw throwable;
			} else {
				throw new AddonException(file.getName() + " -> failed to load main class", throwable);
			}
		}

		Set<String> keySet = entries.keySet();
		for (String key : keySet) {
			if (!key.endsWith(".class")) {
				continue;
			}
			key = key.substring(0, key.length() - 6);
			try {
				loader.loadClass(key);
			} catch (ClassNotFoundException throwable) {
				config.clear();
				jar.close();
				input.close();
				keySet.clear();
				throw new AddonException(file.getName() + " -> failed to find class \"" + key + "\"", throwable);
			}
		}
		
		jar.close();
		input.close();

	}

}
