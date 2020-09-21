package edu.upenn.cis.cis455.m1.handling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.GetRequest;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.Response;

/**
 * Handles marshalling between HTTP Requests and Responses
 */
public class HttpIoHandler {
    final static Logger logger = LogManager.getLogger(HttpIoHandler.class);

    
    public Request createRequest(Socket socket) {
    	
    	InputStream inputStream = null;
    	OutputStream outputStream = null;
    	
    	Request request = new GetRequest();
    	
    	return request;
    }
    
    public String readInputStream(Socket socket) {
    	String request = "";
		try {
	    	InputStreamReader reader = new InputStreamReader(socket.getInputStream());
	    	BufferedReader in = new BufferedReader(reader);
	    	while((request = in.readLine()) != null) {
	    		
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading socket input stream" + e.toString());
		}
    	return request;
    }
    
    /**
     * Sends an exception back, in the form of an HTTP response code and message.
     * Returns true if we are supposed to keep the connection open (for persistent
     * connections).
     */
    public static boolean sendException(Socket socket, Request request, HaltException except) {
        return true;
    }

    /**
     * Sends data back. Returns true if we are supposed to keep the connection open
     * (for persistent connections).
     */
    public static boolean sendResponse(Socket socket, Request request, Response response) {
        return true;
    }
}
