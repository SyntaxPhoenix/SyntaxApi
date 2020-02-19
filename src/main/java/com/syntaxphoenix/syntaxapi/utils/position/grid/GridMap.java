package com.syntaxphoenix.syntaxapi.utils.position.grid;

import java.util.ArrayList;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.Numbers;

public class GridMap<V> {
	
	private final GridType type;
	
	public GridType getType() {
		return type;
	}
	
	/*
	 * 
	 */
	
	private ArrayList<GridLayer<V>> layers = new ArrayList<>(1);
	
	public GridMap(GridType type) {
		this.type = type;
	}
	
	/*
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public GridLayer<V>[] getLayers() {
		return layers.toArray(new GridLayer[0]);
	}
	
	/*
	 * 
	 */
	
	public int getHeight() {
		if(layers.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for(GridLayer<V> layer : layers) {
			int depth = layer.getY();
			if(depth > heighest) {
				heighest = depth;
			} else if(depth < lowest) {
				lowest = depth;
			}
		}
		return Numbers.toPlus(lowest - heighest).intValue();
	}
	
	public int getWidth() {
		if(layers.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for(GridLayer<V> layer : layers) {
			int depth = layer.getWidth();
			if(depth > heighest) {
				heighest = depth;
			} else if(depth < lowest) {
				lowest = depth;
			}
		}
		return Numbers.toPlus(lowest - heighest).intValue();
	}
	
	public int getDepth() {
		if(layers.isEmpty()) {
			return 0;
		}
		int lowest = 0;
		int heighest = 0;
		for(GridLayer<V> layer : layers) {
			int depth = layer.getDepth();
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
	
	public GridMap<V> set(int x, int z, V value) {
		return set(x, 0, z, value);
	}
	
	public GridMap<V> set(int y, int x, int z, V value) {
		GridLayer<V> layer;
		if(type.is2D()) {
			if(layers.isEmpty()) {
				layer = new GridLayer<>();
				layer.setY(y);
				layers.add(layer);
			} else {
				layer = layers.get(0);
			}
		} else { 
			Optional<GridLayer<V>> option = getLayer(y);
			if(!option.isPresent()) {
				layer = new GridLayer<>();
				layer.setY(y);
				layers.add(layer);
			} else {
				layer = option.get();
			}
		}
		layer.set(x, z, value);
		return this;
	}
	
	public GridMap<V> remove(int y) {
		Optional<GridLayer<V>> option = getLayer(y);
		if(option.isPresent()) {
			layers.remove(option.get());
		}
		return this;
	}
	
	
	public GridMap<V> remove(int y, int x) {
		Optional<GridLayer<V>> option = getLayer(y);
		if(option.isPresent()) {
			option.get().remove(x);
		}
		return this;
	}
	
	public GridMap<V> remove(int y, int x, int z) {
		Optional<GridLayer<V>> option = getLayer(y);
		if(option.isPresent()) {
			option.get().remove(x, z);
		}
		return this;
	}
	
	
	public boolean contains(int y) {
		return getLayer(y).isPresent();
	}
	
	public boolean contains(int y, int x) {
		Optional<GridLayer<V>> layer = getLayer(x);
		return layer.isPresent() ? layer.get().contains(x) : false;
	}
	
	public boolean contains(int y, int x, int z) {
		Optional<GridLayer<V>> layer = getLayer(x);
		return layer.isPresent() ? layer.get().contains(x, z) : false;
	}
	
	/*
	 * 
	 */
	
	private Optional<GridLayer<V>> getLayer(int y) {
		return layers.stream().filter(layer -> layer.getY() == y).findFirst();
	}

}
