package com.syntaxphoenix.syntaxapi.version;

/**
 * 
 * @author Lauriichan
 *
 */
@FunctionalInterface
public interface VersionAnalyzer {
	
	public Version analyze(String formatted);
	
}
