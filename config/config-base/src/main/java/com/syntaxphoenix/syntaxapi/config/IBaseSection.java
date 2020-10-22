package com.syntaxphoenix.syntaxapi.config;

import java.util.Map;
import java.util.Set;

public interface IBaseSection {

	public Map<String, Object> getValues();

	public Set<String> getKeys();

	public String getName();

	public boolean isValid();

	public void clear();

	public boolean contains(String path);

	public <E> boolean isInstance(String path, E value);

	public <E> boolean isInstance(String path, Class<E> value);

	public <E> E check(String path, E value);

	public Object get(String path);

	public <E> E get(String path, Class<E> sample);

	public <E> E get(String path, E sample);

	public BaseSection getSection(String path);

	public BaseSection createSection(String path);

	public void set(String path, Object value);

	public void set(String key, String[] path, Object value);

	public SectionMap<String, Object> toMap();

	public void fromMap(SectionMap<String, Object> input);
}
