package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Method;

import com.syntaxphoenix.syntaxapi.event.EventPriority;

public class ServiceMethodValue implements IServiceValue {
	
	private final Method method;
	private final SubscribeService annotation;
	
	public ServiceMethodValue(Method method) {
		this.method = method;
		this.annotation = method.getAnnotation(SubscribeService.class);
	}
	
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