package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.BufferedReader;
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
	
}
