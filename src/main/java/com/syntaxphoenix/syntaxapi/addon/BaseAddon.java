package com.syntaxphoenix.syntaxapi.addon;

/**
 * @author Lauriichen
 *
 */ 
public abstract class BaseAddon {
	
	public abstract void onLoad();
	
	public abstract void onEnable();
	
	public abstract void onReload();
	
	public abstract void onDisable();
	
}
