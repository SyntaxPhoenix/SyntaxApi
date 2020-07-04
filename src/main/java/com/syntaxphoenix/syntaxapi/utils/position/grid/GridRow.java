package com.syntaxphoenix.syntaxapi.utils.position.grid;

import java.util.ArrayList;
import java.util.Optional;

public final class GridRow<V> {

	private final ArrayList<GridValue<V>> values = new ArrayList<>();
	private int x;

	/*
	 * 
	 */

	@SuppressWarnings("unchecked")
	public GridValue<V>[] getValues() {
		return values.toArray(new GridValue[0]);
	}

	/*
	 * 
	 */

	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	/*
	 * 
	 */

	public int getHeighestZ() {
		if (values.isEmpty())
			return 0;
		int heighest = 0;
		int buffer;
		for (GridValue<V> value : values)
			if ((buffer = value.getZ()) > heighest)
				heighest = buffer;
		return heighest;
	}

	public int getLowestZ() {
		if (values.isEmpty())
			return 0;
		int lowest = 0;
		int buffer;
		for (GridValue<V> value : values)
			if ((buffer = value.getZ()) < lowest)
				lowest = buffer;
		return lowest;
	}

	/*
	 * 
	 */

	public int getDepth() {
		if (values.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for (GridValue<V> value : values) {
			int depth = value.getZ();
			if (depth > heighest) {
				heighest = depth;
			} else if (depth < lowest) {
				lowest = depth;
			}
		}
		return Math.abs(lowest - heighest);
	}

	/*
	 * 
	 */

	public void clear() {
		values.clear();
	}

	public GridRow<V> set(int z, V value) {
		if(value == null)
			return this;
		Optional<GridValue<V>> option = getOptionalValue(z);
		GridValue<V> grid;
		if (!option.isPresent()) {
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
		Optional<GridValue<V>> option = getOptionalValue(z);
		if (option.isPresent()) {
			values.remove(option.get());
		}
		return this;
	}

	public boolean contains(int z) {
		return getOptionalValue(z).isPresent();
	}

	public boolean contains(V value) {
		return values.stream().anyMatch(grid -> grid.getValue().equals(value));
	}

	/*
	 * 
	 */

	public Optional<GridValue<V>> getOptionalValue(int z) {
		return values.stream().filter(value -> value.getZ() == z).findFirst();
	}

	public GridValue<V> getValue(int z) {
		return getOptionalValue(z).orElse(null);
	}

}
