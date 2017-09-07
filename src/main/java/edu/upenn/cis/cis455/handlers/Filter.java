package edu.upenn.cis.cis455.handlers;

import edu.upenn.cis.cis455.m1.server.interfaces.Request;
import edu.upenn.cis.cis455.m1.server.interfaces.Response;

/**
 * A Filter is called by the Web server to process data before the Route
 * Handler is called.  This is typically used to attach attributes ()
 * or to call the HaltException, e.g., if the user is not authorized.
 */
@FunctionalInterface
public interface Filter {
    void handle(Request request, Response response) throws Exception;
}
