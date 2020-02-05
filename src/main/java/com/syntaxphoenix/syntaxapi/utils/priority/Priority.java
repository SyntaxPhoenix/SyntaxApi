package com.syntaxphoenix.syntaxapi.utils.priority;

public interface Priority<C> {
	
	public boolean isHigher(Priority<C> priority);
	
	public boolean isSame(Priority<C> priority);
	
	public Priority<C> getDefault();

}
