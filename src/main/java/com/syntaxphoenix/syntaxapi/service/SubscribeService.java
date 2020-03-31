package com.syntaxphoenix.syntaxapi.service;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.syntaxphoenix.syntaxapi.event.EventPriority;

import java.lang.annotation.Target;

@Target({ FIELD, METHOD })
public @interface SubscribeService {
	
	public Class<? extends IService> service();
	public EventPriority priority() default EventPriority.NORMAL;

}
