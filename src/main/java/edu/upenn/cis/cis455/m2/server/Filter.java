package edu.upenn.cis.cis455.m2.server;

import edu.upenn.cis.cis455.m1.server.stubs.Request;
import edu.upenn.cis.cis455.m1.server.stubs.Response;

@FunctionalInterface
public interface Filter {
    void handle(Request request, Response response) throws Exception;
}
