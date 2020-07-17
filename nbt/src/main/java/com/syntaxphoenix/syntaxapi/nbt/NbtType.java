package com.syntaxphoenix.syntaxapi.nbt;

import com.syntaxphoenix.syntaxapi.reflection.Reflect;

/**
 * <p>
 *    The type of an NBTTag.
 * </p>
 * <p>
 *     This enum may be prone to further additions, such as the {@link #LONG_ARRAY} which has been added by Mojang
 *     in NBT Version 19133. (second NBT version)
 * </p>
 * <p>
 *     For a community maintained documentation of the NBT format and its types, visit the
 *     <a href=https://minecraft.gamepedia.com/NBT_format>Minecraft Wiki</a>
 * </p>
 */
public enum NbtType {
    /**
     * Used to mark the end of compounds tags. May also be the type of empty list tags.
     * @since NBT Version 19132
     */
    END("TAG_End", false, false, false, NbtEnd.class),

    /**
     * A signed integer (8 bits). Sometimes used for booleans. (-128 to 127)
     * @since NBT Version 19132
     */
    BYTE("TAG_Byte", true, true, false, NbtByte.class),

    /**
     * A signed integer (16 bits). (-2<sup>15</sup> to 2<sup>15</sup>-1)
     * @since NBT Version 19132
     */
    SHORT("TAG_Short", true, true, false, NbtShort.class),

    /**
     * A signed integer (32 bits). (-2<sup>31</sup> to 2<sup>31</sup>-1)
     * @since NBT Version 19132
     */
    INT("TAG_Int", true, true, false, NbtInt.class),

    /**
     * A signed integer (64 bits). (-2<sup>63</sup> to 2<sup>63</sup>-1)
     * @since NBT Version 19132
     */
    LONG("TAG_Long", true, true, false, NbtLong.class),

    /**
     * A signed (IEEE 754-2008) floating point number (32 bits).
     * @since NBT Version 19132
     */
    FLOAT("TAG_Float", true, true, false, NbtFloat.class),

    /**
     * A signed (IEEE 754-2008) floating point number (64 bits).
     * @since NBT Version 19132
     */
    DOUBLE("TAG_Double", true, true, false, NbtDouble.class),
    
    /**
     * An array of {@link #BYTE} with maximum length of {@link Integer#MAX_VALUE}.
     * @since NBT Version 19132
     */
    BYTE_ARRAY("TAG_Byte_Array", false, false, true, NbtByteArray.class),

    /**
     * UTF-8 encoded string.
     * @since NBT Version 19132
     */
    STRING("TAG_String", true, false, false, NbtString.class),

    /**
     * A list of unnamed tags of equal type.
     * @since NBT Version 19132
     */
    LIST("TAG_List", false, false, false, NbtList.class),

    /**
     * Compound of named tags followed by {@link #END}.
     * @since NBT Version 19132
     */
    COMPOUND("TAG_Compound", false, false, false, NbtCompound.class),
    
    /**
     * An array of {@link #BYTE} with maximum length of {@link Integer#MAX_VALUE}.
     * @since NBT Version 19132
     */
    INT_ARRAY("TAG_Int_Array", false, false, true, NbtIntArray.class),
    
    /**
     * An array of {@link #LONG} with maximum length of {@link Integer#MAX_VALUE}.
     * @since NBT Version 19133
     */
    LONG_ARRAY("TAG_Long_Array", false, false, true, NbtLongArray.class);

    private final String name;
    private final boolean numeric, primitive, array;
    private final byte id;
    private final Reflect owner;

    NbtType(String name, boolean primitive, boolean numeric, boolean array, Class<? extends NbtTag> owner) {
        this.name = name;
        this.id = (byte) ordinal();
        this.numeric = numeric;
        this.primitive = primitive;
        this.array = array;
        this.owner = new Reflect(owner);
    }
    
    /**
     * Returns a new instance of the type
     * 
     * @return the new instance
     */
    public NbtTag init() {
    	return (NbtTag) owner.init();
    }
    
    /**
     * Returns the type with the given id.
     *
     * @param id the id
     * @return the type
     */
    public static NbtType getById(byte id) {
        return values()[id];
    }
    
    /**
     * <p>
     *     Returns the id of this tag type.
     * </p>
     * <p>
     *     Although this method is currently equivalent to {@link #ordinal()}, it should always be used in its stead,
     *     since it is not guaranteed that this behavior will remain consistent.
     * </p>
     *
     * @return the id
     */
    public byte getId() {
        return id;
    }

    /**
     * Return the type with the given owning class
     * 
     * @param clazz the owning class
     * @return the type or null
     */
	public static NbtType getByClass(Class<? extends NbtTag> clazz) {
		for(NbtType type : values()) {
			if(type.getOwningClass().equals(clazz)) {
				return type;
			}
		}
		return null;
	}
    
    /**
     * Returns the owning class of this type.
     *
     * @return the owning class
     */
    @SuppressWarnings("unchecked")
	public Class<? extends NbtTag> getOwningClass() {
    	return (Class<? extends NbtTag>) owner.getOwner();
    }
    
    /**
     * Returns the owning class of this type.
     *
     * @return the owning class
     */
    public Reflect getOwner() {
    	return owner;
    }
    
    /**
     * Returns the name of this type.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the name of this type.
     * {@link getName()}
     * 
     * @return the name
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * <p>
     *     Returns whether this tag type is numeric.
     * </p>
     * <p>
     *     All tag types with payloads that are representable as a {@link Number} are compliant with this definition.
     * </p>
     *
     * @return whether this type is numeric
     */
    public boolean isNumeric() {
        return numeric;
    }
    
    /**
     * Returns whether this tag type is primitive, meaning that it is not a {@link NbtByteArray}, {@link NbtIntArray},
     * {@link NbtList}, {@link NbtCompound} or {@link NbtEnd}.
     *
     * @return whether this type is numeric
     */
    public boolean isPrimitive() {
        return primitive;
    }
    
    /**
     * Returns whether this tag type is is an array type such as {@link NbtByteArray} or {@link NbtIntArray}.
     *
     * @return whether this type is an array type
     */
    public boolean isArray() {
        return array;
    }
    
}
