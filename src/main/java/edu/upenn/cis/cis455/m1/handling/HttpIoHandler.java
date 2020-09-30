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
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.SocketOutputBodyBuilder;
import edu.upenn.cis.cis455.m1.interfaces.SuccessResponse;
import edu.upenn.cis.cis455.m1.server.HttpTask;
import edu.upenn.cis.cis455.m1.server.HttpWorker;

/**
 * Handles marshalling between HTTP Requests and Responses
 */
public class HttpIoHandler {
    final static Logger logger = LogManager.getLogger(HttpIoHandler.class);

    public Hashtable<String,String> _parsedHeaders;
    public String _uri;
    private InetAddress _remoteIp;
    private Socket _socket;
    private HttpTask _httpTask;
    
    public HttpIoHandler(Socket socket, HttpTask task) {
    	this._socket = socket;
    	this._httpTask = task;
    }
    
    public void handleRequest() throws IOException {
    	
		// Parses the input stream and sets values to _parsedHeaders and _uri
    	parseInputStream();
    	
    	// Creates a new request based on type w/ RequestFactory
    	Request request = createRequest();
    	HttpWorker.threadStatus = request.url();
    	SuccessResponse successResponse = new SuccessResponse();
    	
		// Call Request Handler to handle the request
		RequestHandler requestHandler = new RequestHandler();
		requestHandler.handleRequest(request, successResponse, _socket);
		
		// Build the output to the socket
		SocketOutputBodyBuilder socketOutputBuilder = new SocketOutputBodyBuilder();
		byte[] socketOutputBytes = socketOutputBuilder.buildSocketOutput(successResponse);
		sendResponse(_socket, socketOutputBytes);
    		
    }
    
    /**
     * Parses the socket's InputStream and gets the headers as well as the uri
     */
    private void parseInputStream() {
		try {
//	    	InputStreamReader reader = new InputStreamReader(socket.getInputStream());
//	    	BufferedReader in = new BufferedReader(reader);
	    	Hashtable<String, String> headers = new Hashtable<String,String>();
	    	Hashtable<String, List<String>> parms = new Hashtable<String,List<String>>();
	    	String remoteIp = "";
	    	if (_socket.getInetAddress() != null) {
	    		remoteIp = _socket.getInetAddress().toString();
	    	}
	    	_uri = HttpParsing.parseRequest(remoteIp, _socket.getInputStream(), headers, parms);
	    	this._parsedHeaders = headers;
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading socket input stream" + e.toString());
		}
    }
    
    private Request createRequest() throws IOException {
		RequestFactory requestFactory = new RequestFactory();
    	return requestFactory.getRequest(_parsedHeaders, _httpTask, _uri);
	}

    /**
     * Sends an exception back, in the form of an HTTP response code and message.
     * Returns true if we are supposed to keep the connection open (for persistent
     * connections).
     * @throws IOException 
     */
    public static boolean sendException(Socket socket, Request request, HaltException except) throws IOException {
    	if (!request.persistentConnection()) {
    		OutputStream outputStream = socket.getOutputStream();
    		outputStream.write(except.body().getBytes());
    	}
    	return true;
    }

    /**
     * Sends data back. Returns true if we are supposed to keep the connection open
     * (for persistent connections).
     * @throws IOException 
     */
    //public static boolean sendResponse(Socket socket, Request request, byte[] socketOutputBytes) throws IOException {
	public static boolean sendResponse(Socket socket, byte[] socketOutputBytes) throws IOException {
    	//if (!request.persistentConnection()) {
    		// Write output to socket
        	OutputStream outputStream = socket.getOutputStream();
        	outputStream.write(socketOutputBytes);  
    	//}
    	return true;
        
    }
}
