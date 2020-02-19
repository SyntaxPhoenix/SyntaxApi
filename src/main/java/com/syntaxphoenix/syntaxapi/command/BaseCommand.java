package com.syntaxphoenix.syntaxapi.command;

/**
 * @author Lauriichen
 *
 */
public abstract class BaseCommand {
	
	protected abstract void execute(BaseInfo info, Arguments arguments);

}
