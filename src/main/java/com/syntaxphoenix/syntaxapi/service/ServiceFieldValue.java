package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Field;

import com.syntaxphoenix.syntaxapi.event.EventPriority;

public class ServiceFieldValue implements IServiceValue {
	
	private final Field field;
	private final SubscribeService annotation;
	
	public ServiceFieldValue(Field field) {
		this.field = field;
		this.annotation = field.getAnnotation(SubscribeService.class);
	}
	
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
