package com.syntaxphoenix.syntaxapi.command.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.syntaxphoenix.syntaxapi.command.ArgumentIdentifier;
import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.DefaultArgumentIdentifier;
import com.syntaxphoenix.syntaxapi.command.DefaultArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.SerializationHelper;
import com.syntaxphoenix.syntaxapi.command.arguments.BooleanArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.StringArgument;

public final class EqualArgumentHelper extends SerializationHelper {

	public static final EqualArgumentHelper DEFAULT = new EqualArgumentHelper(DefaultArgumentIdentifier.DEFAULT, DefaultArgumentSerializer.DEFAULT);

	private final Pattern entryPattern = Pattern.compile("(?<Key>[a-zA-Z0-9._-]+)(?<Split>=|:)(?<Start>\")?(?<Value1>.+[^\"])(?<Value2>(?<End>\")+)?");

	public EqualArgumentHelper(ArgumentIdentifier identifier, ArgumentSerializer serializer) {
		super(identifier, serializer);
	}

	public Map<String, BaseArgument> serializeMap(String... arguments) {
		HashMap<String, BaseArgument> output = new HashMap<>();
		if (arguments.length == 0)
			return Collections.unmodifiableMap(output);
		for (int index = 0; index < arguments.length; index++) {
			Matcher matcher = entryPattern.matcher(arguments[index]);
			if (!matcher.matches()) {
				continue;
			}
			String key = matcher.group("Key");
			String value1 = matcher.group("Value1");
			String value2 = matcher.group("Value2");
			if (value1 == null && value2 == null) {
				output.put(key, new StringArgument());
				continue;
			}
			if (value2 != null && matcher.group("Start") != null) {
				value2 = value2.substring(0, value2.length() - 1);
			}
			output.put(key, DefaultArgumentIdentifier.DEFAULT.process(orEmpty(value1) + orEmpty(value2)).get(0));
		}
		return Collections.unmodifiableMap(output);
	}

	public String[] deserializeMap(Map<String, BaseArgument> arguments) {
		if (arguments.isEmpty())
			return new String[0];
		ArrayList<String> list = new ArrayList<>();
		arguments.entrySet().forEach(entry -> {
			BaseArgument argument = entry.getValue();
			if (!(argument instanceof BooleanArgument)) {
				list.add(entry.getKey() + "=\"" + argument.toString(serializer) + '"');
				return;
			}
			if (!(argument.asBoolean()).getValue())
				list.add("false");
		});
		return list.toArray(new String[list.size()]);
	}

	private String orEmpty(String value) {
		return value == null ? "" : value;
	}

}