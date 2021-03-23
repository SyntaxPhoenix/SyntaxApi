package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;

@FunctionalInterface
public interface JsonContentSerializer extends ContentSerializer {

    @Override
    default String process(RequestData<?> parameters) {
        Object value = parameters.getValue();
        return value instanceof JsonObject ? process((JsonObject) value) : null;
    }

    public String process(JsonObject object);

}
