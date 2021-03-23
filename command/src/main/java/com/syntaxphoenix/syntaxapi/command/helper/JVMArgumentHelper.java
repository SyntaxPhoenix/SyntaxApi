package com.syntaxphoenix.syntaxapi.command.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.syntaxphoenix.syntaxapi.command.ArgumentIdentifier;
import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.DefaultArgumentIdentifier;
import com.syntaxphoenix.syntaxapi.command.DefaultArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.SerializationHelper;
import com.syntaxphoenix.syntaxapi.command.arguments.BooleanArgument;

public final class JVMArgumentHelper extends SerializationHelper {

    public static final JVMArgumentHelper DEFAULT = new JVMArgumentHelper(DefaultArgumentIdentifier.DEFAULT,
        DefaultArgumentSerializer.DEFAULT);

    public JVMArgumentHelper(ArgumentIdentifier identifier, ArgumentSerializer serializer) {
        super(identifier, serializer);
    }

    public Map<String, BaseArgument> serializeMap(String... arguments) {
        HashMap<String, BaseArgument> output = new HashMap<>();
        if (arguments.length == 0) {
            return Collections.unmodifiableMap(output);
        }
        for (int index = 0; index < arguments.length; index++) {
            String first = arguments[index];
            if (!first.startsWith("--")) {
                continue;
            }
            first = first.substring(2);
            if (index + 1 >= arguments.length) {
                output.put(first, new BooleanArgument(true));
                continue;
            }
            String second = arguments[index + 1];
            if (second.startsWith("--")) {
                output.put(first, new BooleanArgument(true));
                continue;
            }
            if (second.startsWith("\"")) {
                StringBuilder builder = new StringBuilder();
                String type = second.substring(1);
                if (index + 2 >= arguments.length) {
                    type = type.substring(0, type.length() - 1);
                }
                if (type.endsWith("\"")) {
                    builder.append(type.substring(0, type.length() - 1));
                } else {
                    builder.append(type);
                    for (int current = index + 2; current < arguments.length; current++) {
                        builder.append(' ');
                        if (!arguments[current].endsWith("\"")) {
                            builder.append(arguments[current]);
                            continue;
                        }
                        builder.append(arguments[current].substring(0, arguments[current].length() - 1));
                        break;
                    }
                }
                output.put(first, identifier.process(builder.toString()).get(0));
                continue;
            } else {
                output.put(first, identifier.process(second).get(0));
            }
        }
        return Collections.unmodifiableMap(output);
    }

    public String[] deserializeMap(Map<String, BaseArgument> arguments) {
        if (arguments.isEmpty()) {
            return new String[0];
        }
        ArrayList<String> list = new ArrayList<>();
        arguments.entrySet().forEach(entry -> {
            list.add("--" + entry.getKey());
            BaseArgument argument = entry.getValue();
            if (!(argument instanceof BooleanArgument)) {
                list.add('"' + argument.toString(serializer) + '"');
                return;
            }
            if (!(argument.asBoolean()).getValue()) {
                list.add("false");
            }
        });
        return list.toArray(new String[list.size()]);
    }

}
