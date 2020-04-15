package com.syntaxphoenix.syntaxapi.logging.color;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class LogTypeMap {
	
	private final ArrayList<LogType> types = new ArrayList<>();
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public void register(final LogType type) {
		if(contains(type)) {
			return;
		}
		types.add(type);
	}
	
	public void override(final LogType type) {
		deleteById(type.getId());
		types.add(type);
	}
	
	public void delete(final LogType type) {
		if(contains(type)) {
			types.remove(type);
		} else {
			deleteById(type.getId());
		}
	}
	
	public void deleteById(final String id) {
		Optional<LogType> option = tryGetById(id);
		if(option.isPresent()) {
			delete(option.get());
		}
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public LogType getById(String id) {
		return tryGetById(id).get();
	}
	
	public LogType getByName(String name) {
		return tryGetById(name).get();
	}
	
	public Optional<LogType> tryGetById(String id) {
		return tryGet(type -> type.getId().equals(id));
	}
	
	public Optional<LogType> tryGetByName(String name) {
		return tryGet(type -> type.getName().equals(name));
	}
	
	public Optional<LogType> tryGet(Predicate<LogType> predicate) {
		return types.stream().filter(predicate).findFirst();
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	public boolean contains(LogType type) {
		return type == null ? false : types.contains(type);
	}
	
	public boolean containsId(String id) {
		return tryGetById(id).isPresent();
	}
	
	public boolean containsName(String name) {
		return tryGetByName(name).isPresent();
	}

}
