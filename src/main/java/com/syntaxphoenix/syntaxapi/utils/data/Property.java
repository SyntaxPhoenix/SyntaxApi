package com.syntaxphoenix.syntaxapi.utils.data;
	
import java.io.Serializable;
	
/**	
 * @author Lauriichen
 *	
 */	
public class Property<E> implements Serializable {
	
	private static final long serialVersionUID = 8586167622486650403L;
	
	/*
	 * 
	 */
	
	private String key;
	private E value;
	
	public Property(String key, E value) {
		this.key = key;
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public <Z> Property<Z> tryParse(Class<Z> clz) {
		if(instanceOf(clz)) {
			return (Property<Z>) this;
		}
		return null;
	}
	
	public Property<String> parseString(){
		Property<String> output;
		if((output = tryParse(String.class)) == null) {
			output = new Property<String>(key, value.toString());
		}
		return output;
	}
	
	public <Z> boolean instanceOf(Class<Z> clz) {
		return clz.isInstance(value);
	}
	
	public boolean isSerializable() {
		return value.getClass().isAssignableFrom(Serializable.class);
	}
	
	public String getHolderKey() {
		return "%" + key + "%";
	}
	
	public String getKey() {
		return key;
	}
	
	public E getValue() {
		return value;
	}
	
	/*
	 * 
	 * Creation
	 * 
	 */
	
	public static Property<Object> createObject(String key, Object value) {
		return new Property<Object>(key, value);
	}
	
	public static <T> Property<T> create(String key, T value) {
		return new Property<T>(key, value);
	}
	
}
