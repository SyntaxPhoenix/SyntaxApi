package com.syntaxphoenix.syntaxapi.command;

/**
 * @author Lauriichen
 *
 */
public enum ArgumentType {
	
	STRING(ArgumentSuperType.TEXT), 
	INTEGER(ArgumentSuperType.NUMBER), 
	FLOAT(ArgumentSuperType.NUMBER), 
	LONG(ArgumentSuperType.NUMBER), 
	DOUBLE(ArgumentSuperType.NUMBER), 
	BIG_INTEGER(ArgumentSuperType.NUMBER), 
	LIST(ArgumentSuperType.COLLECTION),
	ARRAY(ArgumentSuperType.COLLECTION);
	
	/**
	 * 
	 */
	
	private final ArgumentSuperType superType;
	
	/**
	 * 
	 */
	private ArgumentType(ArgumentSuperType superType) {
		this.superType = superType;
	}
	
	
	public ArgumentSuperType getSuperType() {
		return superType;
	}
	
}
