package com.syntaxphoenix.syntaxapi.service;

import com.syntaxphoenix.syntaxapi.utils.general.Status;

public interface IService {
	
	public default Class<? extends IService> getOwner() {
		return getClass();
	}
	
	public String getId();
	
	public Status execute(ServiceManager manager);
	
}
