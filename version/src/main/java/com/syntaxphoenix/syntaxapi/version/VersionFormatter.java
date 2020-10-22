package com.syntaxphoenix.syntaxapi.version;

/**
 * 
 * @author Lauriichan
 *
 */
@FunctionalInterface
public interface VersionFormatter {

	public String format(Version version);

}
