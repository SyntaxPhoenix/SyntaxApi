package com.syntaxphoenix.syntaxapi.config;

import java.io.File;
import java.io.IOException;

/**
 * @author Lauriichen
 *
 */
public interface BaseConfig {
	
	public void load(File file) throws IOException;
	
	public void save(File file) throws IOException;

}
