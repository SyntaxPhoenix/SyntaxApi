package com.syntaxphoenix.syntaxapi.addon;

import java.io.File;
import java.util.ArrayList;

import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.utils.alias.AliasMap;

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
	
	public int loadAddons() {
		if(!addons.isEmpty()) {
			addons.values().forEach(addon -> {
				addon.classes().clear();
			});
			addons.clear();
		}
		if(directories.isEmpty()) {
			return 0;
		}
		int addons = 0;
		for(File directory : directories) {
			addons += loader.loadAddons(directory);
		}
		return addons;
	}
	
	public BaseAddon getAddon(String label) {
		if(addons.isEmpty()) {
			return null;
		}
		return addons.get(label).getAddon();
	}
	
	public BaseAddon getAddon(Class<? extends BaseAddon> clazz) {
		if(addons.isEmpty()) {
			return null;
		}
		for(Addon addon : addons.values()) {
			if(addon.getMainClass().equals(clazz)) {
				return addon.getAddon();
			}
		}
		return null;
	}

}
