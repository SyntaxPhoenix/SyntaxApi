package com.syntaxphoenix.syntaxapi.version;

@FunctionalInterface
public interface VersionAnalyzer {
	
	public Version analyze(String formatted);
	
}
