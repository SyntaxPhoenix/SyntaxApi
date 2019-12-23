package com.syntaxphoenix.syntaxapi.nbt;

/**
 * A named tag consisting of a {@link String} for its name and a {@link NbtTag} for its value.
 */
public class NbtNamedTag {

    private final String name;
    private final NbtTag tag;

    /**
     * Constructs a new named tag.
     *
     * @param name the name
     * @param tag the tag
     */
    public NbtNamedTag(String name, NbtTag tag) {
        this.name = name;
        this.tag = tag;
    }

    /**
     * Returns the name of the tag.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the tag.
     *
     * @return the tag
     */
    public NbtTag getTag() {
        return tag;
    }
    
    // MISC
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NbtNamedTag && equals((NbtNamedTag) obj);
    }
    
    public boolean equals(NbtNamedTag tag) {
        return this.name.equals(tag.name) && this.tag.equals(tag.tag);
    }

}
