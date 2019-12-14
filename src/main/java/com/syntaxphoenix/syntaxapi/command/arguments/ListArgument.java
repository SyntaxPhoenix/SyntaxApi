package com.syntaxphoenix.syntaxapi.command.arguments;

import java.util.ArrayList;

import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class ListArgument<E> extends BaseArgument {
	
	private ArrayList<E> list;
	
	@Override
	public ArgumentType getType() {
		return ArgumentType.LIST;
	}
	
	@Override
	public Object asObject() {
		return list;
	}
	
	public ArrayList<E> getValue() {
		return list;
	}

}
