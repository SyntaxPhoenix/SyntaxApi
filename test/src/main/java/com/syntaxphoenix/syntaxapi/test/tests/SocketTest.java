package com.syntaxphoenix.syntaxapi.test.tests;

import java.io.IOException;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.net.SocketServer;
import com.syntaxphoenix.syntaxapi.net.http.RestApiServer;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;

public class SocketTest implements Consumer<String[]>, Printer {

    public int failed = 0;

    @Override
    public void accept(String[] args) {
        int port = SocketServer.DEFAULT_PORT;
        if (args.length >= 1) {
            for (String arg : args) {
                if (!arg.contains("=")) {
                    continue;
                }
                String[] parts = arg.split("=");
                int value;
                try {
                    value = Integer.parseInt(parts[1]);
                } catch (Throwable thrw) {
                    value = SocketServer.DEFAULT_PORT;
                }
                if (parts[0].equalsIgnoreCase("port")) {
                    port = value;
                }
            }
        }

        RestApiServer server = new RestApiServer(port);

        try {
            server.start();
        } catch (IOException e) {
            print(e);
        }

        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            print(e);
        }

        try {
            server.stop();
        } catch (IOException e) {
            print(e);
        }
    }

}
