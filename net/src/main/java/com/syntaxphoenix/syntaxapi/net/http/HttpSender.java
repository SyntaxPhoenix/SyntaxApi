package com.syntaxphoenix.syntaxapi.net.http;

import java.io.InputStream;
import java.net.Socket;

public class HttpSender {

    private final Socket client;
    private final InputStream input;

    public HttpSender(Socket client, InputStream input) {
        this.client = client;
        this.input = input;
    }

    public Socket getClient() {
        return client;
    }

    public InputStream getInput() {
        return input;
    }

}
