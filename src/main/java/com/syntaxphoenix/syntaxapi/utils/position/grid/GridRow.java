package com.syntaxphoenix.syntaxapi.utils.position.grid;

import java.util.ArrayList;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.Numbers;

public final class GridRow<V> {
	
	private final ArrayList<GridValue<V>> values = new ArrayList<>();
	private int x;
	
	/*
	 * 
	 */
	
	public int getX() {
		return x;
	}
	
	protected void setX(int x) {
		this.x = x;
	}
	
	public int getDepth() {
		if(values.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for(GridValue<V> value : values) {
			int depth = value.getZ();
			if(depth > heighest) {
				heighest = depth;
			} else if(depth < lowest) {
				lowest = depth;
			}
		}
		return Numbers.toPlus(lowest - heighest).intValue();
	}
	
	/*
	 * 
	 */
	
	public GridRow<V> set(int z, V value) {
		Optional<GridValue<V>> option = getValue(z);
		GridValue<V> grid;
		if(!option.isPresent()) {
			grid = new GridValue<>();
			grid.setZ(z);
			values.add(grid);
		} else {
			grid = option.get();
		}
		grid.setValue(value);
		return this;
	}
	
	public GridRow<V> remove(int z) {
		Optional<GridValue<V>> option = getValue(z);
		if(option.isPresent()) {
			values.remove(option.get());
		}
		return this;
	}
	
	public boolean contains(int z) {
		return getValue(z).isPresent();
	}
	
	public boolean contains(V value) {
		return getValue(value).isPresent();
	}
	
	/*
	 * 
	 */
	
	private Optional<GridValue<V>> getValue(int z) {
		return values.stream().filter(value -> value.getZ() == z).findFirst();
	}
	
	private Optional<GridValue<V>> getValue(V value) {
		return values.stream().filter(grid -> grid.getValue().equals(value)).findFirst();
	}
	
}
