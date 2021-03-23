package com.syntaxphoenix.syntaxapi.command;

import com.syntaxphoenix.syntaxapi.command.range.CollectionSizeRange;
import com.syntaxphoenix.syntaxapi.command.range.NumberChooseRange;
import com.syntaxphoenix.syntaxapi.command.range.NumberValueRange;
import com.syntaxphoenix.syntaxapi.command.range.StateRange;
import com.syntaxphoenix.syntaxapi.command.range.TextChooseRange;
import com.syntaxphoenix.syntaxapi.command.range.TextSizeRange;

public abstract class BaseArgumentRange {

    private String label;

    public String getLabel() {
        return label;
    }

    public BaseArgumentRange setLabel(String label) {
        this.label = label;
        return this;
    }

    public abstract RangeType getType();

    public abstract Class<?> getInputType();

    public abstract boolean hasType(BaseArgument argument);

    public abstract boolean isInRange(BaseArgument argument);

    @Override
    public abstract String toString();

    public abstract String toString(ArgumentRangeSerializer serializer);

    public TextSizeRange asTextSize() {
        return (TextSizeRange) this;
    }

    public TextChooseRange asTextChoose() {
        return (TextChooseRange) this;
    }

    public StateRange asState() {
        return (StateRange) this;
    }

    public CollectionSizeRange<?> asCollectionSize() {
        return (CollectionSizeRange<?>) this;
    }

    public NumberValueRange asNumberValue() {
        return (NumberValueRange) this;
    }

    public NumberChooseRange asNumberChoose() {
        return (NumberChooseRange) this;
    }

}
