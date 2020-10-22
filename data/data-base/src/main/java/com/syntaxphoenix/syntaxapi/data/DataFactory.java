package com.syntaxphoenix.syntaxapi.data;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class DataFactory<B> {

	private final DataAdapterRegistry<B> adapterRegistry;

	public DataFactory(DataAdapterRegistry<B> adapterRegistry) {
		this.adapterRegistry = adapterRegistry;
	}

	/*
	 * Get
	 */

	public DataAdapterRegistry<B> getAdapaterRegistry() {
		return adapterRegistry;
	}

	/*
	 * Serialize
	 */

	public abstract DataFactory<B> toFile(DataContainer holder, File file);

	public abstract DataFactory<B> toStream(DataContainer holder, OutputStream stream);

	public abstract DataFactory<B> toString(DataContainer holder, StringBuilder builder);

	/*
	 * Deserialize
	 */

	public abstract DataFactory<B> fromFile(DataContainer holder, File file);

	public abstract DataFactory<B> fromStream(DataContainer holder, InputStream stream);

	public abstract DataFactory<B> fromString(DataContainer holder, String string);

}
