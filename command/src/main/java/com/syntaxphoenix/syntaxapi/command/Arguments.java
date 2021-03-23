package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import com.syntaxphoenix.syntaxapi.command.arguments.ListArgument;
import com.syntaxphoenix.syntaxapi.exception.ObjectLockedException;

/**
 * @author Lauriichen
 *
 */
public class Arguments implements Iterable<BaseArgument> {

    private final List<BaseArgument> arguments;
    private boolean locked = false;

    public Arguments() {
        this.arguments = new ArrayList<>();
    }

    public Arguments(List<BaseArgument> arguments) {
        this.arguments = arguments;
    }

    public int count() {
        return arguments.size();
    }

    public BaseArgument get(int position) {
        if (position < 1) {
            throw negativeOrZero();
        }
        if (position > count()) {
            throw outOfBounce(position);
        }
        return arguments.get(position - 1);
    }

    public void add(BaseArgument argument, int position) {
        if (locked) {
            throw locked();
        }
        if (position < 1) {
            throw negativeOrZero();
        }
        if (argument == null) {
            return;
        }
        arguments.add(position - 1, argument);
    }

    public void add(BaseArgument argument) {
        if (locked) {
            throw locked();
        }
        if (argument == null) {
            return;
        }
        arguments.add(argument);
    }

    public boolean match(Predicate<BaseArgument> filter) {
        return arguments.stream().anyMatch(filter);
    }

    public ArgumentType getType(int position) {
        return get(position).getType();
    }

    public ArgumentSuperType getSuperType(int position) {
        return getType(position).getSuperType();
    }

    protected boolean isLocked() {
        return locked;
    }

    protected void setLocked(boolean locked) {
        this.locked = locked;
    }

    /*
     * toString
     */

    @Override
    public String toString() {
        return toString(ArgumentSerializer.DEFAULT);
    }

    public String toString(int start) {
        return toString(start, arguments.size());
    }

    public String toString(int start, int end) {
        return toString(start, end, ArgumentSerializer.DEFAULT);
    }

    public String toString(ArgumentSerializer serializer) {
        return new ListArgument<>(arguments).toString(serializer);
    }

    public String toString(int start, ArgumentSerializer serializer) {
        return toString(start, serializer);
    }

    public String toString(int start, int end, ArgumentSerializer serializer) {
        return new ListArgument<>(arguments.subList(start, end)).toString(serializer);
    }

    /**
     * Exception Construction
     */

    private IllegalArgumentException negativeOrZero() {
        return new IllegalArgumentException("Bound must be positive!");
    }

    private IndexOutOfBoundsException outOfBounce(int position) {
        return new IndexOutOfBoundsException("Index: " + position + " - Size: " + count());
    }

    private ObjectLockedException locked() {
        return new ObjectLockedException("Cannot edit a locked object!");
    }

    /*
     * 
     * Utils
     * 
     */

    @Override
    public Iterator<BaseArgument> iterator() {
        return arguments.iterator();
    }

    public Arguments copy(int start) {
        return copy(start, arguments.size());
    }

    public Arguments copy(int start, int end) {
        return new Arguments(arguments.subList(start, end));
    }

}
