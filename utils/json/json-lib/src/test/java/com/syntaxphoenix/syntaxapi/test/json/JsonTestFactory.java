package com.syntaxphoenix.syntaxapi.test.json;

import static com.syntaxphoenix.syntaxapi.test.json.JsonTestConfiguration.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.io.JsonWriter;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

public class JsonTestFactory {

    private static final DecimalFormat FORMAT = new DecimalFormat("00");

    // Creates samples to be parsed
    @TestFactory
    public Collection<DynamicTest> b0Parse() {
        try {
            FileUtils.cleanDirectory(TEST_DIRECTORY);
        } catch (IOException e) {
        }
        TEST_DIRECTORY.mkdirs();
        ArrayList<DynamicTest> list = new ArrayList<>();
        if (TEST_PLAIN) {
            JsonWriter plainWriter = new JsonWriter().setPretty(false);
            for (int i = 0; i < TEST_AMOUNT; i++) {
                RandomNumberGenerator random = newGenerator();
                final int id = i;
                list.add(DynamicTest.dynamicTest("sample-" + FORMAT.format(i), () -> {
                    JsonObject object = new JsonObject();
                    for (ValueType type : ValueType.values()) {
                        JsonValue<?> value = build(random, type);
                        if (value == null) {
                            continue;
                        }
                        object.set(type.name(), value);
                    }
                    plainWriter.toFile(object, new File(TEST_DIRECTORY, "test-" + FORMAT.format(id) + ".json"));
                }));
            }
        } else {
            skipGenerators();
        }
        if (TEST_PRETTY) {
            JsonWriter prettyWriter = new JsonWriter().setPretty(true).setSpaces(true).setIndent(4);
            for (int i = 0; i < TEST_AMOUNT; i++) {
                RandomNumberGenerator random = newGenerator();
                final int id = i;
                list.add(DynamicTest.dynamicTest("sample-pretty-" + FORMAT.format(i), () -> {
                    JsonObject object = new JsonObject();
                    for (ValueType type : ValueType.values()) {
                        JsonValue<?> value = build(random, type);
                        if (value == null) {
                            continue;
                        }
                        object.set(type.name(), value);
                    }
                    prettyWriter.toFile(object, new File(TEST_DIRECTORY, "test-pretty-" + FORMAT.format(id) + ".json"));
                }));
            }
        }
        return list;
    }

    // Parses value and prints it
    @TestFactory
    public Collection<DynamicTest> b1Parse() {
        if (!TEST_DIRECTORY.exists()) {
            return Collections.emptyList();
        }
        ArrayList<DynamicTest> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonWriter plainWriter = new JsonWriter().setPretty(false);
        JsonWriter prettyWriter = new JsonWriter().setPretty(true).setSpaces(true).setIndent(4);
        for (File file : TEST_DIRECTORY.listFiles()) {
            if (!file.getName().endsWith(".json")) {
                continue;
            }
            final String name = file.getName().replace(".json", "");
            list.add(DynamicTest.dynamicTest(name, () -> {
                File target = new File(TEST_DIRECTORY, name + "-parsed.json");
                JsonValue<?> parsed = parser.fromFile(file);
                if (name.contains("pretty")) {
                    prettyWriter.toFile(parsed, target);
                    return;
                }
                plainWriter.toFile(parsed, target);
            }));
        }
        return list;
    }

    // Checks the output of parsed value
    @TestFactory
    public Collection<DynamicTest> c2Parse() {
        if (!TEST_DIRECTORY.exists()) {
            return Collections.emptyList();
        }
        ArrayList<DynamicTest> list = new ArrayList<>();
        for (File file : TEST_DIRECTORY.listFiles()) {
            if (!file.getName().endsWith(".json") || file.getName().contains("parsed")) {
                continue;
            }
            File target = new File(TEST_DIRECTORY, file.getName().replace(".json", "") + "-parsed.json");
            list.add(DynamicTest.dynamicTest(file.getName(), () -> {
                Assertions.assertTrue(FileUtils.contentEqualsIgnoreEOL(file, target, "UTF-8"));
            }));
        }
        return list;
    }

}
