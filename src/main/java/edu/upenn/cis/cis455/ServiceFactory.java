package edu.upenn.cis.cis455;

import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cis.cis455.m1.server.interfaces.WebService;
import edu.upenn.cis.cis455.m1.server.interfaces.HttpRequestHandler;
import edu.upenn.cis.cis455.m1.server.interfaces.Request;
import edu.upenn.cis.cis455.m1.server.interfaces.Response;
import edu.upenn.cis.cis455.m2.server.interfaces.Session;


public class ServiceFactory {

    /**
     * Get the HTTP server associated with port 45555
     */
    public static WebService getServerInstance() {
        return null;
    }
    
    /**
     * Create an HTTP request given an incoming socket
     */
    public static Request createRequest(Socket socket,
                         String uri,
                         boolean keepAlive,
                         Map<String, String> headers,
                         Map<String, List<String>> parms) {
        return null;
    }
    
    /**
     * Gets a request handler for files (i.e., static content) or dynamic content
     */
    public static HttpRequestHandler createRequestHandlerInstance(Path serverRoot) {
        return null;
    }

    /**
     * Gets a new HTTP Response object
     */
    public static Response createResponse() {
        return null;
    }

    /**
     * Creates a blank session ID and registers a Session object for the request
     */
    public static String createSession() {
        return null;
    }
    
    /**
     * Looks up a session by ID and updates / returns it
     */
    public static Session getSession(String id) {
        
        return null;
    }
}