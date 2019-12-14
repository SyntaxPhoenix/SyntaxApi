package com.syntaxphoenix.syntaxapi.config.bytemesh;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.utils.config.ConfigSerializer;

/**
 * @author Lauriichen
 *
 */
public class MeshConfigSection extends BaseSection {

	/**
	 *  
	 */
	public MeshConfigSection() {
		super("");
	}

	/**
	 * @param String
	 *            {name}
	 */
	public MeshConfigSection(String name) {
		super(name);
	}

	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseSection#initSection(java.lang.String)
	 */
	@Override
	protected BaseSection initSection(String name) {
		return new MeshConfigSection(name);
	}

	/**
	 * @param String
	 *            {data input}
	 */
	public void fromString(String input) {
		fromData(input.getBytes());
	}

	/**
	 * @param byte[] {data input}
	 */
	public void fromData(byte[] input) {
		Object output = ConfigSerializer.catchedDeserialize(input);
		if (output == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Serializable> values = (HashMap<String, Serializable>) output;

		if (!values.isEmpty()) {
			for (Entry<String, Serializable> entry : values.entrySet()) {
				Serializable object = entry.getValue();
				if (object instanceof String) {
					String string = (String) object;
					if (string.endsWith("#?MeshConfigSection")) {
						((MeshConfigSection) createSection(entry.getKey()))
								.fromString(string.replace("#?MeshConfigSection", ""));
					} else {
						set(entry.getKey(), object);
					}
				} else {
					set(entry.getKey(), object);
				}
			}
		}
	}

	/**
	 * @param byte[] {data input}
	 */
	public void fromMeshedData(byte[] input) {
		fromData(meshData(input));
	}

	/**
	 * @return String {data output}
	 */
	public String toString() {
		return new String(toData());
	}

	/**
	 * @return byte[] {Data}
	 */
	public byte[] toData() {
		HashMap<String, Serializable> serializable = new HashMap<>();
		HashMap<String, Object> values = getValues();
		if (!values.isEmpty()) {
			for (Entry<String, Object> entry : values.entrySet()) {
				Object input = entry.getValue();
				if (input instanceof MeshConfigSection) {
					serializable.put(entry.getKey(), ((MeshConfigSection) input).toString() + "#?MeshConfigSection");
				} else {
					if (input instanceof Serializable) {
						serializable.put(entry.getKey(), (Serializable) input);
					}
				}
			}
		}
		return ConfigSerializer.catchedSerialize(serializable);
	}

	/**
	 * @return byte[] {Meshed data}
	 */
	public byte[] toMeshedData() {
		return meshData(toData());
	}

	/**
	 * @param byte[]
	 *            {Unmeshed / Mashed data}
	 * @return byte[] {Meshed / Unmeshed data}
	 */
	private static byte[] meshData(byte[] input) {
		byte[] output = new byte[input.length];
		if (input.length == 0) {
			return output;
		}
		for (int x = 0; x < input.length; x += 2) {
			int y = x + 1;
			if (y >= input.length) {
				output[x] = input[x];
				continue;
			}
			output[y] = input[x];
			output[x] = input[y];
		}
		return output;
	}

}
