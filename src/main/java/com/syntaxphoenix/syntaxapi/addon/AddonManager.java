package com.syntaxphoenix.syntaxapi.addon;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.syntaxphoenix.syntaxapi.config.json.JsonConfig;
import com.syntaxphoenix.syntaxapi.exceptions.AddonException;
import com.syntaxphoenix.syntaxapi.logging.LogType;
import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.utils.alias.Alias;
import com.syntaxphoenix.syntaxapi.utils.alias.AliasMap;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

/**
 * @author Lauriichen
 *
 */
public class AddonManager {

	private final AddonLoader loader = new AddonLoader(this, this.getClass().getClassLoader());
	private final ArrayList<File> directories = new ArrayList<>();
	private final AliasMap<Addon> addons = new AliasMap<>();
	private final SynLogger logger;

	public AddonManager(SynLogger logger) {
		this.logger = logger;
	}

	protected SynLogger getLogger() {
		return logger;
	}

	/*
	 * 
	 */

	void register(Addon addon) throws AddonException {
		WeakReference<Alias> alias = new WeakReference<>(makeAlias(addon));
		String name = alias.get().getName();
		
		Alias label;
		ArrayList<String> conflict = addons.hasConflict(alias.get());
		if(!conflict.isEmpty()) {
			if(conflict.contains(name)) {
				throw new AddonException("Addon with the name " + name + " already exists!");
			}
			label = alias.get().removeConflicts(conflict);
			logger.log(LogType.WARNING, "Following aliases could not be used for addon \"" + name + "\": " +  Strings.toString(conflict));
		} else {
			label = alias.get();
		}
		conflict.clear();
		alias.enqueue();
		
		addons.put(label, addon);
	}

	Alias makeAlias(Addon addon) {
		JsonConfig info = addon.getAddonInfo();
		String name = (info.contains("name") ? info.get("name", String.class) : addon.getAddonFile().getName().replace(".jar", ""))
				.replace(" ", "_");
		String display = info.contains("display") ? info.get("display", String.class) : name;

		StringBuilder builder = new StringBuilder();
		if (info.contains("aliases")) {
			List<?> list = info.get("aliases", List.class);
			if (!list.isEmpty()) {
				Object input;
				int size = list.size();
				for (int index = 0; index < size; index++) {
					input = list.get(index);
					if (!(input instanceof String)) {
						continue;
					}
					builder.append((String) input);
					if ((index - 1) == size) {
						builder.append(' ');
					}
				}
			}
		}
		String[] array = new String[0];
		String built = builder.toString();
		if (!built.isEmpty()) {
			if (built.contains(" ")) {
				array = built.split(" ");
			} else {
				array = new String[] { built };
			}
		}
		return new Alias(name, display, array);
	}

	/*
	 * 
	 * 
	 * 
	 */

	public int loadAddons() {
		if (!addons.isEmpty()) {
			addons.values().forEach(addon -> {
				addon.classes().clear();
			});
			addons.clear();
		}
		if (directories.isEmpty()) {
			return 0;
		}
		int addons = 0;
		for (File directory : directories) {
			addons += loader.loadAddons(directory);
		}
		return addons;
	}

	public BaseAddon getAddon(String label) {
		if (addons.isEmpty()) {
			return null;
		}
		return addons.get(label).getAddon();
	}

	public BaseAddon getAddon(Class<? extends BaseAddon> clazz) {
		if (addons.isEmpty()) {
			return null;
		}
		for (Addon addon : addons.values()) {
			if (addon.getMainClass().equals(clazz)) {
				return addon.getAddon();
			}
		}
		return null;
	}

}
