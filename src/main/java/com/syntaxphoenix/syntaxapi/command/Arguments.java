package com.syntaxphoenix.syntaxapi.command;

import java.util.Collections;
import java.util.List;

/**
 * @author Lauriichen
 *
 */
public class Arguments {
	
	private List<BaseArgument> arguments;
	
	public Arguments(List<BaseArgument> arguments) {
		this.arguments = Collections.unmodifiableList(arguments);
	}

	public int count() {
		return arguments.size();
	}
	
	public BaseArgument get(int position) {
		if(position < 1) {
			throw negativeOrZero();
		}
		if(position > count()) {
			throw outOfBounce(position);
		}
		return arguments.get(position - 1);
	}
	
	public ArgumentType getType(int position) {
		return get(position).getType();
	}
	
	public ArgumentSuperType getSuperType(int position) {
		return getType(position).getSuperType();
	}
	
	/**
	 * Exception Construction
	 */
	
	private IllegalArgumentException negativeOrZero() {
		return new IllegalArgumentException("Bound must be positive!");
	}
	
	private IndexOutOfBoundsException outOfBounce(int position) {
		return new IndexOutOfBoundsException("Index: " + position + " - Size: " + count());
	}
	
}
