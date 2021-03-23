package net.test;

import com.syntaxphoenix.syntaxapi.net.http.HttpWriter;
import com.syntaxphoenix.syntaxapi.net.http.ReceivedRequest;
import com.syntaxphoenix.syntaxapi.net.http.RequestContent;
import com.syntaxphoenix.syntaxapi.net.http.RequestType;
import com.syntaxphoenix.syntaxapi.net.http.RequestValidator;

public class RestValidator implements RequestValidator {

    @Override
    public RequestContent parseContent(HttpWriter writer, ReceivedRequest request) throws Exception {
        return request.getType() == RequestType.GET ? RequestContent.UNNEEDED : RequestContent.NEEDED;
    }

}
