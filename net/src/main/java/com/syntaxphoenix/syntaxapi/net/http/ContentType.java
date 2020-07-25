package com.syntaxphoenix.syntaxapi.net.http;

import java.net.URL;

import com.google.gson.JsonObject;

public enum ContentType {

	X_WWW_FORM_URLENCODED(ContentSerializer.URL_ENCODED, ContentDeserializer.URL_ENCODED, ContentToUrlModifier.URL_ENCODED),
	PLAIN(ContentSerializer.PLAIN, ContentDeserializer.PLAIN), 
	JSON(ContentSerializer.JSON, ContentDeserializer.JSON),
	CUSTOM;

	private ContentSerializer serializer;
	private ContentDeserializer deserializer;

	private ContentToUrlModifier urlModifier;

	private ContentType(ContentSerializer serializer, ContentDeserializer deserializer, ContentToUrlModifier urlModifier) {
		this.serializer = serializer;
		this.deserializer = deserializer;
		this.urlModifier = urlModifier;
	}

	private ContentType(ContentSerializer serializer, ContentDeserializer deserializer) {
		this.serializer = serializer;
		this.deserializer = deserializer;
	}

	private ContentType() {

	}

	/*
	 * 
	 */

	public boolean supportsUrlModification() {
		return urlModifier != null;
	}

	public String type() {
		return "application/" + name().toLowerCase();
	}

	/*
	 * 
	 */
	
	public void modifyUrl(URL url, JsonObject object) {
		if(urlModifier == null)
			return;
		urlModifier.apply(url, object, serializer);
	}

	public String serialize(JsonObject object) {
		if (serializer == null)
			return "";
		return serializer.process(object);
	}

	public JsonObject deserialize(String value) {
		if (deserializer == null)
			return null;
		return deserializer.process(value);
	}

	/*
	 * 
	 */

	public ContentType urlModifier(ContentToUrlModifier urlModifier) {
		if (this.urlModifier == null)
			this.urlModifier = urlModifier;
		return this;
	}

	public ContentType serializer(ContentSerializer serializer) {
		if (this.serializer == null)
			this.serializer = serializer;
		return this;
	}

	public ContentType deserializer(ContentDeserializer deserializer) {
		if (this.deserializer == null)
			this.deserializer = deserializer;
		return this;
	}

	/*
	 * 
	 */

	public static ContentType fromString(String value) {
		for (ContentType type : values())
			if (type.type().equals(value))
				return type;
		return ContentType.CUSTOM;
	}

}
