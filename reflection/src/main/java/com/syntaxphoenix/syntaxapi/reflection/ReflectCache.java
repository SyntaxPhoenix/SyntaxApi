package com.syntaxphoenix.syntaxapi.reflection;

public class ReflectCache extends AbstractReflectCache<Reflect> {

    @Override
    protected Reflect create(Class<?> clazz) {
        return new Reflect(clazz);
    }

    @Override
    protected Reflect create(String path) {
        return new Reflect(path);
    }

}
