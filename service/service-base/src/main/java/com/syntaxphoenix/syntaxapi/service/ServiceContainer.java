package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ServiceContainer {

    private final ArrayList<IServiceValue> subscribed = new ArrayList<>();
    private final Object owner;

    ServiceContainer(Object owner) {
        this.owner = owner;
    }

    /*
     * 
     */

    public Object getOwner() {
        return owner;
    }

    /*
     * 
     */

    boolean isEmpty() {
        return subscribed.isEmpty();
    }

    boolean add(IServiceValue value) {
        return subscribed.add(value);
    }

    /*
     * 
     */

    public IServiceValue[] getValues(Class<? extends IService> service) {
        return filter(value -> value.getService().equals(service)).toArray(size -> new IServiceValue[size]);
    }

    public ServiceFieldValue getValue(Field field) {
        Optional<IServiceValue> option = filter(value -> {
            if (!value.isField()) {
                return false;
            }
            return value.asField().equals(field);
        }).findAny();
        return option.isPresent() ? (ServiceFieldValue) option.get() : null;
    }

    public ServiceMethodValue getValue(Method method) {
        Optional<IServiceValue> option = filter(value -> {
            if (!value.isMethod()) {
                return false;
            }
            return value.asMethod().equals(method);
        }).findAny();
        return option.isPresent() ? (ServiceMethodValue) option.get() : null;
    }

    /*
     * 
     */

    private Stream<IServiceValue> filter(Predicate<IServiceValue> predicate) {
        return subscribed.stream().filter(predicate);
    }

}
