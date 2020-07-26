package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;

/**
 * @author Lauriichen
 *
 */
public abstract class ArgumentIdentifier {

	public static final ArgumentIdentifier DEFAULT = new DefaultArgumentIdentifier();

	/**
	 * @param rawArguments array of raw arguments
	 * @return list of complex arguments
	 */
	public abstract ArrayList<BaseArgument> process(String... rawArguments);

}
