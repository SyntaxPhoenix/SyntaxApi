package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;
import java.util.Iterator;

import com.syntaxphoenix.syntaxapi.exception.ObjectLockedException;

public class Ranges implements Iterable<BaseArgumentRange> {

	private final ArrayList<BaseArgumentRange> ranges;
	private boolean locked = false;

	public Ranges() {
		this.ranges = new ArrayList<>();
	}

	public Ranges(ArrayList<BaseArgumentRange> ranges) {
		this.ranges = ranges;
	}

	public int count() {
		return ranges.size();
	}

	public BaseArgumentRange get(int position) {
		if (position < 1) {
			throw negativeOrZero();
		}
		if (position > count()) {
			throw outOfBounce(position);
		}
		return ranges.get(position - 1);
	}

	public void add(BaseArgumentRange argument, int position) {
		if (locked) {
			throw locked();
		}
		if (position < 1) {
			throw negativeOrZero();
		}
		if (argument == null) {
			return;
		}
		ranges.add(position - 1, argument);
	}

	public void add(BaseArgumentRange argument) {
		if (locked) {
			throw locked();
		}
		if (argument == null) {
			return;
		}
		ranges.add(argument);
	}

	public Class<?> getInputType(int position) {
		return get(position).getInputType();
	}

	public RangeType getType(int position) {
		return get(position).getType();
	}
	
	public RangeSuperType getSuperType(int position) {
		return getType(position).getSuperType();
	}

	protected boolean isLocked() {
		return locked;
	}

	protected void setLocked(boolean locked) {
		this.locked = locked;
	}

	/*
	 * 
	 */

	@Override
	public String toString() {
		return toString(ArgumentSerializer.DEFAULT);
	}

	public String toString(ArgumentSerializer serializer) {
		return toString(ArgumentRangeSerializer.DEFAULT, serializer);
	}

	public String toString(ArgumentRangeSerializer serializer) {
		return toString(serializer, ArgumentSerializer.DEFAULT);
	}

	public String toString(ArgumentRangeSerializer range, ArgumentSerializer argument) {
		return null;
	}

	/*
	 * Exception Construction
	 */

	private IllegalArgumentException negativeOrZero() {
		return new IllegalArgumentException("Bound must be positive!");
	}

	private IndexOutOfBoundsException outOfBounce(int position) {
		return new IndexOutOfBoundsException("Index: " + position + " - Size: " + count());
	}

	private ObjectLockedException locked() {
		return new ObjectLockedException("Cannot edit a locked object!");
	}

	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public Iterator<BaseArgumentRange> iterator() {
		return ranges.iterator();
	}

}