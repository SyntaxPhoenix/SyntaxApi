package com.syntaxphoenix.syntaxapi.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

public class PrimitiveDataType<P, C> implements DataType<P, C> {

	public static final PrimitiveDataType<Byte, Byte> BYTE = new PrimitiveDataType<>(byte.class, Byte.class);
	public static final PrimitiveDataType<Short, Short> SHORT = new PrimitiveDataType<>(short.class, Short.class);
	public static final PrimitiveDataType<Integer, Integer> INTEGER = new PrimitiveDataType<>(int.class, Integer.class);
	public static final PrimitiveDataType<Long, Long> LONG = new PrimitiveDataType<>(long.class, Long.class);

	public static final PrimitiveDataType<Float, Float> FLOAT = new PrimitiveDataType<>(float.class, Float.class);
	public static final PrimitiveDataType<Double, Double> DOUBLE = new PrimitiveDataType<>(double.class, Double.class);

	public static final PrimitiveDataType<BigInteger, BigInteger> BIG_INTEGER = new PrimitiveDataType<>(BigInteger.class, BigInteger.class);
	public static final PrimitiveDataType<BigDecimal, BigDecimal> BIG_DECIMAL = new PrimitiveDataType<>(BigDecimal.class, BigDecimal.class);

	public static final PrimitiveDataType<Boolean, Boolean> BOOLEAN = new PrimitiveDataType<>(boolean.class, Boolean.class);

	public static final PrimitiveDataType<String, String> STRING = new PrimitiveDataType<>(String.class, String.class);

	public static final PrimitiveDataType<int[], int[]> INT_ARRAY = new PrimitiveDataType<>(int[].class, int[].class);
	public static final PrimitiveDataType<byte[], byte[]> BYTE_ARRAY = new PrimitiveDataType<>(byte[].class, byte[].class);
	public static final PrimitiveDataType<long[], long[]> LONG_ARRAY = new PrimitiveDataType<>(long[].class, long[].class);

	public static final DataType<?, ?>[] PRIMITIVES = new PrimitiveDataType<?, ?>[] {
			BYTE,
			SHORT,
			INTEGER,
			LONG,
			FLOAT,
			DOUBLE,
			BIG_INTEGER,
			BIG_DECIMAL,
			BOOLEAN,
			STRING,
			/* DATA_HOLDER, DATA_HOLDER_ARRAY, */ INT_ARRAY,
			BYTE_ARRAY,
			LONG_ARRAY
	};

	public static Optional<DataType<?, ?>> getPrimitive(Object object) {
		Class<?> clazz = object.getClass();
		return Arrays.stream(PRIMITIVES).filter(type -> type.getComplex() == clazz || type.getPrimitive() == clazz).findFirst();
	}

	/*
	 * 
	 * 
	 *
	 */

	private final Class<P> primitiveType;
	private final Class<C> complexType;

	private PrimitiveDataType(Class<P> primitiveType, Class<C> complexType) {
		this.primitiveType = primitiveType;
		this.complexType = complexType;
	}

	@Override
	public Class<P> getPrimitive() {
		return primitiveType;
	}

	@Override
	public Class<C> getComplex() {
		return complexType;
	}

	@Override
	public P toPrimitive(DataAdapterContext context, C complex) {
		return primitiveType.cast(complex);
	}

	@Override
	public C fromPrimitive(DataAdapterContext context, P primitive) {
		return complexType.cast(primitive);
	}

}
