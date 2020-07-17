package com.syntaxphoenix.syntaxapi.utils.position.grid;

public final class GridValue<V> {
	
	private int z;
	private V value;
	
	/*
	 * 
	 */
	
	public int getZ() {
		return z;
	}
	
	protected GridValue<V> setZ(int z) {
		this.z = z;
		return this;
	}
	
	/*
	 * 
	 */
	
	public V getValue() {
		return value;
	}
	
	protected GridValue<V> setValue(V value) {
		if(value == null)
			return this;
		this.value = value;
		return this;
	}
	
}
