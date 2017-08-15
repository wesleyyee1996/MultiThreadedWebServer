package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.stubs.Request;
import edu.upenn.cis.cis455.webserver.stubs.Response;

@FunctionalInterface
public interface Filter {
    void handle(Request request, Response response) throws Exception;
}
