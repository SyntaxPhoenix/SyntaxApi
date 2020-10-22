package com.syntaxphoenix.syntaxapi.command;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public final class ArgumentMap {

	public static final ArgumentMap EMPTY = new ArgumentMap(Collections.emptyMap());

	protected final Map<String, BaseArgument> arguments = Collections.synchronizedMap(new HashMap<>());

	private final boolean locked;

	public ArgumentMap() {
		locked = true;
	}

	public ArgumentMap(Map<String, BaseArgument> arguments) {
		this.arguments.putAll(arguments);
		locked = false;
	}

	final Map<String, BaseArgument> getHandle() {
		return arguments;
	}

	public final boolean isLocked() {
		return locked;
	}

	public final boolean set(String key, BaseArgument value) {
		return !locked && !arguments.containsKey(key) && arguments.put(key, value) == null;
	}

	public final Optional<BaseArgument> get(String key) {
		return Optional.ofNullable(arguments.get(key));
	}

	public final Optional<BaseArgument> get(String key, ArgumentType type) {
		return get(key).filter(argument -> argument.getType() == type);
	}

	public final Optional<BaseArgument> get(String key, ArgumentSuperType type) {
		return get(key).filter(argument -> argument.getSuperType() == type);
	}

	@SuppressWarnings("unchecked")
	public final <E extends Number> Optional<E> number(String key, E sample) {
		return number(key, (Class<E>) sample.getClass());
	}

	@SuppressWarnings("unchecked")
	public final <E extends Number> Optional<E> number(String key, Class<E> sample) {
		Optional<BaseArgument> optional = get(key, ArgumentSuperType.NUMBER);
		if (!optional.isPresent())
			return Optional.empty();
		Optional<NumberEnum> option = NumberEnum.of(sample);
		if (!option.isPresent())
			return Optional.empty();
		NumericArgument argument = optional.get().asNumeric();
		boolean primitive = Primitives.isPrimitive(sample);
		switch (option.get()) {
		case BYTE:
			if (argument.getType() == ArgumentType.BYTE)
				return option(primitive ? argument.asByte().getValue().byteValue() : argument.asByte().getValue(), sample);
			byte value = argument.asNumber().byteValue();
			return option(primitive ? value : Byte.valueOf(value), sample);
		case SHORT:
			if (argument.getType() == ArgumentType.SHORT)
				return option(primitive ? argument.asShort().getValue().shortValue() : argument.asShort().getValue(), sample);
			short value0 = argument.asNumber().shortValue();
			return option(primitive ? value0 : Short.valueOf(value0), sample);
		case INTEGER:
			if (argument.getType() == ArgumentType.INTEGER)
				return option(primitive ? argument.asInteger().getValue().intValue() : argument.asInteger().getValue(), sample);
			int value1 = argument.asNumber().intValue();
			return option(primitive ? value1 : Integer.valueOf(value1), sample);
		case LONG:
			if (argument.getType() == ArgumentType.LONG)
				return option(primitive ? argument.asLong().getValue().longValue() : argument.asLong().getValue(), sample);
			long value2 = argument.asNumber().longValue();
			return option(primitive ? value2 : Long.valueOf(value2), sample);
		case FLOAT:
			if (argument.getType() == ArgumentType.FLOAT)
				return option(primitive ? argument.asFloat().getValue().floatValue() : argument.asFloat().getValue(), sample);
			float value3 = argument.asNumber().floatValue();
			return option(primitive ? value3 : Float.valueOf(value3), sample);
		case DOUBLE:
			if (argument.getType() == ArgumentType.DOUBLE)
				return option(primitive ? argument.asDouble().getValue().doubleValue() : argument.asDouble().getValue(), sample);
			double value4 = argument.asNumber().doubleValue();
			return option(primitive ? value4 : Double.valueOf(value4), sample);
		case BIG_INTEGER:
			return (Optional<E>) Optional
				.of(argument.getType() == ArgumentType.BIG_INTEGER ? argument.asBigInteger().getValue() : new BigInteger(argument.asNumber().toString()));
		case BIG_DECIMAL:
			return (Optional<E>) Optional
				.of(argument.getType() == ArgumentType.BIG_DECIMAL ? argument.asBigDecimal().getValue() : new BigDecimal(argument.asNumber().toString()));
		}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	private final <T, E> Optional<E> option(T value, Class<E> sample) {
		return Optional.of(value).map(internal -> (E) internal);
	}

}
