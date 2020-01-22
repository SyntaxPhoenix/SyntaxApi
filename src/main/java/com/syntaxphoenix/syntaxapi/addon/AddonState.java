package com.syntaxphoenix.syntaxapi.addon;

public enum AddonState {
	
	LOADED, INITIALIZED, ENABLED, DISABLED, INVALID;
	
	public boolean isInitialized() {
		AddonState current = this;
		return current == INITIALIZED || current == ENABLED || current == DISABLED;
	}
	
	public boolean isEnabled() {
		return this == ENABLED;
	}
	
	public boolean isLoaded() {
		return this == LOADED;
	}
	
	public boolean isValid() {
		return this != INVALID;
	}
	
	public boolean isUseable() {
		AddonState current = this;
		return current != INVALID && current != DISABLED;
	}
	
}
