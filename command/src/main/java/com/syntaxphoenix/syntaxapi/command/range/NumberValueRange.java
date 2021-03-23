package com.syntaxphoenix.syntaxapi.command.range;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.syntaxphoenix.syntaxapi.command.ArgumentRangeSerializer;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;
import com.syntaxphoenix.syntaxapi.command.BaseArgumentRange;
import com.syntaxphoenix.syntaxapi.command.RangeType;

public class NumberValueRange extends BaseArgumentRange {

    private final BigDecimal minimum;
    private final BigDecimal maximum;

    public NumberValueRange(int minimum, int maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(byte minimum, byte maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(short minimum, short maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(float minimum, float maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(double minimum, double maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(long minimum, long maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(BigInteger minimum, BigInteger maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(BigDecimal minimum, BigDecimal maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public NumberValueRange(char minimum, char maximum) {
        this.minimum = new BigDecimal(minimum);
        this.maximum = new BigDecimal(maximum);
    }

    public NumberValueRange(Number minimum, Number maximum) {
        this.minimum = new BigDecimal(minimum.toString());
        this.maximum = new BigDecimal(maximum.toString());
    }

    public NumberValueRange(NumberValueRange number) {
        this.minimum = number.getMinimum();
        this.maximum = number.getMaximum();
    }

    /*
     * 
     */

    public BigDecimal getMinimum() {
        return minimum;
    }

    public BigDecimal getMaximum() {
        return maximum;
    }

    /*
     * 
     */

    @Override
    public RangeType getType() {
        return RangeType.NUMBER_VALUE_RANGE;
    }

    @Override
    public Class<?> getInputType() {
        return getType().getInputType();
    }

    @Override
    public boolean hasType(BaseArgument argument) {
        return argument.getClassType().isAssignableFrom(getInputType());
    }

    @Override
    public boolean isInRange(BaseArgument argument) {
        if (!hasType(argument)) {
            return false;
        }
        Number number = (Number) argument.asObject();
        BigDecimal decimal;
        if (!(number instanceof BigDecimal)) {
            decimal = new BigDecimal(number.toString());
        } else {
            decimal = (BigDecimal) number;
        }
        return decimal.compareTo(minimum) >= 0 && decimal.compareTo(maximum) <= 0;
    }

    /*
     * 
     */

    @Override
    public String toString() {
        return toString(ArgumentRangeSerializer.DEFAULT);
    }

    @Override
    public String toString(ArgumentRangeSerializer serializer) {
        return serializer.toString(this);
    }

}
