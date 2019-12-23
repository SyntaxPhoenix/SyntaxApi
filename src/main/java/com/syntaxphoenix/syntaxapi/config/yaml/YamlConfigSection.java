package com.syntaxphoenix.syntaxapi.config.yaml;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import com.syntaxphoenix.syntaxapi.config.BaseSection;

public class YamlConfigSection extends BaseSection {

	public static final String COMMENT_PREFIX = "# ";
	public static final String BLANK_CONFIG = "{}\n";
	private final DumperOptions yamlOptions = new DumperOptions();
	private final Representer yamlRepresenter = new Representer();
	private final Yaml yaml;

	public YamlConfigSection() {
		this("");
	}

	public YamlConfigSection(String name) {
		super(name);
		yaml = new Yaml(new SafeConstructor(), yamlRepresenter, yamlOptions);
	}

	@Override
	protected BaseSection initSection(String name) {
		return new YamlConfigSection(name);
	}

	public String toYamlString() {
		yamlOptions.setIndent(2);
		yamlOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		yamlRepresenter.setDefaultFlowStyle(FlowStyle.BLOCK);

		String output = "";

		if (!values.isEmpty()) {
			output = yaml.dump(toValueMap());
		}

		if (output.equals(BLANK_CONFIG)) {
			output = "";
		}

		return output;
	}
	
	public HashMap<String, Object> toValueMap() {
		HashMap<String, Object> output = new HashMap<>();
		Set<Entry<String, Object>> entries = values.entrySet();
		for (Entry<String, Object> entry : entries) {
			Object obj = entry.getValue();
			if (obj instanceof YamlConfigSection) {
				output.put(entry.getKey(), ((YamlConfigSection) obj).toValueMap());
			} else {
				output.put(entry.getKey(), entry.getValue());
			}
		}
		return output;
	}

	@SuppressWarnings("unchecked")
	public void fromYamlString(String contents) {

		Map<String, Object> input = null;
		try {
			input = (Map<String, Object>) yaml.load(contents);
		} catch (YAMLException | ClassCastException e) {
			e.printStackTrace();
		}

		if (input == null) {
			return;
		}

		fromEntrySet(input.entrySet());		
	}
	
	@SuppressWarnings("unchecked")
	private void fromEntrySet(Set<Entry<String, Object>> entries) {
		for (Entry<String, Object> entry : entries) {
			Object value = entry.getValue();
			if (value instanceof Map) {
				((YamlConfigSection) createSection(entry.getKey())).fromEntrySet(((Map<String, Object>) value).entrySet());
			} else {
				set(entry.getKey(), entry.getValue());
			}
		}
	}

}
