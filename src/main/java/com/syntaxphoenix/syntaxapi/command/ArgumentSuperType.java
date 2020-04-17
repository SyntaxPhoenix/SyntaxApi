package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Lauriichen
 *
 */
public enum ArgumentSuperType {
	
	TEXT, 
	STATE,
	OBJECT,
	NUMBER, 
	COLLECTION;
	
	/**
	 * 
	 */
	
	public ArgumentType[] getSubTypes() {
		Collection<ArgumentType> types = new ArrayList<>();
		for(ArgumentType type : ArgumentType.values()) {
			if(type.getSuperType().equals(this)) {
				types.add(type);
			}
		}
		return types.toArray(new ArgumentType[0]);
	}
	
}
