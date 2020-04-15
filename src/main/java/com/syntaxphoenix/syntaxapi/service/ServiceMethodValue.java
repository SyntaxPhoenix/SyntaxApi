package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Method;

import com.syntaxphoenix.syntaxapi.event.EventPriority;

public class ServiceMethodValue implements IServiceValue {
	
	private final Object instance;
	private final Class<? extends Object> owner;
	
	private final Method method;
	private final SubscribeService annotation;
	
	public ServiceMethodValue(Class<? extends Object> owner, Method method) {
		this(owner, method, null);
	}
	
	public ServiceMethodValue(Class<? extends Object> owner, Method method, Object instance) {
		this.owner = owner;
		this.instance = instance;
		
		this.method = method;
		this.annotation = method.getAnnotation(SubscribeService.class);
	}
	
	/*
	 * 
	 */
	
	@Override
	public Class<? extends Object> getOwner() {
		return owner;
	}
	
	@Override
	public Object getOwnerInstance() {
		return instance;
	}
	
	/*
	 * 
	 */
	
	@Override
	public ValueType getType() {
		return ValueType.METHOD;
	}
	
	@Override
	public Method asMethod() {
		return method;
	}
	
	@Override
	public Method getObject() {
		return method;
	}
	
	@Override
	public SubscribeService getAnnotation() {
		return annotation;
	}
	
	@Override
	public EventPriority getPriority() {
		return annotation.priority();
	}
	
	@Override
	public Class<? extends IService> getService() {
		return annotation.service();
	}

}