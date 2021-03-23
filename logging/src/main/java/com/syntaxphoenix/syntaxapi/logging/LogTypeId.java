package com.syntaxphoenix.syntaxapi.logging;

public enum LogTypeId {

    INFO,
    WARNING,
    ERROR,
    DEBUG;

    public String id() {
        return name().toLowerCase();
    }

}
