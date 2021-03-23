package com.syntaxphoenix.syntaxapi.config.yaml;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.config.yaml.utils.LinkedConstructor;

public class YamlConfigSection extends BaseSection {

    public static final String COMMENT_PREFIX = "#";
    public static final String BLANK_CONFIG = "{}\n";
    private final DumperOptions yamlOptions = new DumperOptions();
    private final Representer yamlRepresenter;
    private final Yaml yaml;

    private String comment = "";

    public YamlConfigSection() {
        this("");
    }

    public YamlConfigSection(String name) {
        super(name);
        yamlOptions.setIndent(2);
        yamlOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
        yamlOptions.setPrettyFlow(true);
        yamlRepresenter = new Representer(yamlOptions);
        yaml = new Yaml(new LinkedConstructor(), yamlRepresenter, yamlOptions);
    }

    @Override
    public void clear() {
        super.clear();
        comment = null;
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
        return comment == null ? output : comment + output;
    }

    public String getComment() {
        return comment;
    }

    @SuppressWarnings("unchecked")
    public void fromYamlString(String contents) {
        String[] lines = contents.split("\n");
        StringBuilder content = new StringBuilder();
        StringBuilder comment = new StringBuilder();
        for (int index = 0; index < lines.length; index++) {
            String line = lines[index];
            if (line.startsWith("#")) {
                comment.append(line);
                comment.append(System.lineSeparator());
            } else {
                content.append(line);
                if (index + 1 != lines.length) {
                    content.append('\n');
                }
            }
        }
        if (comment.length() != 0) {
            this.comment = comment.toString();
        }
        Map<String, Object> input = null;
        try {
            input = (Map<String, Object>) yaml.load(content.toString());
        } catch (YAMLException | ClassCastException e) {
            throw e;
        }

        if (input == null) {
            throw new NullPointerException("No content!");
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
