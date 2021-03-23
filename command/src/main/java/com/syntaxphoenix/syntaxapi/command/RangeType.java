package com.syntaxphoenix.syntaxapi.command;

import java.util.Collection;

public enum RangeType {

    CUSTOM(RangeSuperType.OBJECT, Object.class),

    TEXT_SIZE_RANGE(RangeSuperType.TEXT, CharSequence.class),
    TEXT_CHOOSE_RANGE(RangeSuperType.TEXT, CharSequence.class),

    STATE_RANGE(RangeSuperType.STATE, boolean.class),

    NUMBER_VALUE_RANGE(RangeSuperType.NUMBER, Number.class),
    NUMBER_CHOOSE_RANGE(RangeSuperType.NUMBER, Number.class),

    COLLECTION_SIZE_RANGE(RangeSuperType.COLLECTION, Collection.class);

    /*
     * 
     */

    private final RangeSuperType superType;
    private final Class<?> inputType;

    /*
     * 
     */

    private RangeType(RangeSuperType superType, Class<?> inputType) {
        this.superType = superType;
        this.inputType = inputType;
    }

    public RangeSuperType getSuperType() {
        return superType;
    }

    public Class<?> getInputType() {
        return inputType;
    }

}
