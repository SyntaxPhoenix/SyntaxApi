package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.utils.io.Deserializer;

public class NbtDeserializer implements Deserializer<NbtNamedTag> {

    public static final NbtDeserializer COMPRESSED = new NbtDeserializer(true);
    public static final NbtDeserializer UNCOMPRESSED = new NbtDeserializer(false);

    private final boolean compressed;

    /**
     * Constructs a new NBT-Deserializer.
     *
     * @param compressed whether the input is g-zip compressed
     */
    public NbtDeserializer(boolean compressed) {
        this.compressed = compressed;
    }

    /**
     * Constructs a new NBT-Deserializer with enabled g-zip decompression.
     */
    public NbtDeserializer() {
        this(true);
    }

    @Override
    public NbtNamedTag fromStream(InputStream stream) throws IOException {
        NbtInputStream nbtStream = compressed ? new NbtInputStream(new GZIPInputStream(stream)) : new NbtInputStream(stream);

        NbtNamedTag tag = nbtStream.readNamedTag();
        nbtStream.close();
        if (tag == null) {
            throw new IOException("failed to read NBT tag due to EOS");
        } else {
            return tag;
        }
    }

}
