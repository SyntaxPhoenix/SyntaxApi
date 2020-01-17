package com.syntaxphoenix.syntaxapi.config.ini;

import java.io.File;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;

public class IniConfig extends IniConfigSection implements BaseConfig {

	@Override
	public void load(File file) throws Throwable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(File file) throws Throwable {

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
		
	}

}
