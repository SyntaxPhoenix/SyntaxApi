package com.syntaxphoenix.syntaxapi.utils.position.grid;

import java.util.ArrayList;
import java.util.Optional;

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

	public int getHeighestX() {
		if (rows.isEmpty())
			return 0;
		int heighest = 0;
		int buffer;
		for (GridRow<V> row : rows)
			if ((buffer = row.getX()) > heighest)
				heighest = buffer;
		return heighest;
	}

	public int getLowestX() {
		if (rows.isEmpty())
			return 0;
		int lowest = 0;
		int buffer;
		for (GridRow<V> row : rows)
			if ((buffer = row.getX()) < lowest)
				lowest = buffer;
		return lowest;
	}

	public int getHeighestZ() {
		if (rows.isEmpty())
			return 0;
		int heighest = 0;
		int buffer;
		for (GridRow<V> row : rows)
			if ((buffer = row.getHeighestZ()) > heighest)
				heighest = buffer;
		return heighest;
	}

	public int getLowestZ() {
		if (rows.isEmpty())
			return 0;
		int lowest = 0;
		int buffer;
		for (GridRow<V> row : rows)
			if ((buffer = row.getLowestZ()) < lowest)
				lowest = buffer;
		return lowest;
	}
	
	/*
	 * 
	 */

	public int getWidth() {
		if (rows.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for (GridRow<V> row : rows) {
			int depth = row.getX();
			if (depth > heighest) {
				heighest = depth;
			} else if (depth < lowest) {
				lowest = depth;
			}
		}
		return Math.abs(lowest - heighest);
	}

	public int getDepth() {
		if (rows.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for (GridRow<V> row : rows) {
			int depth = row.getDepth();
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
		rows.forEach(row -> row.clear());
		rows.clear();
	}

	public GridLayer<V> set(int x, int z, V value) {
		if(value == null)
			return this;
		Optional<GridRow<V>> option = getOptionalRow(x);
		GridRow<V> row;
		if (!option.isPresent()) {
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
		Optional<GridRow<V>> option = getOptionalRow(x);
		if (option.isPresent()) {
			rows.remove(option.get());
		}
		return this;
	}

	public GridLayer<V> remove(int x, int z) {
		Optional<GridRow<V>> option = getOptionalRow(x);
		if (option.isPresent()) {
			option.get().remove(z);
		}
		return this;
	}

	public boolean contains(int x) {
		return getOptionalRow(x).isPresent();
	}

	public boolean contains(int x, int z) {
		Optional<GridRow<V>> row = getOptionalRow(x);
		return row.isPresent() ? row.get().contains(z) : false;
	}

	/*
	 * 
	 */

	public Optional<GridRow<V>> getOptionalRow(int x) {
		return rows.stream().filter(row -> row.getX() == x).findFirst();
	}

	public Optional<GridValue<V>> getOptionalValue(int x, int z) {
		Optional<GridRow<V>> option = getOptionalRow(x);
		if (option.isPresent())
			return option.get().getOptionalValue(z);
		return Optional.empty();
	}

	public GridRow<V> getRow(int x) {
		return getOptionalRow(x).orElse(null);
	}

	public GridValue<V> getValue(int x, int z) {
		return getOptionalValue(x, z).orElse(null);
	}

}
