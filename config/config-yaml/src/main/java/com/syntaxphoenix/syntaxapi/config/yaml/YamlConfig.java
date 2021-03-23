package com.syntaxphoenix.syntaxapi.config.yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;

public class YamlConfig extends YamlConfigSection implements BaseConfig {

    private final String charsetName;

    public YamlConfig() {
        this("UTF-8");
    }

    public YamlConfig(String charsetName) {
        this.charsetName = charsetName;
    }

    @Override
    public void load(File file) throws IOException, RuntimeException {

        if (file.exists()) {

            Scanner scanner = new Scanner(file, charsetName);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();

            String yaml = builder.toString();
            if (yaml.isEmpty()) {
                return;
            }

            fromYamlString(yaml.substring(0, yaml.length() - 1));

        }

    }

    @Override
    public void save(File file) throws IOException {

        if (!file.exists()) {
            String parentPath = file.getParent();
            if (parentPath != null && !parentPath.isEmpty()) {
                File parent = file.getParentFile();
                if (parent.exists()) {
                    if (!parent.isDirectory()) {
                        parent.delete();
                        parent.mkdirs();
                    }
                } else {
                    parent.mkdirs();
                }
            }
            file.createNewFile();
        }

        FileOutputStream stream = new FileOutputStream(file);
        stream.write(toYamlString().getBytes(charsetName));
        stream.flush();
        stream.close();

    }

}
