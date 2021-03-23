package com.syntaxphoenix.syntaxapi.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ServicePriority {

    LOWEST(-2),
    LOW(-1),
    NORMAL(0),
    HIGH(1),
    HIGHEST(2);

    /*
     * 
     */

    public static final List<ServicePriority> ORDERED_VALUES = Collections
        .unmodifiableList(Arrays.asList(HIGHEST, HIGH, NORMAL, LOW, LOWEST));

    /*
     * 
     */

    private int priority;

    private ServicePriority(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

}
