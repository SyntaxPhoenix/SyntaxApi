package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.utils.io.Serializer;

public class NbtSerializer implements Serializer<NbtNamedTag> {

	public static final NbtSerializer COMPRESSED = new NbtSerializer(true);
	public static final NbtSerializer UNCOMPRESSED = new NbtSerializer(true);

	private final boolean compress;

	/**
	 * Constructs a new NBT-Serializer.
	 *
	 * @param compress whether to use gzip compression.
	 */
	public NbtSerializer(boolean compress) {
		this.compress = compress;
	}

	/**
	 * Constructs a new NBT-Serializer with enabled gzip compression.
	 */
	public NbtSerializer() {
		this(true);
	}

	@Override
	public void toStream(NbtNamedTag tag, OutputStream stream) throws IOException {
		if (compress) {
			GZIPOutputStream gzipStream = new GZIPOutputStream(stream);
			NbtOutputStream nbtStream = new NbtOutputStream(gzipStream);
			nbtStream.writeNamedTag(tag);
			gzipStream.finish();
			nbtStream.flush();
			nbtStream.close();
		} else {
			NbtOutputStream output = new NbtOutputStream(stream);
			output.writeNamedTag(tag);
			output.flush();
			output.close();
		}
	}

}
