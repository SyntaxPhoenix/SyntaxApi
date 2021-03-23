package com.syntaxphoenix.syntaxapi.net.http;

@FunctionalInterface
public interface RequestSerializer {

	public RequestData<?> serialize(String data) throws Exception;

}
