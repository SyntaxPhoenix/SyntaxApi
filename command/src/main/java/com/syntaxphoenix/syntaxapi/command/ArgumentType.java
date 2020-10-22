package com.syntaxphoenix.syntaxapi.command;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author Lauriichen
 *
 */

public enum ArgumentType {

	CUSTOM(ArgumentSuperType.OBJECT, Object.class),
	BOOLEAN(ArgumentSuperType.STATE, Boolean.class),
	STRING(ArgumentSuperType.TEXT, String.class),
	BYTE(ArgumentSuperType.NUMBER, Byte.class),
	SHORT(ArgumentSuperType.NUMBER, Short.class),
	INTEGER(ArgumentSuperType.NUMBER, Integer.class),
	FLOAT(ArgumentSuperType.NUMBER, Float.class),
	LONG(ArgumentSuperType.NUMBER, Long.class),
	DOUBLE(ArgumentSuperType.NUMBER, Double.class),
	BIG_DECIMAL(ArgumentSuperType.NUMBER, BigDecimal.class),
	BIG_INTEGER(ArgumentSuperType.NUMBER, BigInteger.class),
	LIST(ArgumentSuperType.COLLECTION, ArrayList.class),
	ARRAY(ArgumentSuperType.COLLECTION, Array.class);

	/**
	 * 
	 */

	private final ArgumentSuperType superType;
	private final Class<?> classType;

	/**
	 * 
	 */
	private ArgumentType(ArgumentSuperType superType, Class<?> classType) {
		this.superType = superType;
		this.classType = classType;
	}

	public ArgumentSuperType getSuperType() {
		return superType;
	}

	public Class<?> getClassType() {
		return classType;
	}

}
