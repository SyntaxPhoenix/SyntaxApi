package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Field;

import com.syntaxphoenix.syntaxapi.event.EventPriority;

public class ServiceFieldValue implements IServiceValue {
	
	private final Object instance;
	private final Class<? extends Object> owner;
	
	private final Field field;
	private final SubscribeService annotation;
	
	public ServiceFieldValue(Class<? extends Object> owner, Field field) {
		this(owner, field, null);
	}
	
	public ServiceFieldValue(Class<? extends Object> owner, Field field, Object instance) {
		this.owner = owner;
		this.instance = instance;
		
		this.field = field;
		this.annotation = field.getAnnotation(SubscribeService.class);
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
		return ValueType.FIELD;
	}
	
	@Override
	public Field asField() {
		return field;
	}
	
	@Override
	public Field getObject() {
		return field;
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
