package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface IServiceValue {

	public ValueType getType();

	public default boolean isMethod() {
		return getType() == ValueType.METHOD;
	}

	public default boolean isField() {
		return getType() == ValueType.FIELD;
	}

	public default Method asMethod() {
		if (isMethod())
			return (Method) getObject();
		return null;
	}

	public default Field asField() {
		if (isField())
			return (Field) getObject();
		return null;
	}

	public AccessibleObject getObject();

	public SubscribeService getAnnotation();

	public ServicePriority getPriority();

	public Class<? extends IService> getService();

	public Object getOwnerInstance();

	public Class<? extends Object> getOwner();

}
