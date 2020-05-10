package com.syntaxphoenix.syntaxapi.addon;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.syntaxphoenix.syntaxapi.config.json.JsonConfig;
import com.syntaxphoenix.syntaxapi.exceptions.AddonException;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.utils.alias.Alias;
import com.syntaxphoenix.syntaxapi.utils.alias.AliasMap;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

/**
 * @author Lauriichen
 *
 */
public abstract class AddonManager<E extends BaseAddon> {

	protected final AddonLoader<E> loader;
	protected final ArrayList<File> directories = new ArrayList<>();
	protected final AliasMap<Addon<E>> addons = new AliasMap<>();
	private final Class<E> addonClass;
	private final ILogger logger;

	public AddonManager(Class<E> addonClass) {
		this(addonClass, new SynLogger());
	}

	public AddonManager(Class<E> addonClass, ILogger logger) {
		this.loader = new AddonLoader<>(this, this.getClass().getClassLoader(), logger);
		this.addonClass = addonClass;
		this.logger = logger;
	}

	protected ILogger getLogger() {
		return logger;
	}

	public Class<E> getAddonClass() {
		return addonClass;
	}

	/*
	 * 
	 */

	void register(Addon<E> addon) throws AddonException {
		WeakReference<Alias> alias = new WeakReference<>(makeAlias(addon));
		String name = alias.get().getName();

		Alias label;
		ArrayList<String> conflict = addons.hasConflict(alias.get());
		if (!conflict.isEmpty()) {
			if (conflict.contains(name)) {
				throw new AddonException("Addon with the name " + name + " already exists!");
			}
			label = alias.get().removeConflicts(conflict);
			logger.log(LogTypeId.WARNING,
					"Following aliases could not be used for addon \"" + name + "\": " + Strings.toString(conflict));
		} else {
			label = alias.get();
		}
		conflict.clear();
		alias.enqueue();

		addon.getAddon().setManager(this);
		if (onRegistration(label, addon)) {
			addons.put(label, addon);
		}
	}

	Alias makeAlias(Addon<E> addon) {
		JsonConfig info = addon.getAddonInfo();
		String name = (info.contains("name") ? info.get("name", String.class)
				: addon.getAddonFile().getName().replace(".jar", "")).replace(" ", "_");
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
		return new Alias(name, array).setDisplayName(display);
	}

	/*
	 * 
	 */

	protected boolean onRegistration(Alias label, Addon<E> addon) {
		return true;
	}

	/*
	 * 
	 * 
	 * 
	 */

	public int loadAddons() {
		if (!addons.isEmpty()) {
			addons.values().forEach(addon -> {
				addon.delete();
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
	
	public int enableAddons() {
		if (addons.isEmpty())
			return 0;
		int enabled = 0;
		for(Addon<E> addon : addons.values()) {
			if(addon.state.isEnabled())
				continue;
			if(!addon.state.isInitialized())
				continue;
			try {
				addon.getAddon().onEnable();
				enabled++;
			} catch(Throwable throwable) {
				logger.log(new AddonException("Couldn't enable addon \"" + addon.getAddonInfo().get("name", String.class) + "\"!", throwable));
				addon.delete();
			}
		}
		return enabled;
	}

	public E getAddon(String label) {
		if (addons.isEmpty()) {
			return null;
		}
		return addons.get(label).getAddon();
	}

	public E getAddon(Class<? extends BaseAddon> clazz) {
		if (addons.isEmpty()) {
			return null;
		}
		for (Addon<E> addon : addons.values()) {
			if (addon.getMainClass().equals(clazz)) {
				return addon.getAddon();
			}
		}
		return null;
	}
	
	public void shutdown() {
		for (Addon<E> addon : addons.values()) {
			if(!addon.state.isInitialized())
				continue;
			addon.getAddon().onDisable();
			addon.delete();
		}
	}

}
