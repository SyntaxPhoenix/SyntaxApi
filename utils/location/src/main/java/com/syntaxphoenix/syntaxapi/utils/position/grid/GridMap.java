package com.syntaxphoenix.syntaxapi.utils.position.grid;

import java.util.ArrayList;
import java.util.Optional;

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

    public long getEntryCount() {
        if (layers.isEmpty()) {
            return 0;
        }
        long output = 0;
        for (GridLayer<V> layer : layers) {
            output = +layer.getEntryCount();
        }
        return output;
    }

    /*
     * 
     */

    public int getHeighestY() {
        if (layers.isEmpty()) {
            return 0;
        }
        int heighest = 0;
        int buffer;
        for (GridLayer<V> layer : layers) {
            if ((buffer = layer.getY()) > heighest) {
                heighest = buffer;
            }
        }
        return heighest;
    }

    public int getLowestY() {
        if (layers.isEmpty()) {
            return 0;
        }
        int lowest = 0;
        int buffer;
        for (GridLayer<V> layer : layers) {
            if ((buffer = layer.getY()) < lowest) {
                lowest = buffer;
            }
        }
        return lowest;
    }

    public int getHeighestX() {
        if (layers.isEmpty()) {
            return 0;
        }
        int heighest = 0;
        int buffer;
        for (GridLayer<V> layer : layers) {
            if ((buffer = layer.getHeighestX()) > heighest) {
                heighest = buffer;
            }
        }
        return heighest;
    }

    public int getLowestX() {
        if (layers.isEmpty()) {
            return 0;
        }
        int lowest = 0;
        int buffer;
        for (GridLayer<V> layer : layers) {
            if ((buffer = layer.getLowestX()) < lowest) {
                lowest = buffer;
            }
        }
        return lowest;
    }

    public int getHeighestZ() {
        if (layers.isEmpty()) {
            return 0;
        }
        int heighest = 0;
        int buffer;
        for (GridLayer<V> layer : layers) {
            if ((buffer = layer.getHeighestZ()) > heighest) {
                heighest = buffer;
            }
        }
        return heighest;
    }

    public int getLowestZ() {
        if (layers.isEmpty()) {
            return 0;
        }
        int lowest = 0;
        int buffer;
        for (GridLayer<V> layer : layers) {
            if ((buffer = layer.getLowestZ()) < lowest) {
                lowest = buffer;
            }
        }
        return lowest;
    }

    /*
     * 
     */

    public int getHeight() {
        if (layers.isEmpty()) {
            return 0;
        }
        int lowest = 0;
        int heighest = 0;
        for (GridLayer<V> layer : layers) {
            int depth = layer.getY();
            if (depth > heighest) {
                heighest = depth;
            } else if (depth < lowest) {
                lowest = depth;
            }
        }
        return Math.abs(lowest - heighest);
    }

    public int getWidth() {
        if (layers.isEmpty()) {
            return 0;
        }
        int lowest = 0;
        int heighest = 0;
        for (GridLayer<V> layer : layers) {
            int depth = layer.getWidth();
            if (depth > heighest) {
                heighest = depth;
            } else if (depth < lowest) {
                lowest = depth;
            }
        }
        return Math.abs(lowest - heighest);
    }

    public int getDepth() {
        if (layers.isEmpty()) {
            return 0;
        }
        int lowest = 0;
        int heighest = 0;
        for (GridLayer<V> layer : layers) {
            int depth = layer.getDepth();
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
        layers.forEach(layer -> layer.clear());
        layers.clear();
    }

    public GridMap<V> set(int[] array, V value) {
        if (array.length < 2) {
            return this;
        }
        return array.length == 2 ? set(array[0], array[1], value) : set(array[0], array[1], array[2], value);
    }

    public GridMap<V> set(int x, int z, V value) {
        return set(x, 0, z, value);
    }

    public GridMap<V> set(int y, int x, int z, V value) {
        if (value == null) {
            return this;
        }
        GridLayer<V> layer;
        if (type.is2D()) {
            if (layers.isEmpty()) {
                layer = new GridLayer<>();
                layer.setY(y);
                layers.add(layer);
            } else {
                layer = layers.get(0);
            }
        } else {
            Optional<GridLayer<V>> option = getOptionalLayer(y);
            if (!option.isPresent()) {
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
        Optional<GridLayer<V>> option = getOptionalLayer(y);
        if (option.isPresent()) {
            layers.remove(option.get());
        }
        return this;
    }

    public GridMap<V> remove(int y, int x) {
        Optional<GridLayer<V>> option = getOptionalLayer(y);
        if (option.isPresent()) {
            option.get().remove(x);
        }
        return this;
    }

    public GridMap<V> remove(int y, int x, int z) {
        Optional<GridLayer<V>> option = getOptionalLayer(y);
        if (option.isPresent()) {
            option.get().remove(x, z);
        }
        return this;
    }

    public boolean contains(int y) {
        return getOptionalLayer(y).isPresent();
    }

    public boolean contains(int y, int x) {
        Optional<GridLayer<V>> layer = getOptionalLayer(x);
        return layer.isPresent() ? layer.get().contains(x) : false;
    }

    public boolean contains(int y, int x, int z) {
        Optional<GridLayer<V>> layer = getOptionalLayer(x);
        return layer.isPresent() ? layer.get().contains(x, z) : false;
    }

    /*
     * 
     */

    public Optional<GridLayer<V>> getOptionalLayer(int y) {
        return layers.stream().filter(layer -> layer.getY() == y).findFirst();
    }

    public Optional<GridRow<V>> getOptionalRow(int y, int x) {
        Optional<GridLayer<V>> option = getOptionalLayer(y);
        if (option.isPresent()) {
            return option.get().getOptionalRow(x);
        }
        return Optional.empty();
    }

    public Optional<GridValue<V>> getOptionalValue(int x, int z) {
        Optional<GridRow<V>> option = getOptionalRow(0, x);
        if (option.isPresent()) {
            return option.get().getOptionalValue(x);
        }
        return Optional.empty();
    }

    public Optional<GridValue<V>> getOptionalValue(int y, int x, int z) {
        Optional<GridRow<V>> option = getOptionalRow(y, x);
        if (option.isPresent()) {
            return option.get().getOptionalValue(x);
        }
        return Optional.empty();
    }

    public Optional<GridValue<V>> getOptionalValue(int[] location) {
        if (location == null || location.length < 2) {
            return Optional.empty();
        }
        if (location.length == 2) {
            return getOptionalValue(location[0], location[1]);
        }
        return getOptionalValue(location[0], location[1], location[2]);
    }

    public GridLayer<V> getLayer(int y) {
        return getOptionalLayer(y).orElse(null);
    }

    public GridRow<V> getRow(int y, int x) {
        return getOptionalRow(y, x).orElse(null);
    }

    public GridValue<V> getValue(int x, int z) {
        return getOptionalValue(0, x, z).orElse(null);
    }

    public GridValue<V> getValue(int y, int x, int z) {
        return getOptionalValue(y, x, z).orElse(null);
    }

    public GridValue<V> getValue(int[] location) {
        return getOptionalValue(location).orElse(null);
    }

}
