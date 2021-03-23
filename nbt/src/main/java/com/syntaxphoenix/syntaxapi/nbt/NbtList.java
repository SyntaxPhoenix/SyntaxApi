package com.syntaxphoenix.syntaxapi.nbt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
     * @param list the value of the tag
     */
    public NbtList(List<T> list) {
        if (list != null && !list.isEmpty()) {
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
        if (array.length == 0) {
            return;
        }
        addAll(Arrays.asList(array));
        type = array[0].getType();
    }

    /**
     * Creates an empty list with a type.
     */
    public NbtList() {

    }

    /**
     * Add a Tag to the list
     * 
     * @param element - tag that gets added
     * 
     * @return if it had the same type or not
     */
    @SuppressWarnings("unchecked")
    public boolean addTag(NbtTag element) {
        if (element != null) {
            if (type == null) {
                type = element.getType();
            } else if (element.getType() != type) {
                return false;
            }
        }
        return super.add((T) element);
    }

    /**
     * Add a Tag to the list at an specific index
     * 
     * @param index   - index at which the element should be inserted
     * @param element - element to be inserted
     * 
     * @return if it had the same type or not
     */
    @SuppressWarnings("unchecked")
    public boolean addTag(int index, NbtTag element) {
        if (element != null) {
            if (type == null) {
                type = element.getType();
            } else if (element.getType() != type) {
                return false;
            }
        }
        super.add(index, (T) element);
        return true;
    }

    /**
     * Add a Tag to the list
     * 
     * @param element - element that gets added
     * 
     * @return if it had the same type or not
     */
    @Override
    public boolean add(T element) {
        if (element != null) {
            if (type == null) {
                type = element.getType();
            } else if (element.getType() != type) {
                return false;
            }
        }
        return super.add(element);
    }

    /**
     * Add a Tag on a specific Index
     * 
     * @param index   - index at which the element should be inserted
     * @param element - element to be inserted
     */
    @Override
    public void add(int index, T element) {
        if (element != null) {
            if (type == null) {
                type = element.getType();
            } else if (element.getType() != type) {
                return;
            }
        }
        super.add(index, element);
    }

    /**
     * Adds all Tags of the collection into the list if they have the same type
     * 
     * @param collection - that should be added
     * 
     * @return if all values have been added or not
     */
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        int added = 0;
        for (NbtTag tag : collection) {
            if (addTag(tag)) {
                added++;
            }
        }
        return added == collection.size();
    }

    /**
     * Adds all Tags of the collection into the list starting at a specific index if
     * they have the same type
     * 
     * @param index      - index where the values should be inserted
     * @param collection - that should be inserted
     * 
     * @return if all values have been added or not
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        int added = 0;
        for (NbtTag tag : collection) {
            if (addTag(index, tag)) {
                added++;
                index++;
            }
        }
        return added == collection.size();
    }

    /**
     * Adds all Tags of the collection into the list if they have the same type
     * 
     * @param collection that should be added
     */
    public void addAllTags(Collection<NbtTag> collection) {
        if (collection.isEmpty()) {
            return;
        }
        for (NbtTag tag : collection) {
            addTag(tag);
        }
    }

    /**
     * Returns the size of this list.
     *
     * @return the size of this list
     */
    @Override
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
            if (first) {
                first = false;
            } else {
                builder.append(',');
            }
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

    @SuppressWarnings("unchecked")
    @Override
    public NbtList<T> clone() {
        if (list.isEmpty()) {
            return new NbtList<T>(type);
        }
        List<T> list = new ArrayList<>();
        for (T tag : this.list) {
            list.add((T) tag.clone());
        }
        return new NbtList<T>(type, list);
    }

    @Override
    public NbtList<T> subList(int fromIndex, int toIndex) {
        return new NbtList<T>(type, list.subList(fromIndex, toIndex));
    }

}
