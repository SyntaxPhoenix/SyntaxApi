package com.syntaxphoenix.syntaxapi.config.yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;

public class YamlConfig extends YamlConfigSection implements BaseConfig {

	@Override
	public void load(File file) throws IOException {
		
		if (file.exists()) {

			Scanner scanner = new Scanner(file);
			String yaml = "";
			while (scanner.hasNextLine()) {
				yaml += scanner.nextLine() + "\n";
			}
			scanner.close();
			fromYamlString(yaml);

		}
		
	}

	@Override
	public void save(File file) throws IOException {
		
		File folder = file.getParentFile();
		if(!folder.exists()) {
			folder.mkdirs();
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file);
		writer.write(toYamlString());
		writer.close();

	}

}
