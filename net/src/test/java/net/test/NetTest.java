package net.test;

import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import com.syntaxphoenix.syntaxapi.net.http.RequestType;
import com.syntaxphoenix.syntaxapi.net.http.RestApiServer;

public class NetTest {

    @Test
    public void test() throws Exception {
        RestApiServer server = new RestApiServer(Executors.newCachedThreadPool());
        server.setSerializer(new RestSerializer());
        server.setValidator(new RestValidator());
        server.setHandler(new RestHandler());
        server.addTypes(RequestType.GET, RequestType.POST);
        server.start();
        long wait = 1;
        while (server.isStarted()) {
            if (wait-- == 0) {
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        server.stop();
    }

}
