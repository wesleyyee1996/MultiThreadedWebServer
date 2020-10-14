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
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m1.server.HttpTask;
import edu.upenn.cis.cis455.m1.server.HttpWorker;
import edu.upenn.cis.cis455.m1.server.RequestFactory;
import edu.upenn.cis.cis455.m1.server.RequestObj;
import edu.upenn.cis.cis455.m1.server.ResponseObj;
import edu.upenn.cis.cis455.m2.server.WebService;
import edu.upenn.cis.cis455.utils.SocketOutputBodyBuilder;

/**
 * Handles marshalling between HTTP Requests and Responses
 */
public class HttpIoHandler {
    final static Logger logger = LogManager.getLogger(HttpIoHandler.class);

    private ResponseObj successResponse = new ResponseObj();
    public Hashtable<String,String> _parsedHeaders = new Hashtable<String,String>();
    public Hashtable<String,List<String>> _parsedQueryParams = new Hashtable<String,List<String>>();
    public String _uri;
    private InetAddress _remoteIp;
    private Socket _socket;
    private HttpTask _httpTask;
    
    public HttpIoHandler(Socket socket, HttpTask task) {
    	this._socket = socket;
    	this._httpTask = task;
    }
    
    public void handleRequest() throws HaltException, Exception {
    	
		// Parses the input stream and sets values to _parsedHeaders and _uri
    	try {
			parseInputStream();
		} catch (HaltException e1) {
			logger.error("Error reading from socket input stream");
			Request request = new RequestObj();
			sendException(_socket, request, e1);
		}
    	
    	// Creates a new request based on type w/ RequestFactory
    	Request request = createRequest();
    	WebService.getInstance().threadStatuses.put(Thread.currentThread().getName(),request.uri());
    	logger.info(request.requestMethod() +" "+ request.uri());
    	
		// Call Request Handler to handle the request
		RequestHandler requestHandler = new RequestHandler(request, successResponse, _socket);
		try {
			requestHandler.handleRequest();
			outputSuccessToSocket(successResponse);
		} catch (HaltException he) {
			logger.error("Halt Exception called with status code "+he.statusCode());
			sendException(_socket, request, he);
		} catch (IOException e) {
			logger.error("Error reading request or outputting to socket stream: "+e);
			HaltException he = new HaltException(500);
			sendException(_socket, request, he);
		} catch (Exception e) {
			logger.error("An unknown server error occurred: "+e);
			HaltException he = new HaltException(500);
			sendException(_socket, request, he);
		}		
    }
    
    /**
     * Parses the socket's InputStream and gets the headers as well as the uri
     */
    private void parseInputStream() throws HaltException {
		try {
			
	    	Hashtable<String, String> headers = new Hashtable<String,String>();
	    	Hashtable<String, List<String>> parms = new Hashtable<String,List<String>>();
	    	
	    	// Get the client's IP address if available
	    	String remoteIp = "";
	    	if (_socket.getInetAddress() != null) {
	    		remoteIp = _socket.getInetAddress().toString();
	    	}
	    	
	    	// If there was a bad request, then throw a 400 error
	    	try {
	    		_uri = HttpParsing.parseRequest(remoteIp, _socket.getInputStream(), headers, parms);
	    	} catch(HaltException he) {
	    		logger.error("Error parsing request");
	    		he.setBody(HttpParsing.explainStatus(400));
	    		he.setStatusCode(400);
	    		throw he;
	    	}
	    	
	    	this._parsedHeaders.putAll(headers);
	    	this._parsedQueryParams.putAll(parms);
	    	
		} catch (IOException e) {
			logger.error("Error reading socket input stream" + e.toString());
			throw new HaltException(500);
		} catch (HaltException he) {
			he.setStatusCode(400);
			logger.error("Bad request");
			throw he;
		}
		return true;
    }
    
    private void outputSuccessToSocket(Response response) throws IOException {
    	// Build the output to the socket
    			SocketOutputBodyBuilder socketOutputBuilder = new SocketOutputBodyBuilder();
    			byte[] socketOutputBytes = socketOutputBuilder.buildSocketOutput(response);
    			sendResponse(_socket, socketOutputBytes);
    }
    
    // Use the RequestFactory to build a request
    private Request createRequest() throws IOException {
		RequestFactory requestFactory = new RequestFactory();
    	return requestFactory.getRequest(_parsedHeaders, _httpTask, _uri, _parsedQueryParams);
	}

    /**
     * Sends an exception back, in the form of an HTTP response code and message.
     * Returns true if we are supposed to keep the connection open (for persistent
     * connections).
     * @throws IOException 
     */
    public static boolean sendException(Socket socket, Request request, HaltException except) throws IOException {
    	if (!request.persistentConnection()) {
    		SocketOutputBodyBuilder socketOutputBuilder = new SocketOutputBodyBuilder();
    		byte[] socketOutputBytes = socketOutputBuilder.buildSocketOutput(except);
    		logger.debug("Sending exception");
    		OutputStream outputStream = socket.getOutputStream();
    		outputStream.write(socketOutputBytes);
    	}
    	return true;
    }

    /**
     * Sends data back. Returns true if we are supposed to keep the connection open
     * (for persistent connections).
     * @throws IOException 
     */
	public static boolean sendResponse(Socket socket, byte[] socketOutputBytes) throws IOException {
		logger.debug("Sending response");
    	OutputStream outputStream = socket.getOutputStream();
    	outputStream.write(socketOutputBytes); 
    	return true;
        
    }
}
