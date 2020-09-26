package edu.upenn.cis.cis455.m1.handling;

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.GetRequest;
import edu.upenn.cis.cis455.m1.interfaces.GetResponse;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.ResponseFactory;
import edu.upenn.cis.cis455.m1.server.HttpTask;

/**
 * Handles marshalling between HTTP Requests and Responses
 */
public class HttpIoHandler {
    final static Logger logger = LogManager.getLogger(HttpIoHandler.class);

    public Hashtable<String,String> parsedHeaders;
    public String uri;
    private InetAddress remoteIp;
    private Socket socket;
    private HttpTask httpTask;
    
    public HttpIoHandler(Socket socket, HttpTask task) {
    	this.socket = socket;
    	this.httpTask = task;
    }
    
    public void parseInputStream() {
		try {
//	    	InputStreamReader reader = new InputStreamReader(socket.getInputStream());
//	    	BufferedReader in = new BufferedReader(reader);
	    	Hashtable<String, String> headers = new Hashtable<String,String>();
	    	Hashtable<String, List<String>> parms = new Hashtable<String,List<String>>();
	    	String remoteIp = "";
	    	if (socket.getInetAddress() != null) {
	    		remoteIp = socket.getInetAddress().toString();
	    	}
	    	uri = HttpParsing.parseRequest(remoteIp, socket.getInputStream(), headers, parms);
	    	this.parsedHeaders = headers;
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading socket input stream" + e.toString());
		}
    }
    
    public void handleRequest() throws IOException {
    	Request request = createRequest();
		
		// Call Request Handler
		RequestHandler requestHandler = new RequestHandler(request);
		String responseBody = requestHandler.responseBody;
    	
		// Write output to socket
    	OutputStream outputStream = socket.getOutputStream();
    	outputStream.write(responseBody.getBytes());   	    	
    }
    
    public Request createRequest() throws IOException {
		RequestFactory requestFactory = new RequestFactory();
    	return requestFactory.getRequest(parsedHeaders, httpTask, uri);
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
