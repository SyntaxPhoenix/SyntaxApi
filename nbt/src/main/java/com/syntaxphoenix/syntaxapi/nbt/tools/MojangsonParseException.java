package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.IOException;

public class MojangsonParseException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 3011813566487100293L;

    public MojangsonParseException(String msg, String content, int index) {
        super(msg + " at: " + printErrorLoc(content, index));
    }

    private static String printErrorLoc(String content, int index) {
        StringBuilder builder = new StringBuilder();
        int i = Math.min(content.length(), index);
        if (i > 35) {
            builder.append("...");
        }
        builder.append(content.substring(Math.max(0, i - 35), i));
        builder.append("<--[HERE]");

        return builder.toString();
    }

}
