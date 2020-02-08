package com.syntaxphoenix.syntaxapi.addon;

import com.syntaxphoenix.syntaxapi.config.json.JsonConfig;

/**
 * @author Lauriichen
 *
 */
public abstract class BaseAddon {

	private AddonManager<?> manager;
	private Addon<?> addon;

	void setManager(AddonManager<?> manager) {
		if (this.manager == null) {
			this.manager = manager;
		}
	}
	
	void setAddon(Addon<?> addon) {
		if(addon.getAddon() == this) {
			this.addon = addon;
		}
	}
	
	/*
	 * 
	 */
	
	public JsonConfig getInfo() {
		return addon.getAddonInfo();
	}
	
	public AddonState getState() {
		return addon.getState();
	}
	
	public AddonManager<?> getManager() {
		return manager;
	}

	/*
	 * 
	 */

	public void onLoad() {

	}

	public void onEnable() {

	}

	public void onDisable() {

	}

}
