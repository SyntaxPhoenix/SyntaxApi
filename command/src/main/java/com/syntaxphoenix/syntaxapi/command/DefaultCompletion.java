package com.syntaxphoenix.syntaxapi.command;

public class DefaultCompletion extends BaseCompletion {

    private final Arguments arguments = new Arguments();

    public DefaultCompletion add(BaseArgument argument) {
        arguments.add(argument);
        return this;
    }

    public DefaultCompletion addSafe(BaseArgument argument) {
        if (!arguments.match(current -> current.asObject().equals(argument.asObject())))
            arguments.add(argument);
        return this;
    }

    public DefaultCompletion addAll(BaseArgument... arguments) {
        for (BaseArgument argument : arguments) {
            add(argument);
        }
        return this;
    }

    public DefaultCompletion addAllSafe(BaseArgument... arguments) {
        for (BaseArgument argument : arguments) {
            addSafe(argument);
        }
        return this;
    }

    @Override
    public Arguments getCompletion() {
        return arguments;
    }

}