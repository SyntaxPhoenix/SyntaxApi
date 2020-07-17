package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;

public enum ContentType {
	
	X_WWW_FORM_URLENCODED(ContentSerializer.URL_ENCODED, ContentDeserializer.URL_ENCODED), PLAIN(ContentSerializer.PLAIN, ContentDeserializer.PLAIN), JSON(ContentSerializer.JSON, ContentDeserializer.JSON), CUSTOM;
	
	private ContentSerializer serializer;
	private ContentDeserializer deserializer;
	
	private ContentType(ContentSerializer serializer, ContentDeserializer deserializer) {
		this.serializer = serializer;
	}
	
	private ContentType() {
		
	}
	
	/*
	 * 
	 */
	
	public String type() {
		return "application/" + name().toLowerCase();
	}
	
	/*
	 * 
	 */
	
	public String serialize(JsonObject object) {
		if(serializer == null)
			return "";
		return serializer.process(object);
	}
	
	public JsonObject deserialize(String value) { 
		if(deserializer == null)
			return null;
		return deserializer.process(value);
	}
	
	/*
	 * 
	 */
	
	public ContentType serializer(ContentSerializer serializer) {
		if(this.serializer == null)
			this.serializer = serializer;
		return this;
	}
	
	public ContentType deserializer(ContentDeserializer deserializer) {
		if(this.deserializer == null)
			this.deserializer = deserializer;
		return this;
	}
	
	/*
	 * 
	 */
	
	public static ContentType fromString(String value) {
		for(ContentType type : values())
			if(type.type().equals(value))
				return type;
		return ContentType.CUSTOM;
	}

}
