package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Streams {

	public static String toString(InputStream input) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
			return buffer.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int length;
		while ((length = input.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, length);
		}
		buffer.flush();
		buffer.close();
		return buffer.toByteArray();
	}

}
