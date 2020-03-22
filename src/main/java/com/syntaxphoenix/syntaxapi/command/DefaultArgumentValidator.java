package com.syntaxphoenix.syntaxapi.command;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.syntaxphoenix.syntaxapi.command.arguments.*;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

/**
 * @author Lauriichen
 *
 */
public class DefaultArgumentValidator extends ArgumentValidator {
	
	public static final Pattern LIST = Pattern.compile("\\A\\{{1}.*[, ]?.*\\}{1}\\z");
	public static final Pattern ARRAY = Pattern.compile("\\A\\[{1}.*[, ]?.*\\]{1}\\z");

	@Override
	public ArrayList<BaseArgument> process(String... arguments) {
		ArrayList<BaseArgument> list = new ArrayList<>();
		if (arguments == null || arguments.length == 0) {
			return list;
		}
		for (String argument : arguments) {
			if (Strings.isBoolean(argument)) {
				list.add(new BooleanArgument(Boolean.valueOf(argument)));
				continue;
			} else if (Strings.isNumeric(argument)) {
				try {
					list.add(new ByteArgument(Byte.parseByte(argument)));
					continue;
				} catch (NumberFormatException e) {
				}
				try {
					list.add(new ShortArgument(Short.parseShort(argument)));
					continue;
				} catch (NumberFormatException e) {
				}
				try {
					list.add(new IntegerArgument(Integer.parseInt(argument)));
					continue;
				} catch (NumberFormatException e) {
				}
				try {
					list.add(new LongArgument(Long.parseLong(argument)));
					continue;
				} catch (NumberFormatException e) {
				}
				list.add(new BigIntegerArgument(new BigInteger(argument)));
				continue;
			} else if (Strings.isDecimal(argument)) {
				String number = argument.split("\\.")[0];
				try {
					Integer.parseInt(number);
					list.add(new FloatArgument(Float.parseFloat(argument)));
					continue;
				} catch (NumberFormatException e) {
				}
				try {
					Long.parseLong(number);
					list.add(new DoubleArgument(Double.parseDouble(argument)));
					continue;
				} catch (NumberFormatException e) {
				}
				list.add(new BigDecimalArgument(new BigDecimal(argument)));
				continue;
			} else if (LIST.matcher(argument).matches()) {
				String[] args = argument.replaceFirst("\\A\\{", "").replaceFirst("\\}\\z", "").split(",");
				HashMap<ArgumentType, ArrayList<BaseArgument>> argumentMap = new HashMap<>();
				for (String arg : args) {
					if (arg.isEmpty()) {
						continue;
					}
					if (arg.startsWith(" ")) {
						arg = arg.replaceFirst(" ", "");
					}
					ArrayList<BaseArgument> value = process(arg);
					for (BaseArgument out : value) {
						ArgumentType type = out.getType();
						if (argumentMap.containsKey(type)) {
							argumentMap.get(type).add(out);
						} else {
							ArrayList<BaseArgument> output = new ArrayList<>();
							output.add(out);
							argumentMap.put(type, output);
						}
					}
				}
				Set<Entry<ArgumentType, ArrayList<BaseArgument>>> entries = argumentMap.entrySet();
				for (Entry<ArgumentType, ArrayList<BaseArgument>> entry : entries) {
					list.add(new ListArgument<>(entry.getValue()));
				}
				continue;
			} else if (ARRAY.matcher(argument).matches()) {
				String[] args = argument.replaceFirst("\\A\\[", "").replaceFirst("\\]\\z", "").split(",");
				HashMap<ArgumentType, ArrayList<BaseArgument>> argumentMap = new HashMap<>();
				for (String arg : args) {
					if (arg.isEmpty()) {
						continue;
					}
					if (arg.startsWith(" ")) {
						arg = arg.replaceFirst(" ", "");
					}
					ArrayList<BaseArgument> value = process(arg);
					for (BaseArgument out : value) {
						ArgumentType type = out.getType();
						if (argumentMap.containsKey(type)) {
							argumentMap.get(type).add(out);
						} else {
							ArrayList<BaseArgument> output = new ArrayList<>();
							output.add(out);
							argumentMap.put(type, output);
						}
					}
				}
				Set<Entry<ArgumentType, ArrayList<BaseArgument>>> entries = argumentMap.entrySet();
				for (Entry<ArgumentType, ArrayList<BaseArgument>> entry : entries) {
					list.add(new ArrayArgument<>(entry.getValue().toArray(new BaseArgument[0])));
				}
				continue;
			}
			list.add(new StringArgument(argument));
			continue;
		}
		return list;
	}

	@Override
	public String[] asStringArray(BaseArgument... arguments) {
		if (arguments == null || arguments.length == 0) {
			return new String[0];
		}
		int length = arguments.length;
		String[] array = new String[length];
		for (int index = 0; index < length; index++) {
			array[index] = arguments[index].toString();
		}
		return array;
	}

}
