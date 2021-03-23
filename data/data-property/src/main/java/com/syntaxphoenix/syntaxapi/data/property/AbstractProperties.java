package com.syntaxphoenix.syntaxapi.data.property;

import java.util.ArrayList;
import java.util.Optional;

public abstract class AbstractProperties {

    private final ArrayList<Property<?>> properties = new ArrayList<>();

    public void setProperties(Property<?>... properties) {
        clearProperties();
        for (Property<?> property : properties) {
            setProperty(property);
        }
    }

    public void setProperty(Property<?> property) {
        Property<?> remove;
        if ((remove = findProperty(property.getKey())) != null) {
            properties.remove(remove);
        }
        properties.add(property);
    }

    public void addProperties(Property<?>... properties) {
        for (Property<?> property : properties) {
            addProperty(property);
        }
    }

    public void addProperty(Property<?> property) {
        if (!containsProperty(property.getKey())) {
            properties.add(property);
        }
    }

    public void removeProperties(String... keys) {
        for (String key : keys) {
            removeProperty(key);
        }
    }

    public boolean removeProperty(String key) {
        if (properties.isEmpty()) {
            return false;
        }
        Optional<Property<?>> optional = properties.stream().filter(property -> property.getKey().equals(key)).findFirst();
        if (optional.isPresent()) {
            return properties.remove(optional.get());
        }
        return false;
    }

    public Property<?> findProperty(String key) {
        if (properties.isEmpty()) {
            return null;
        }
        Optional<Property<?>> optional = properties.stream().filter(property -> property.getKey().equals(key)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public ArrayList<Property<?>> findProperties(String... keys) {
        ArrayList<Property<?>> properties = new ArrayList<>();
        for (String key : keys) {
            Property<?> property;
            if ((property = findProperty(key)) != null) {
                properties.add(property);
            }
        }
        return properties;
    }

    public void clearProperties() {
        properties.clear();
    }

    public boolean containsProperty(String key) {
        return properties.stream().anyMatch(property -> property.getKey().equals(key));
    }

    public int getPropertyCount() {
        return properties.size();
    }

    public boolean hasProperties() {
        return !properties.isEmpty();
    }

    public Property<?>[] getProperties() {
        return properties.toArray(new Property<?>[0]);
    }

    /*
     * 
     */

//	@Override
//	public NbtCompound asNbt() {
//		NbtCompound compound = new NbtCompound();
//		if(properties.isEmpty()) {
//			return compound;
//		}
//		for(Property<?> property : properties) {
//			String key = property.getKey();
//			Object value = property.getValue();
//			if(property.instanceOf(Boolean.class)) {
//				compound.set(key, (boolean) value);
//			} else if(property.instanceOf(Byte.class)) {
//				compound.set(key, (byte) value);
//			} else if(property.instanceOf(Short.class)) {
//				compound.set(key, (short) value);
//			} else if(property.instanceOf(Integer.class)) {
//				compound.set(key, (int) value);
//			} else if(property.instanceOf(Long.class)) {
//				compound.set(key, (long) value);
//			} else if(property.instanceOf(Float.class)) {
//				compound.set(key, (float) value);
//			} else if(property.instanceOf(Double.class)) {
//				compound.set(key, (double) value);
//			} else if(property.instanceOf(BigInteger.class)) {
//				compound.set(key, (BigInteger) value);
//			} else if(property.instanceOf(BigDecimal.class)) {
//				compound.set(key, (BigDecimal) value);
//			} else if(property.instanceOf(String.class)) {
//				compound.set(key, (String) value);
//			} else if(property.instanceOf(byte[].class)) {
//				compound.set(key, (byte[]) value);
//			} else if(property.instanceOf(int[].class)) {
//				compound.set(key, (int[]) value);
//			} else if(property.instanceOf(long[].class)) {
//				compound.set(key, (long[]) value);
//			}
//		}
//		return compound;
//	}
//	
//	@Override
//	public void fromNbt(NbtCompound nbt) {
//		if(nbt.isEmpty()) {
//			return;
//		}
//		for(String key : nbt.getKeys()) {
//			addProperty(Property.create(key, nbt.get(key).getValue()));
//		}
//	}

}
