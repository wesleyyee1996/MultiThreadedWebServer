package edu.upenn.cis.cis455.m1.server.implementations;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.server.interfaces.HttpRequestHandler;
import edu.upenn.cis.cis455.m1.server.interfaces.Request;
import edu.upenn.cis.cis455.m1.server.interfaces.Response;

public class MockRequestHandler implements HttpRequestHandler {

    @Override
    public void handle(Request request, Response response) throws HaltException {
        response.body("<html><head><title>Response</title></head><body><h1>Response</h1><p>Test</p></body></html>");
        response.status(200);
        response.type("text/html");
    }
}
