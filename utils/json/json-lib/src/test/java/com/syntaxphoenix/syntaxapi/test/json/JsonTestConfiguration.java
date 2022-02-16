package com.syntaxphoenix.syntaxapi.test.json;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.value.*;
import com.syntaxphoenix.syntaxapi.random.Keys;
import com.syntaxphoenix.syntaxapi.random.NumberGeneratorType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

public final class JsonTestConfiguration {

    private JsonTestConfiguration() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final File TEST_DIRECTORY = new File("testData");

    public static final long SEED = 5908375382894239029L;
    private static final RandomNumberGenerator SEED_RANDOM = NumberGeneratorType.MURMUR.create(SEED);

    public static final int TEST_AMOUNT = 20;
    public static final boolean TEST_PLAIN = true;
    public static final boolean TEST_PRETTY = true;

    public static final int OBJECT_VALUE_AMOUNT = 10;

    private static final HashMap<ValueType, Function<RandomNumberGenerator, JsonValue<?>>> BUILDERS = new HashMap<>();

    private static final BigInteger MAX_INT = BigInteger.valueOf(Long.MAX_VALUE);
    private static final BigDecimal DECI_LONG = BigDecimal.valueOf(Long.MAX_VALUE);
    private static final BigDecimal MAX_DECI = BigDecimal.valueOf(Double.MAX_VALUE).multiply(DECI_LONG).multiply(DECI_LONG);

    static {
        BUILDERS.put(ValueType.NULL, random -> JsonNull.get());
        BUILDERS.put(ValueType.STRING, random -> {
            return new JsonString(new Keys(random).makeKey(32));
        });
        BUILDERS.put(ValueType.BOOLEAN, random -> {
            return new JsonBoolean(random.nextBoolean());
        });
        BUILDERS.put(ValueType.BYTE, random -> {
            return new JsonByte((byte) random.nextShort(Byte.MIN_VALUE, Byte.MAX_VALUE));
        });
        BUILDERS.put(ValueType.SHORT, random -> {
            return new JsonShort(random.nextShort());
        });
        BUILDERS.put(ValueType.INTEGER, random -> {
            return new JsonInteger(random.nextInt());
        });
        BUILDERS.put(ValueType.FLOAT, random -> {
            return new JsonFloat(random.nextFloat(Float.MAX_VALUE));
        });
        BUILDERS.put(ValueType.LONG, random -> {
            return new JsonLong(random.nextLong());
        });
        BUILDERS.put(ValueType.DOUBLE, random -> {
            return new JsonDouble(random.nextDouble(Double.MAX_VALUE));
        });
        BUILDERS.put(ValueType.BIG_INTEGER, random -> {
            return new JsonBigInteger(BigInteger.valueOf(random.nextLong() * (random.nextShort() >> 8)).multiply(MAX_INT));
        });
        BUILDERS.put(ValueType.BIG_DECIMAL, random -> {
            return new JsonBigDecimal(BigDecimal.valueOf(random.nextDouble() * (random.nextShort() >> 8)).multiply(MAX_DECI));
        });
        BUILDERS.put(ValueType.ARRAY, random -> {
            JsonArray array = new JsonArray();
            for (ValueType type : ValueType.values()) {
                if (type.isJson() || type == ValueType.NUMBER) {
                    continue;
                }
                array.add(build(random, type));
            }
            return array;
        });
        BUILDERS.put(ValueType.OBJECT, random -> {
            JsonObject object = new JsonObject();
            for (ValueType type : ValueType.values()) {
                if (type.isJson() || type == ValueType.NUMBER) {
                    continue;
                }
                JsonArray array = new JsonArray();
                for (int i = 0; i < OBJECT_VALUE_AMOUNT; i++) {
                    array.add(build(random, type));
                }
                object.set(type.name(), array);
            }
            return object;
        });
    }

    public static RandomNumberGenerator newGenerator() {
        return NumberGeneratorType.MURMUR.create(SEED_RANDOM.nextLong(Long.MAX_VALUE));
    }

    public static void skipGenerators() {
        for (int i = 0; i < TEST_AMOUNT; i++) {
            SEED_RANDOM.nextLong(Long.MAX_VALUE);
        }
    }

    public static JsonValue<?> build(RandomNumberGenerator generator, ValueType type) {
        if (type == ValueType.JSON || type == ValueType.NUMBER) {
            return null;
        }
        return BUILDERS.get(type).apply(generator);
    }

}
