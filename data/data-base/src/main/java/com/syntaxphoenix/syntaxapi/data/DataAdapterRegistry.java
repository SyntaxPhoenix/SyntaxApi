package com.syntaxphoenix.syntaxapi.data;

import java.util.HashMap;
import java.util.function.Function;

public abstract class DataAdapterRegistry<B> {

	private final HashMap<Class<?>, DataAdapter<?, B, B>> adapters = new HashMap<>();
	private final Function<Class<?>, DataAdapter<?, B, B>> adapterCreator;

	public DataAdapterRegistry(Function<Class<?>, DataAdapter<?, B, B>> adapterCreator) {
		this.adapterCreator = adapterCreator;
	}

	protected abstract <I, R extends B> DataAdapter<I, R, B> createAdapter(Class<I> primitiveType, Class<R> resultType,
			Function<I, R> builder, Function<R, I> extractor);

	public <T> B wrap(Class<T> type, T value) {
		DataAdapter<?, B, B> adapter = adapters.computeIfAbsent(type, adapterCreator);
		return adapter == null ? null : adapter.build(value);
	}

	public <T> T extract(Class<T> type, B base) throws ClassCastException, IllegalArgumentException {
		DataAdapter<?, B, B> adapter = this.adapters.computeIfAbsent(type, adapterCreator);
		if (adapter == null || !adapter.isInstance(base))
			return null;
		Object foundValue = adapter.extract(base);
		return type.isInstance(foundValue) ? type.cast(foundValue) : null;
	}

	public <T> boolean isInstanceOf(Class<T> type, B base) {
		DataAdapter<?, B, B> adapter = adapters.computeIfAbsent(type, adapterCreator);
		return adapter == null ? null : adapter.isInstance(base);
	}

	public boolean hasAdapter(Class<?> type) {
		return adapters.containsKey(type);
	}
	
}
