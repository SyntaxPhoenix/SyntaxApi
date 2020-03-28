package com.syntaxphoenix.syntaxapi.reflections;

import java.util.HashMap;
import java.util.Optional;

public abstract class AbstractReflectCache<R extends AbstractReflect> {
	
	protected final HashMap<String, R> cache = new HashMap<>();
	
	public void clear() {
		cache.values().forEach(ClassCache::uncache);
	}
	
	public Optional<R> get(String name) {
		return Optional.ofNullable(cache.get(name));
	}
	
	public boolean has(String name) {
		return cache.containsKey(name);
	}
	
	public R create(String name, String path) {
		if(has(name)) {
			return cache.get(name);
		}
		R reflect = create(path);
		cache.put(name, reflect);
		return reflect;
	}
	
	public R create(String name, Class<?> clazz) {
		if(has(name)) {
			return cache.get(name);
		}
		R reflect = create(clazz);
		cache.put(name, reflect);
		return reflect;
	}
	
	protected abstract R create(Class<?> clazz);
	
	protected abstract R create(String path);
	
}
