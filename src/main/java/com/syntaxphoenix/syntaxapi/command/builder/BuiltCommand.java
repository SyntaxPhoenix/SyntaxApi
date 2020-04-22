package com.syntaxphoenix.syntaxapi.command.builder;

import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.BaseCommand;
import com.syntaxphoenix.syntaxapi.command.BaseCompletion;
import com.syntaxphoenix.syntaxapi.command.BaseInfo;
import com.syntaxphoenix.syntaxapi.command.DefaultCompletion;

public class BuiltCommand<E extends BaseInfo> extends BaseCommand {
	
	private final Class<E> infoType;

	BuiltCommand(CommandBuilder<E> commandBuilder) {
		this.infoType = commandBuilder.infoType();
	}

	@Override
	protected void execute(BaseInfo info, Arguments arguments) {
		if(!infoType.isInstance(info))
			return;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected BaseCompletion complete(BaseInfo info, Arguments arguments) {
		if(!infoType.isInstance(info))
			return null;
		return complete0((E) info, arguments);
	}
	
	/*
	 * 
	 */
	
	public void execute0(E info, Arguments arguments) {
		
	}
	
	public DefaultCompletion complete0(E info, Arguments arguments) {
		DefaultCompletion completion = new DefaultCompletion();
		return completion;
	}

}
