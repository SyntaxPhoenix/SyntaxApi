package com.syntaxphoenix.syntaxapi.event;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum EventPriority {

    LOWEST(-2),
    LOW(-1),
    NORMAL(0),
    HIGH(1),
    HIGHEST(2);

    /*
     * 
     */

    public static final List<EventPriority> ORDERED_VALUES = Collections
        .unmodifiableList(Arrays.asList(HIGHEST, HIGH, NORMAL, LOW, LOWEST));

    /*
     * 
     */

    private int priority;

    private EventPriority(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

}
