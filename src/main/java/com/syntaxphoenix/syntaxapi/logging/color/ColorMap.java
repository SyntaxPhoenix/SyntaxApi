package com.syntaxphoenix.syntaxapi.logging.color;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class ColorMap {
	
	private final ArrayList<LogColorType> types = new ArrayList<>();
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public void register(final LogColorType type) {
		if(contains(type)) {
			return;
		}
		types.add(type);
	}
	
	public void override(final LogColorType type) {
		deleteById(type.getId());
		types.add(type);
	}
	
	public void delete(final LogColorType type) {
		if(contains(type)) {
			types.remove(type);
		} else {
			deleteById(type.getId());
		}
	}
	
	public void deleteById(final String id) {
		Optional<LogColorType> option = tryGetById(id);
		if(option.isPresent()) {
			delete(option.get());
		}
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public LogColorType getById(String id) {
		return tryGetById(id).get();
	}
	
	public LogColorType getByName(String name) {
		return tryGetById(name).get();
	}
	
	public Optional<LogColorType> tryGetById(String id) {
		return tryGet(type -> type.getId().equals(id));
	}
	
	public Optional<LogColorType> tryGetByName(String name) {
		return tryGet(type -> type.getName().equals(name));
	}
	
	public Optional<LogColorType> tryGet(Predicate<LogColorType> predicate) {
		return types.stream().filter(predicate).findFirst();
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public boolean contains(LogColorType type) {
		return type == null ? false : types.contains(type);
	}
	
	public boolean containsId(String id) {
		return tryGetById(id).isPresent();
	}
	
	public boolean containsName(String name) {
		return tryGetByName(name).isPresent();
	}

}
