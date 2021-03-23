package com.syntaxphoenix.syntaxapi.config;

import java.io.File;
import java.util.Optional;

/**
 * @author Lauriichen
 *
 */
public interface BaseConfig {

    public void load(File file) throws Throwable;

    public void save(File file) throws Throwable;

    public default Optional<BaseSection> asSection() {
        BaseSection output = null;
        if (this instanceof BaseSection) {
            output = (BaseSection) this;
        }
        return Optional.ofNullable(output);
    }

}
