package edu.upenn.cis.cis455.m1.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.upenn.cis.cis455.ServiceFactory.*;

import edu.upenn.cis.cis455.ServiceFactory;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.server.interfaces.Request;
import edu.upenn.cis.cis455.m1.server.interfaces.Response;
import edu.upenn.cis.cis455.util.HttpParsing;

/**
 * Handles marshalling between HTTP Requests and Responses
 */
public class HttpIoHandler {
    final static Logger logger = LogManager.getLogger(HttpIoHandler.class);

    /**
     * Sends an exception back, in the form of an HTTP response code and message.  Returns true
     * if we are supposed to keep the connection open (for persistent connections).
     */
    public static boolean sendException(Socket socket, Request request, HaltException except) {
        return true;
    }

    /**
     * Sends data back.   Returns true if we are supposed to keep the connection open (for 
     * persistent connections).
     */
    public static boolean sendResponse(Socket socket, Request request, Response response) {
        return true;
    }
}
