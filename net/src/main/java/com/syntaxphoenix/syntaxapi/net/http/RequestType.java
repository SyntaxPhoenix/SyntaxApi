package com.syntaxphoenix.syntaxapi.net.http;

public enum RequestType {

	GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH, NONE;

	public static RequestType fromString(String value) {
		value = value.toUpperCase();
		for (RequestType type : values())
			if (type.name().equals(value))
				return type;
		return NONE;
	}

}
