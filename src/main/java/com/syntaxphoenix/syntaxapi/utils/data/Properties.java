package com.syntaxphoenix.syntaxapi.utils.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Lauriichen
 *
 */
public abstract class Properties implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5806508173901340453L;
	
	/**
	 * 
	 */
	
	private final ArrayList<Property<?>> properties = new ArrayList<>();
	
	public void setProperties(Property<?>... properties) {
		clearProperties();
		for(Property<?> property : properties) {
			setProperty(property);
		}
	}
	
	public void setProperty(Property<?> property) {
		Property<?> remove;
		if((remove = findProperty(property.getKey())) != null) {
			properties.remove(remove);
		}
		properties.add(property);
	}
	
	public void addProperties(Property<?>... properties) {
		for(Property<?> property : properties) {
			addProperty(property);
		}
	}
	
	public void addProperty(Property<?> property) {
		if(!containsProperty(property.getKey())) {
			properties.add(property);
		}
	}

	public void removeProperties(String... keys) {
		for(String key : keys) {
			removeProperty(key);
		}
	}

	public boolean removeProperty(String key) {
		Optional<Property<?>> optional = properties.stream().filter(property -> property.getKey().equals(key)).findFirst();
		if (optional.isPresent()) {
			return properties.remove(optional.get());
		}
		return false;
	}
	
	public Property<?> findProperty(String key) {
		Optional<Property<?>> optional = properties.stream().filter(property -> property.getKey().equals(key)).findFirst();
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public ArrayList<Property<?>> findProperties(String... keys) {
		ArrayList<Property<?>> properties = new ArrayList<>();
		for(String key : keys) {
			Property<?> property;
			if((property = findProperty(key)) != null) {
				properties.add(property);
			}
		}
		return properties;
	}
	
	public void clearProperties() {
		properties.clear();
	}
	
	public boolean containsProperty(String key) {
		return properties.stream().filter(property -> property.getKey().equals(key)).findFirst().isPresent();
	}

	public int getPropertyCount() {
		return properties.size();
	}

	public Property<?>[] getProperties() {
		return properties.toArray(new Property<?>[0]);
	}

}
