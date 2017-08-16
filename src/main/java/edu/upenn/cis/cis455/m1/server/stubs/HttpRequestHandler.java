package edu.upenn.cis.cis455.m1.server.stubs;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.server.stubs.Request;
import edu.upenn.cis.cis455.m1.server.stubs.Response;

@FunctionalInterface
public interface HttpRequestHandler {
    public void handle(Request request, Response response) throws HaltException;
}
