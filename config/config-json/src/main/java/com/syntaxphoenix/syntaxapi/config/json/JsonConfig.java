package com.syntaxphoenix.syntaxapi.config.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;

/**
 * @author Lauriichen
 *
 */
public class JsonConfig extends JsonConfigSection implements BaseConfig {

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

            String json = builder.toString();
            fromJsonString(json.substring(0, json.length() - 1));

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

        FileWriter writer = new FileWriter(file);
        writer.write(toJsonString());
        writer.close();

    }

}
