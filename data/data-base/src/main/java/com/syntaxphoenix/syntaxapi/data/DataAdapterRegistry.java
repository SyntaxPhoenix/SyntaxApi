package com.syntaxphoenix.syntaxapi.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public abstract class DataAdapterRegistry<B> {

	private final ConcurrentHashMap<Class<?>, DataAdapter<?, B, B>> adapters = new ConcurrentHashMap<>();

	protected abstract <I, R extends B> DataAdapter<I, R, B> buildAdapter(Class<?> clazz);

	protected abstract <I, R extends B> DataAdapter<I, R, B> createAdapter(Class<I> primitiveType, Class<R> resultType, Function<I, R> builder,
		Function<R, I> extractor);

	public <T> B wrap(Class<T> type, T value) {
		DataAdapter<?, B, B> adapter = adapters.computeIfAbsent(Primitives.fromPrimitive(type), (clazz) -> buildAdapter(clazz));
		return adapter == null ? null : adapter.build(value);
	}

	public <T> T extract(Class<T> type, B base) throws ClassCastException, IllegalArgumentException {
		type = Primitives.fromPrimitive(type);
		DataAdapter<?, B, B> adapter = this.adapters.computeIfAbsent(type, (clazz) -> buildAdapter(clazz));
		if (adapter == null || !adapter.isInstance(base))
			return null;
		Object foundValue = adapter.extract(base);
		return type.isInstance(foundValue) ? type.cast(foundValue) : null;
	}

	public <T> boolean isInstanceOf(Class<T> type, B base) {
		DataAdapter<?, B, B> adapter = adapters.computeIfAbsent(Primitives.fromPrimitive(type), (clazz) -> buildAdapter(clazz));
		return adapter == null ? null : adapter.isInstance(base);
	}

	public boolean hasAdapter(Class<?> type) {
		return adapters.containsKey(Primitives.fromPrimitive(type));
	}

}
