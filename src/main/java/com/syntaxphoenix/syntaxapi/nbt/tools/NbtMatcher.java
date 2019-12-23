package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.util.List;
import java.util.Objects;

import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;

public class NbtMatcher {
    
    /**
     * Check whether the pattern and the matching tag are completely identical. This will cause values which are
     * present in the matching tag but not in the pattern tag to prevent a match.
     */
    @SuppressWarnings("unused")
	private final static int
    EXACT = 0b1,
    /**
     * Ignore values, only verify that the NBT tags match in types.
     */
    TYPES_ONLY = 0b10;
    
    private final NbtTag pattern;
    @SuppressWarnings("unused")
	private final int flags;
    
    public NbtMatcher(NbtTag pattern, int flags) {
        this.pattern = Objects.requireNonNull(pattern);
        this.flags = flags;
    }
    
    public boolean matches(NbtTag tag) {
        return pattern.equals(tag);
    }
    
    /**
     * <p>
     *     Custom implementation of a {@link NbtType#LIST} tag which caches the type id, hash and emptiness.
     * </p>
     * <p>
     *     This custom list is optimized for being matched against a high quantity of other lists.
     * </p>
     */
    @SuppressWarnings("unused")
	private static class NBTMatcherList extends NbtTag {
        
        private final int hash;
        private final byte type;
        private final boolean empty;
        private final NbtTag[] value;
        
        public NBTMatcherList(NbtList<?> tag) {
            this.empty = tag.isEmpty();
            this.hash = tag.hashCode();
            this.type = tag.getTypeId();
            this.value = tag.getValue().toArray(new NbtTag[tag.size()]);
        }
    
        @Override
        public boolean equals(Object obj) {
            return obj instanceof NbtList && equals((NbtList<?>) obj);
        }
    
        @SuppressWarnings("unlikely-arg-type")
		public boolean equals(NbtList<?> tag) {
            return this.empty && tag.isEmpty()
                || this.type == tag.getTypeId() && this.hash == tag.hashCode() && this.equals(tag.getValue());
        }
        
        public boolean equals(List<NbtTag> tags) {
            int index = 0;
            for (NbtTag tag : tags)
                if (!tag.equals(value[index++]))
                    return false;
            return true;
        }
    
        @Override
        public Object getValue() {
            return value;
        }
    
        @Override
        public NbtType getType() {
            return NbtType.LIST;
        }
    
        @Override
        public String toMSONString() {
            throw new UnsupportedOperationException();
        }
        
    }
    
}
