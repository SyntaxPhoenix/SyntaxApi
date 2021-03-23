package com.syntaxphoenix.syntaxapi.command;

import java.util.ArrayList;

public abstract class ArgumentRangeIdentifier {

    public static final ArgumentRangeIdentifier DEFAULT = new DefaultArgumentRangeIdentifier();

    /**
     * @param rawRanges array of raw ranges
     * @return list of complex ranges
     */
    public abstract ArrayList<BaseArgumentRange> process(String... rawRanges);

}
