package com.syntaxphoenix.syntaxapi.command;

import java.util.Map;

public abstract class SerializationHelper {

    protected final ArgumentIdentifier identifier;
    protected final ArgumentSerializer serializer;

    public SerializationHelper(ArgumentIdentifier identifier, ArgumentSerializer serializer) {
        this.identifier = identifier;
        this.serializer = serializer;
    }

    public ArgumentIdentifier getIdentifier() {
        return identifier;
    }

    public ArgumentSerializer getSerializer() {
        return serializer;
    }

    public abstract Map<String, BaseArgument> serializeMap(String... arguments);

    public abstract String[] deserializeMap(Map<String, BaseArgument> map);

    public final ArgumentMap serialize(String... arguments) {
        return new ArgumentMap(serializeMap(arguments));
    }

    public final String[] deserialize(ArgumentMap map) {
        return deserializeMap(map.getHandle());
    }

}
