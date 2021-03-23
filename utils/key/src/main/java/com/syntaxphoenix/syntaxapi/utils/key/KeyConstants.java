package com.syntaxphoenix.syntaxapi.utils.key;

import java.util.regex.Pattern;

public class KeyConstants {

    public static final String KEY_FORMAT = "%s:%s";
    public static final String NAMED_KEY_FORMAT = "%s:%s#%s";

    public static final Pattern KEY_PATTERN = Pattern.compile("(<Namespace>[a-z0-9._-]+):(<Key>[a-z0-9/._-]+)");
    public static final Pattern NAMED_KEY_PATTERN = Pattern
        .compile("(<Namespace>[a-z0-9._-]+):(<Key>[a-z0-9/._-]+)#(<Name>[a-zA-Z0-9/._-\\s]+)");

    public static final Pattern VALID_KEY = Pattern.compile("[a-z0-9/._-]+");
    public static final Pattern VALID_NAMESPACE = Pattern.compile("[a-z0-9._-]+");

}
