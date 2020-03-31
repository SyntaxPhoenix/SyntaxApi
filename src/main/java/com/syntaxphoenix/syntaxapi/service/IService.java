package com.syntaxphoenix.syntaxapi.service;

public interface IService {
	
	public default Class<? extends IService> getOwner() {
		return getClass();
	}
	
	public String getId();
	
}
