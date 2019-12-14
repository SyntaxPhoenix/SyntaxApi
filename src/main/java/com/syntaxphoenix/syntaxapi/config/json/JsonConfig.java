package com.syntaxphoenix.syntaxapi.config.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;

/**
 * @author Lauriichen
 *
 */
public class JsonConfig extends JsonConfigSection implements BaseConfig {

	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseConfig#load(java.io.File)
	 */
	@Override
	public void load(File file) throws IOException {

		if (file.exists()) {

			Scanner scanner = new Scanner(file);
			String json = "";
			while (scanner.hasNextLine()) {
				json += scanner.nextLine() + "\n";
			}
			scanner.close();
			fromJsonString(json);

		}

	}

	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseConfig#save(java.io.File)
	 */
	@Override
	public void save(File file) throws IOException {
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file);
		writer.write(toJsonString());
		writer.close();

	}

}
