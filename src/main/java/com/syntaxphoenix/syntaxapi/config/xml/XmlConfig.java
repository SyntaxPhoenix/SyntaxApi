package com.syntaxphoenix.syntaxapi.config.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;
import com.thoughtworks.xstream.XStream;

public class XmlConfig extends XmlConfigSection implements BaseConfig {
	
	private final XStream stream;
	
	public XmlConfig() {
		this(XmlDriver.XOM);
	}
	
	public XmlConfig(XmlDriver driver) {
		this(driver.newStream());
	}
	
	public XmlConfig(XStream stream) {
		this.stream = stream;
	}

	@Override
	public void load(File file) throws IOException {
		
		if (file.exists()) {

			Scanner scanner = new Scanner(file);
			StringBuilder builder = new StringBuilder();
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());
			}
			scanner.close();
			
			String xml = builder.toString();
			fromXmlString(xml.substring(0, xml.length() - 1), stream);

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
		writer.write(toXmlString(stream));
		writer.close();

	}

}
