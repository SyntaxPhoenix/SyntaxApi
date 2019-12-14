package com.syntaxphoenix.syntaxapi.command;

import java.util.List;

/**
 * @author Lauriichen
 *
 */
public class DefaultArgumentValidator extends ArgumentValidator {

	@Override
	public List<BaseArgument> process(String... rawArguments) {
		return null;
	}

	@Override
	public String[] asStringArray(BaseArgument... arguments) {
		return null;
	}

}
