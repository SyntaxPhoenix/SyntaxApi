package com.syntaxphoenix.syntaxapi.version;

@FunctionalInterface
public interface VersionFormatter {
	
	public String format(Version version);
	
}
