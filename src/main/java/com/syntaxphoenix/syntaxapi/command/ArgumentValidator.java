package com.syntaxphoenix.syntaxapi.command;

import java.util.List;

/**
 * @author Lauriichen
 *
 */
public abstract class ArgumentValidator {

	public static final ArgumentValidator DEFAULT = new DefaultArgumentValidator();

	/**
	 * @param String Array
	 * @return Argument List
	 */
	public abstract List<BaseArgument> process(String... rawArguments);
	
	/**
	 * @param Argument Array
	 * @return String Array
	 */
	public abstract String[] asStringArray(BaseArgument... arguments);

}
