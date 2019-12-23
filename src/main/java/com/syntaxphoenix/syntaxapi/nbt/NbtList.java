package com.syntaxphoenix.syntaxapi.nbt;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.syntaxphoenix.syntaxapi.utils.java.Lists;

/**
 * The {@code TAG_List} tag.
 */
public final class NbtList<T extends NbtTag> extends NbtAbstractList<T> {
    
    private NbtType type;
    
	private NbtList(NbtType type, List<T> list) {
    	addAll(list);
    	this.type = type;
    }
    
	public NbtList(NbtType type) {
    	this.type = type;
    }

    /**
     * Creates the list with a type and a series of elements.
     *
     * @param array the value of the tag
     */
	public NbtList(List<T> list) {
    	if(list != null && !list.isEmpty()) {
    		addAll(list);
    		type = list.get(0).getType();
    	}
    }
    
	/**
     * Creates the list with a type and a series of elements.
     *
     * @param array the value of the tag
     */
    @SuppressWarnings("unchecked")
	public NbtList(T... array) {
    	if(array.length == 0) {
    		return;
    	}
        addAll(Lists.asList(array));
        type = array[0].getType();
    }
    
    /**
     * Creates an empty list with a type.
     */
    public NbtList() {
    	
    }
    
    // GTTTTRS
    
    @Override
    public boolean add(T element) {
    	if(element != null) {
    		if(type == null) {
    			type = element.getType();
    		}
    	}
    	return super.add(element);
    }
    
    @Override
    public void add(int index, T element) {
    	if(element != null) {
    		if(type == null) {
    			type = element.getType();
    		}
    	}
    	super.add(index, element);
    }
    
    /**
     * Returns the size of this list.
     *
     * @return the size of this list
     */
    public int size() {
        return list.size();
    }
    
    @Override
    public NbtType getType() {
        return NbtType.LIST;
    }

    /**
     * Gets the type of elements in this list.
     *
     * @return The type of elements in this list.
     */
    public NbtType getElementType() {
        return type != null ? type : NbtType.COMPOUND;
    }

    @Override
    public String toMSONString() {
        StringBuilder builder = new StringBuilder("[");
        Iterator<T> iter = iterator();
        
        boolean first = true;
        while (iter.hasNext()) {
            if (first) first = false;
            else builder.append(',');
            builder.append(iter.next().toMSONString());
        }
        
        return builder.append("]").toString();
    }

	public static NbtList<?> createFromType(NbtType type) {
		return new NbtList<>(type);
	}

	@SuppressWarnings("unchecked")
	public static NbtList<?> createFromTypeAndFill(NbtType type, Collection<? extends NbtTag> values) {
		NbtList<NbtTag> list = (NbtList<NbtTag>) createFromType(type);
		list.addAll(values);
		return list;
	}

	@Override
	public NbtList<T> clone() {
		return new NbtList<T>(type, list);
	}

	@Override
	public NbtAbstractList<T> subList(int fromIndex, int toIndex) {
		return new NbtList<T>(type, list.subList(fromIndex, toIndex));
	}
    
}
