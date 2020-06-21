package com.syntaxphoenix.syntaxapi.test.tests;

import java.io.IOException;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.net.http.ContentType;
import com.syntaxphoenix.syntaxapi.net.http.Request;
import com.syntaxphoenix.syntaxapi.net.http.RequestType;
import com.syntaxphoenix.syntaxapi.net.http.Response;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;

public class RequestTest implements Consumer<String[]>, Printer {

	@Override
	public void accept(String[] args) {
		
		Request request = new Request(RequestType.POST);
		
		request.parameter("user", "nice");
		
		try {
			Response response = request.execute("http://spigot.syntaxphoenix.com/verify", ContentType.JSON);
			
			print("code: " + response.getCode());
			print("message: " + response.getResponse());
			
		} catch (IOException e) {
			print(e);
		}
		
	}

}
