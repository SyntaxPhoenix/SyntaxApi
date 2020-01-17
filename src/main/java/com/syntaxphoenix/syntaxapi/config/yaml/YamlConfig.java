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
			StringBuilder builder = new StringBuilder();
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());
				builder.append('\n');
			}
			scanner.close();

			String yaml = builder.toString();
			fromYamlString(yaml.substring(0, yaml.length() - 1));

		}

	}

	@Override
	public void save(File file) throws IOException {
		
		if(!file.exists()) {
			String parentPath = file.getParent();
			if(parentPath != null && !parentPath.isEmpty()) {
				File parent = file.getParentFile();
				if(parent.exists()) {
					if(!parent.isDirectory()) {
						parent.delete();
						parent.mkdirs();
					}
				} else {
					parent.mkdirs();
				}
			}
			file.createNewFile();
		}

		FileWriter writer = new FileWriter(file);
		writer.write(toYamlString());
		writer.close();

	}

}
