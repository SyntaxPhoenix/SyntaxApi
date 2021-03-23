package net.test;

import com.syntaxphoenix.syntaxapi.net.http.HttpSender;
import com.syntaxphoenix.syntaxapi.net.http.HttpWriter;
import com.syntaxphoenix.syntaxapi.net.http.JsonAnswer;
import com.syntaxphoenix.syntaxapi.net.http.ReceivedRequest;
import com.syntaxphoenix.syntaxapi.net.http.RequestHandler;
import com.syntaxphoenix.syntaxapi.net.http.ResponseCode;
import com.syntaxphoenix.syntaxapi.net.http.StandardContentType;

public class RestHandler implements RequestHandler {

    @Override
    public boolean handleRequest(HttpSender sender, HttpWriter writer, ReceivedRequest request) throws Exception {
        new JsonAnswer(StandardContentType.JSON).header("buildNumber", 0).respond("info", "Message received!").code(ResponseCode.OK)
            .write(writer);
        return true;
    }

}
