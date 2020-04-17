package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;

/**
 * @author Lauriichen
 *
 */
public abstract class ArgumentIdentifier {

	public static final ArgumentIdentifier DEFAULT = new DefaultArgumentIdentifier();

	/**
	 * @param String Array
	 * @return Argument List
	 */
	public abstract ArrayList<BaseArgument> process(String... rawArguments);

}
