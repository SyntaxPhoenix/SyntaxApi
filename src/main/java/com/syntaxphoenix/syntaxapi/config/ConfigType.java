package com.syntaxphoenix.syntaxapi.config;

import com.syntaxphoenix.syntaxapi.config.ini.*;
import com.syntaxphoenix.syntaxapi.config.yaml.*;
import com.syntaxphoenix.syntaxapi.reflections.Reflect;
import com.syntaxphoenix.syntaxapi.config.json.*;
import com.syntaxphoenix.syntaxapi.config.nbt.*;
import com.syntaxphoenix.syntaxapi.config.xml.*;
import com.syntaxphoenix.syntaxapi.config.toml.*;

public enum ConfigType {

	JSON(JsonConfig.class, JsonConfigSection.class), YAML(YamlConfig.class, YamlConfigSection.class),
	INI(IniConfig.class, IniConfigSection.class), NBT(NbtConfig.class, NbtConfigSection.class),
	TOML(TomlConfig.class, TomlConfigSection.class), XML(XmlConfig.class, XmlConfigSection.class);
	
	private Reflect baseConfig;
	private Reflect baseSection;
	
	private ConfigType(Class<? extends BaseConfig> baseConfig, Class<? extends BaseSection> baseSection) {
		this.baseConfig = new Reflect(baseConfig);
		this.baseSection = new Reflect(baseSection);
	}
	
	public BaseConfig newConfig() {
		return (BaseConfig) baseConfig.init();
	}
	
	public BaseSection newSection() {
		return (BaseSection) baseSection.init();
	}
	
}

