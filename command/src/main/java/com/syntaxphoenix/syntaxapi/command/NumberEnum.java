package com.syntaxphoenix.syntaxapi.command;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum NumberEnum {

    BYTE(Byte.class, byte.class),
    SHORT(Short.class, short.class),
    INTEGER(Integer.class, int.class),
    LONG(Long.class, long.class),
    FLOAT(Float.class, float.class),
    DOUBLE(Double.class, double.class),
    BIG_INTEGER(BigInteger.class),
    BIG_DECIMAL(BigDecimal.class);

    final List<Class<?>> classes;

    private NumberEnum(Class<?>... array) {
        classes = Arrays.asList(array);
    }

    public static Optional<NumberEnum> of(Object object) {
        return of(object instanceof Class ? (Class<?>) object : object.getClass());
    }

    public static Optional<NumberEnum> of(Class<?> clazz) {
        for (NumberEnum number : values()) {
            if (number.classes.contains(clazz)) {
                return Optional.of(number);
            }
        }
        return Optional.empty();
    }

}
