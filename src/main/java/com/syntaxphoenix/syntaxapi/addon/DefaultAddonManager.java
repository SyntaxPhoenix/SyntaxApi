package com.syntaxphoenix.syntaxapi.addon;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

public class DefaultAddonManager<E extends BaseAddon> extends AddonManager<E> {

	public DefaultAddonManager(Class<E> addonClass) {
		super(addonClass);
	}

	public DefaultAddonManager(Class<E> addonClass, ILogger logger) {
		super(addonClass, logger);
	}

}
