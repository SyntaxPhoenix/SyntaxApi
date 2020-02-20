package com.syntaxphoenix.syntaxapi.utils.position.grid;

import java.util.ArrayList;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.Numbers;

public final class GridLayer<V> {
	
	private final ArrayList<GridRow<V>> rows = new ArrayList<>();
	private int y;
	
	/*
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public GridRow<V>[] getRows() {
		return rows.toArray(new GridRow[0]);
	}
	
	/*
	 * 
	 */
	
	public int getY() {
		return y;
	}
	
	protected void setY(int y) {
		this.y = y;
	}
	
	/*
	 * 
	 */
	
	public int getWidth() {
		if(rows.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for(GridRow<V> row : rows) {
			int depth = row.getX();
			if(depth > heighest) {
				heighest = depth;
			} else if(depth < lowest) {
				lowest = depth;
			}
		}
		return Numbers.toPlus(lowest - heighest).intValue();
	}
	
	public int getDepth() {
		if(rows.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for(GridRow<V> row : rows) {
			int depth = row.getDepth();
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
	
	public void clear() {
		rows.forEach(row -> row.clear());
		rows.clear();
	}
	
	public GridLayer<V> set(int x, int z, V value) {
		Optional<GridRow<V>> option = getRow(x);
		GridRow<V> row;
		if(!option.isPresent()) {
			row = new GridRow<>();
			row.setX(x);
			rows.add(row);
		} else {
			row = option.get();
		}
		row.set(z, value);
		return this;
	}
	
	public GridLayer<V> remove(int x) {
		Optional<GridRow<V>> option = getRow(x);
		if(option.isPresent()) {
			rows.remove(option.get());
		}
		return this;
	}
	
	
	public GridLayer<V> remove(int x, int z) {
		Optional<GridRow<V>> option = getRow(x);
		if(option.isPresent()) {
			option.get().remove(z);
		}
		return this;
	}
	
	public boolean contains(int x) {
		return getRow(x).isPresent();
	}
	
	public boolean contains(int x, int z) {
		Optional<GridRow<V>> row = getRow(x);
		return row.isPresent() ? row.get().contains(z) : false;
	}
	
	/*
	 * 
	 */
	
	private Optional<GridRow<V>> getRow(int x) {
		return rows.stream().filter(row -> row.getX() == x).findFirst();
	}

}
