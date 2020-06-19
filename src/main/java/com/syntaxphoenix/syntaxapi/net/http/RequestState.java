package com.syntaxphoenix.syntaxapi.net.http;

public enum RequestState {

	ACCEPTED, DENIED;

	private boolean message = false;

	public boolean message() {
		return message;
	}

	public void message(boolean message) {
		this.message = message;
	}
	
	public boolean accepted() {
		return this == ACCEPTED;
	}
	
	public boolean denied() {
		return this == DENIED;
	}

}
