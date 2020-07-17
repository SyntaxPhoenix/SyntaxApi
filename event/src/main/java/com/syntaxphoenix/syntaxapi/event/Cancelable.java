package com.syntaxphoenix.syntaxapi.event;

/**
 * @author Lauriichen
 *
 */
public interface Cancelable {
	
	public boolean isCancelled();
	
	public void setCancelled(boolean cancelled);
	
}
