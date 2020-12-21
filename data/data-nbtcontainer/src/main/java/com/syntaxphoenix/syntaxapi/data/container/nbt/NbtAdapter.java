package com.syntaxphoenix.syntaxapi.data.container.nbt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.data.DataAdapter;
import com.syntaxphoenix.syntaxapi.data.DataContainer;
import com.syntaxphoenix.syntaxapi.data.IDataContainer;
import com.syntaxphoenix.syntaxapi.nbt.*;
import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public class NbtAdapter<P, C extends NbtTag> extends DataAdapter<P, C, NbtTag> {

	protected NbtAdapter(Class<P> primitiveType, Class<C> resultType, Function<P, C> builder, Function<C, P> extractor) {
		super(primitiveType, resultType, builder, extractor);
	}

	@Override
	public Class<NbtTag> getBaseType() {
		return NbtTag.class;
	}

	/*
	 * 
	 */

	@SuppressWarnings({
			"rawtypes",
			"unchecked"
	})
	protected static DataAdapter<?, ? extends NbtTag, NbtTag> createAdapter(NbtAdapterRegistry registry, Class<?> type) {
		type = Primitives.fromPrimitive(type);

		/*
		 * Numbers
		 */

		if (Objects.equals(Boolean.class, type))
			return new NbtAdapter<Boolean, NbtByte>(Boolean.class, NbtByte.class, state -> new NbtByte((byte) (state ? 1 : 0)),
				value -> value.getByteValue() == 1);

		if (Objects.equals(Byte.class, type))
			return new NbtAdapter<Byte, NbtByte>(Byte.class, NbtByte.class, NbtByte::new, value -> value.getByteValue());

		if (Objects.equals(Short.class, type))
			return new NbtAdapter<Short, NbtShort>(Short.class, NbtShort.class, NbtShort::new, value -> value.getShortValue());

		if (Objects.equals(Integer.class, type))
			return new NbtAdapter<Integer, NbtInt>(Integer.class, NbtInt.class, NbtInt::new, value -> value.getIntValue());

		if (Objects.equals(Long.class, type))
			return new NbtAdapter<Long, NbtLong>(Long.class, NbtLong.class, NbtLong::new, value -> value.getLongValue());

		if (Objects.equals(BigInteger.class, type))
			return new NbtAdapter<BigInteger, NbtBigInt>(BigInteger.class, NbtBigInt.class, NbtBigInt::new, value -> value.getInteger());

		if (Objects.equals(Float.class, type))
			return new NbtAdapter<Float, NbtFloat>(Float.class, NbtFloat.class, NbtFloat::new, value -> value.getFloatValue());

		if (Objects.equals(Double.class, type))
			return new NbtAdapter<Double, NbtDouble>(Double.class, NbtDouble.class, NbtDouble::new, value -> value.getDoubleValue());

		if (Objects.equals(BigDecimal.class, type))
			return new NbtAdapter<BigDecimal, NbtBigDecimal>(BigDecimal.class, NbtBigDecimal.class, NbtBigDecimal::new, value -> value.getDecimal());

		/*
		 * String
		 */

		if (Objects.equals(String.class, type))
			return new NbtAdapter<String, NbtString>(String.class, NbtString.class, NbtString::new, value -> value.getValue());

		/*
		 * Number Arrays
		 */

		if (Objects.equals(byte[].class, type))
			return new NbtAdapter<byte[], NbtByteArray>(byte[].class, NbtByteArray.class, NbtByteArray::new, value -> value.getValue());

		if (Objects.equals(int[].class, type))
			return new NbtAdapter<int[], NbtIntArray>(int[].class, NbtIntArray.class, NbtIntArray::new, value -> value.getValue());

		if (Objects.equals(long[].class, type))
			return new NbtAdapter<long[], NbtLongArray>(long[].class, NbtLongArray.class, NbtLongArray::new, value -> value.getValue());

		/*
		 * Complex Arrays
		 */

		if (Objects.equals(IDataContainer[].class, type))
			return new NbtAdapter<IDataContainer[], NbtList>(IDataContainer[].class, NbtList.class, containers -> {
				NbtList<NbtCompound> list = new NbtList<>(NbtType.COMPOUND);
				for (IDataContainer container : containers) {
					list.add(toNbtCompound(registry, container));
				}
				return list;
			}, list -> {
				if (list.getElementType() != NbtType.COMPOUND) {
					return new IDataContainer[0];
				}
				NbtList<NbtCompound> nbtList = (NbtList<NbtCompound>) list;
				ArrayList<IDataContainer> containers = new ArrayList<>();
				for (NbtTag tag : nbtList) {
					containers.add(fromNbtCompound(registry, (NbtCompound) tag));
				}
				return containers.toArray(new IDataContainer[0]);
			});

		if (Objects.equals(DataContainer[].class, type))
			return new NbtAdapter<DataContainer[], NbtList>(DataContainer[].class, NbtList.class, containers -> {
				NbtList<NbtCompound> list = new NbtList<>(NbtType.COMPOUND);
				for (DataContainer container : containers) {
					list.add(toNbtCompound(registry, container));
				}
				return list;
			}, list -> {
				if (list.getElementType() != NbtType.COMPOUND) {
					return new NbtContainer[0];
				}
				NbtList<NbtCompound> nbtList = (NbtList<NbtCompound>) list;
				ArrayList<DataContainer> containers = new ArrayList<>();
				for (NbtTag tag : nbtList) {
					containers.add(fromNbtCompound(registry, (NbtCompound) tag));
				}
				return containers.toArray(new DataContainer[0]);
			});

		if (Objects.equals(NbtContainer[].class, type))
			return new NbtAdapter<NbtContainer[], NbtList>(NbtContainer[].class, NbtList.class, containers -> {
				NbtList<NbtCompound> list = new NbtList<>(NbtType.COMPOUND);
				for (NbtContainer container : containers) {
					list.add(toNbtCompound(registry, container));
				}
				return list;
			}, list -> {
				if (list.getElementType() != NbtType.COMPOUND) {
					return new NbtContainer[0];
				}
				NbtList<NbtCompound> nbtList = (NbtList<NbtCompound>) list;
				ArrayList<NbtContainer> containers = new ArrayList<>();
				for (NbtTag tag : nbtList) {
					containers.add(fromNbtCompound(registry, (NbtCompound) tag));
				}
				return containers.toArray(new NbtContainer[0]);
			});

		/*
		 * Complex
		 */

		if (Objects.equals(IDataContainer.class, type))
			return new NbtAdapter<IDataContainer, NbtCompound>(IDataContainer.class, NbtCompound.class, container -> {
				return toNbtCompound(registry, container);
			}, compound -> {
				return fromNbtCompound(registry, compound);
			});

		if (Objects.equals(DataContainer.class, type))
			return new NbtAdapter<DataContainer, NbtCompound>(DataContainer.class, NbtCompound.class, container -> {
				return toNbtCompound(registry, container);
			}, compound -> {
				return fromNbtCompound(registry, compound);
			});

		if (Objects.equals(NbtContainer.class, type))
			return new NbtAdapter<NbtContainer, NbtCompound>(NbtContainer.class, NbtCompound.class, container -> {
				return toNbtCompound(registry, container);
			}, compound -> {
				return fromNbtCompound(registry, compound);
			});

		/*
		 * NbtTag
		 */

		if (NbtTag.class.isAssignableFrom(type))
			return new NbtAdapter<NbtTag, NbtTag>(NbtTag.class, NbtTag.class, tag -> tag, tag -> tag);

		return null;
	}

	@SuppressWarnings({
			"unchecked",
			"rawtypes"
	})
	private static NbtCompound toNbtCompound(NbtAdapterRegistry registry, IDataContainer container) {
		if (container instanceof NbtContainer) {
			return ((NbtContainer) container).getRoot().clone();
		}
		NbtCompound compound = new NbtCompound();
		for (String key : container.getKeyspaces()) {
			Object object = container.get(key);
			NbtTag tag = registry.wrap((Class) object.getClass(), object);
			if (tag != null) {
				compound.set(key, tag);
			}
		}
		return compound;
	}

	private static NbtContainer fromNbtCompound(NbtAdapterRegistry registry, NbtCompound compound) {
		return new NbtContainer(compound.clone(), registry);
	}

}
