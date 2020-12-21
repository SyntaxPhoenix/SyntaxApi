package com.syntaxphoenix.syntaxapi.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public class PrimitiveDataType<P> implements DataType<P, P> {

	public static final PrimitiveDataType<Byte> BYTE = new PrimitiveDataType<>(Byte.class);
	public static final PrimitiveDataType<Short> SHORT = new PrimitiveDataType<>(Short.class);
	public static final PrimitiveDataType<Integer> INTEGER = new PrimitiveDataType<>(Integer.class);
	public static final PrimitiveDataType<Long> LONG = new PrimitiveDataType<>(Long.class);

	public static final PrimitiveDataType<Float> FLOAT = new PrimitiveDataType<>(Float.class);
	public static final PrimitiveDataType<Double> DOUBLE = new PrimitiveDataType<>(Double.class);

	public static final PrimitiveDataType<BigInteger> BIG_INTEGER = new PrimitiveDataType<>(BigInteger.class);
	public static final PrimitiveDataType<BigDecimal> BIG_DECIMAL = new PrimitiveDataType<>(BigDecimal.class);

	public static final PrimitiveDataType<Boolean> BOOLEAN = new PrimitiveDataType<>(Boolean.class);

	public static final PrimitiveDataType<String> STRING = new PrimitiveDataType<>(String.class);

	public static final PrimitiveDataType<int[]> INT_ARRAY = new PrimitiveDataType<>(int[].class);
	public static final PrimitiveDataType<byte[]> BYTE_ARRAY = new PrimitiveDataType<>(byte[].class);
	public static final PrimitiveDataType<long[]> LONG_ARRAY = new PrimitiveDataType<>(long[].class);

	public static final PrimitiveDataType<IDataContainer> DATA_HOLDER = new PrimitiveDataType<>(IDataContainer.class);
	public static final PrimitiveDataType<IDataContainer[]> DATA_HOLDER_ARRAY = new PrimitiveDataType<>(IDataContainer[].class);

	public static final DataType<?, ?>[] PRIMITIVES = new PrimitiveDataType<?>[] {
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
			DATA_HOLDER,
			DATA_HOLDER_ARRAY,
			INT_ARRAY,
			BYTE_ARRAY,
			LONG_ARRAY
	};

	public static Optional<DataType<?, ?>> getPrimitive(Object object) {
		Class<?> clazz = Primitives.fromPrimitive(object.getClass());
		return Arrays.stream(PRIMITIVES).filter(type -> Objects.equals(clazz, type.getComplex())).findFirst();
	}

	/*
	 * 
	 * 
	 *
	 */

	private final Class<P> primitiveType;

	private PrimitiveDataType(Class<P> primitiveType) {
		this.primitiveType = primitiveType;
	}

	@Override
	public Class<P> getPrimitive() {
		return primitiveType;
	}

	@Override
	public Class<P> getComplex() {
		return primitiveType;
	}

	@Override
	public P toPrimitive(DataAdapterContext context, P complex) {
		return complex;
	}

	@Override
	public P fromPrimitive(DataAdapterContext context, P primitive) {
		return primitive;
	}

}
