package com.syntaxphoenix.syntaxapi.event;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface EventHandler {

	EventPriority priority() default EventPriority.NORMAL;

	boolean ignoreCancel() default false;

}
