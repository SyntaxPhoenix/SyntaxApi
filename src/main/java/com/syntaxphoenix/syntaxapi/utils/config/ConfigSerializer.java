package com.syntaxphoenix.syntaxapi.utils.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Lauriichen
 *
 */
public class ConfigSerializer {
	
	private static JsonParser parser = new JsonParser();
	private static Gson gson;

	public static String[] getKeys(String input) {
		if (!input.contains(".")) {
			return new String[] { input };
		}
		return input.split("\\.");
	}

	public static String[] getNextKeys(String[] input) {
		if (input.length == 1) {
			return new String[0];
		} else {
			String[] output = new String[input.length - 1];
			for (int x = 1; x < input.length; x++) {
				output[x - 1] = input[x];
			}
			return output;
		}
	}

	public static String getLastKey(String[] input) {
		if (input.length != 0) {
			if (input.length == 1) {
				return input[0];
			}
			return input[input.length - 1];
		}
		return "";
	}

	public static String[] getKeysWithout(String[] input, String denied) {
		ArrayList<String> output = new ArrayList<>();
		for (int x = 0; x < input.length; x++) {
			if (!input[x].equals(denied)) {
				output.add(input[x]);
			}
		}
		return output.toArray(new String[0]);
	}
	
	public static Gson getConfiguredGson() {
		if(gson != null) {
			return gson;
		}
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		return gson = builder.create();
	}
	
	public static JsonParser getJsonParser() {
		return parser;
	}
	
	public static byte[] catchedSerialize(Object obj) {
		try {
			return serialize(obj);
		} catch (IOException e) {
			return new byte[0];
		}
	}
	
	public static Object catchedDeserialize(byte[] data) {
		try {
			return deserialize(data);
		} catch (IOException | ClassNotFoundException e) {
			return new Object();
		}
	}
	
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	/**
	 * @param String {input json}
	 * @return JsonObject {output json}
	 */
	public static JsonObject readJson(String input) {
		return getJsonParser().parse(input).getAsJsonObject();
	}

}
