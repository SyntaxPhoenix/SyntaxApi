package com.syntaxphoenix.syntaxapi.service;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import com.syntaxphoenix.syntaxapi.event.EventPriority;

import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface SubscribeService {
	
	public Class<? extends IService> service();
	
	public EventPriority priority() default EventPriority.NORMAL;
	
	public boolean returnsObject() default false;
	public Class<? extends Object> returnType() default Object.class;

}
