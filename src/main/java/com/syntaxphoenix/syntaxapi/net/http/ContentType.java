package com.syntaxphoenix.syntaxapi.net.http;

import java.util.HashMap;

public enum ContentType {
	
	X_WWW_FORM_URLENCODED(ContentProcessor.URL_ENCODED), JSON(ContentProcessor.JSON), CUSTOM;
	
	private ContentProcessor processor;
	
	private ContentType(ContentProcessor processor) {
		this.processor = processor;
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
	
	public String process(HashMap<String, String> map) {
		if(processor == null)
			return "";
		return processor.process(map);
	}
	
	public ContentType processor(ContentProcessor processor) {
		if(this.processor == null)
			this.processor = processor;
		return this;
	}

}
