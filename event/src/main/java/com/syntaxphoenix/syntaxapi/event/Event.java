package com.syntaxphoenix.syntaxapi.event;

/**
 * @author Lauriichen
 *
 */
public abstract class Event {

    public boolean isCancelable() {
        return this instanceof Cancelable;
    }

}
