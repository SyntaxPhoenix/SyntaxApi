package com.syntaxphoenix.syntaxapi.service;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({
    FIELD,
    METHOD
})
public @interface SubscribeService {

    public Class<? extends IService> service();

    public ServicePriority priority() default ServicePriority.NORMAL;

    public boolean returnsObject() default false;

    public Class<? extends Object> returnType() default Object.class;

}
