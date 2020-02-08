package com.syntaxphoenix.syntaxapi.addon;

import com.syntaxphoenix.syntaxapi.logging.SynLogger;

public class DefaultAddonManager<E extends BaseAddon> extends AddonManager<E> {

	public DefaultAddonManager(Class<E> addonClass) {
		super(addonClass);
	}

	public DefaultAddonManager(Class<E> addonClass, SynLogger logger) {
		super(addonClass, logger);
	}

}
