package com.syntaxphoenix.syntaxapi.utils.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class Collect {

	public static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

	@SuppressWarnings("unchecked")
	public static <I, R> Function<I, R> castingIdentity() {
		return i -> (R) i;
	}

	public static <T> Function<T, T> passthrough() {
		return i -> i;
	}

	/**
	 * Simple implementation class for {@code Collector}.
	 *
	 * @param <T> the type of elements to be collected
	 * @param <R> the type of the result
	 */
	public static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
		private final Supplier<A> supplier;
		private final BiConsumer<A, T> accumulator;
		private final BinaryOperator<A> combiner;
		private final Function<A, R> finisher;
		private final Set<Characteristics> characteristics;

		public CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher,
			Set<Characteristics> characteristics) {
			this.supplier = supplier;
			this.accumulator = accumulator;
			this.combiner = combiner;
			this.finisher = finisher;
			this.characteristics = characteristics;
		}

		public CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Set<Characteristics> characteristics) {
			this(supplier, accumulator, combiner, castingIdentity(), characteristics);
		}

		@Override
		public BiConsumer<A, T> accumulator() {
			return accumulator;
		}

		@Override
		public Supplier<A> supplier() {
			return supplier;
		}

		@Override
		public BinaryOperator<A> combiner() {
			return combiner;
		}

		@Override
		public Function<A, R> finisher() {
			return finisher;
		}

		@Override
		public Set<Characteristics> characteristics() {
			return characteristics;
		}
	}

	/**
	 * Returns a {@code Collector} that accumulates the input elements into a new
	 * {@code Collection}, in encounter order. The {@code Collection} is created by
	 * the provided factory.
	 *
	 * @param <T>               the collection type of the input elements
	 * @param <C>               the type of the resulting {@code Collection}
	 * @param collectionFactory a {@code Supplier} which returns a new, empty
	 *                          {@code Collection} of the appropriate type
	 * @return a {@code Collector} which collects all the input elements into a
	 *         {@code Collection}, in encounter order
	 */
	public static <T, C extends Collection<T>> Collector<C, ?, C> combineCollection(Supplier<C> collectionFactory) {
		return new CollectorImpl<>(collectionFactory, Collection<T>::addAll, (r1, r2) -> {
			r1.addAll(r2);
			return r1;
		}, passthrough(), CH_ID);
	}

	/**
	 * Returns a {@code Collector} that accumulates the input list elements into a
	 * new {@code List}. There are no guarantees on the type, mutability,
	 * serializability, or thread-safety of the {@code List} returned; if more
	 * control over the returned {@code List} is required, use
	 *
	 * @param <T> the collection type of the input elements
	 * @return a {@code Collector} which collects all the input elements into a
	 *         {@code List}, in encounter order
	 */
	public static <T> Collector<List<T>, ?, List<T>> combineList() {
		return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, List<T>::addAll, (left, right) -> {
			left.addAll(right);
			return left;
		}, passthrough(), CH_ID);
	}

	/**
	 * Returns a {@code Collector} that accumulates the input list elements into a
	 * new {@code List}. There are no guarantees on the type, mutability,
	 * serializability, or thread-safety of the {@code List} returned; if more
	 * control over the returned {@code List} is required, use
	 *
	 * @param <T>      the type of the input elements
	 * @param <A>      the type of the output element
	 * @param consumer accumulator of collector
	 * @return a {@code Collector} which collects all the transformed elements into
	 *         a {@code List}, in encounter order
	 */
	public static <T, A> Collector<T, List<A>, List<A>> collectList(BiConsumer<List<A>, T> consumer) {
		return new CollectorImpl<>((Supplier<List<A>>) ArrayList::new, consumer, (left, right) -> {
			left.addAll(right);
			return left;
		}, passthrough(), CH_ID);
	}

}
