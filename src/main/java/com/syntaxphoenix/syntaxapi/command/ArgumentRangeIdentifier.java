package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;

public abstract class ArgumentRangeIdentifier {

	public static final ArgumentRangeIdentifier DEFAULT = new DefaultArgumentRangeIdentifier();

	/**
	 * @param String Array
	 * @return Argument List
	 */
	public abstract ArrayList<BaseArgumentRange> process(String... rawRanges);
	
	/**
	 * @param Argument Array
	 * @return String Array
	 */
	public abstract String[] asStringArray(BaseArgumentRange... ranges);

}
