package com.syntaxphoenix.syntaxapi.command;

/**
 * @author Lauriichen
 *
 */
public abstract class BaseCommand {

	public abstract void execute(BaseInfo info, Arguments arguments);

	public BaseCompletion complete(BaseInfo info, Arguments arguments) {
		return null;
	}

}
