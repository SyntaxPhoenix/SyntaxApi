package com.syntaxphoenix.syntaxapi.config.toml;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;

public class TomlConfig extends TomlConfigSection implements BaseConfig {

    @Override
    public void load(File file) throws Throwable {

        if (file.exists()) {

            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();

            String toml = builder.toString();
            fromTomlString(toml.substring(0, toml.length() - 1));

        }

    }

    @Override
    public void save(File file) throws Throwable {

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

        FileWriter writer = new FileWriter(file);
        writer.write(toTomlString());
        writer.close();

    }

}
