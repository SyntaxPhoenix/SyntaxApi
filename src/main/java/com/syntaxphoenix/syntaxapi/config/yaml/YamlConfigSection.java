package com.syntaxphoenix.syntaxapi.config.yaml;

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
		yamlOptions.setIndent(2);
		yamlOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		yamlOptions.setPrettyFlow(true);
		yamlRepresenter.setDefaultFlowStyle(FlowStyle.BLOCK);
		yaml = new Yaml(new SafeConstructor(), yamlRepresenter, yamlOptions);
	}

	@Override
	protected BaseSection initSection(String name) {
		return new YamlConfigSection(name);
	}
	
	@Override
	protected boolean isSectionInstance(BaseSection section) {
		return section instanceof YamlConfigSection;
	}

	public String toYamlString() {
		String output = "";

		if (!values.isEmpty()) {
			output = yaml.dump(toMap());
		}

		if (output.equals(BLANK_CONFIG)) {
			output = "";
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
