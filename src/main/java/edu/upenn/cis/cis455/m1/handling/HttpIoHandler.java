package edu.upenn.cis.cis455.m1.handling;

import java.util.Hashtable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.GetRequest;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.Response;

/**
 * Handles marshalling between HTTP Requests and Responses
 */
public class HttpIoHandler {
    final static Logger logger = LogManager.getLogger(HttpIoHandler.class);

    public Hashtable<String,String> parsedHeaders;
    public String requestLine;
    public Hashtable<String,String> parsedRequestLine;
    
    public void parseInputStream(Socket socket) {
		try {
	    	InputStreamReader reader = new InputStreamReader(socket.getInputStream());
	    	BufferedReader in = new BufferedReader(reader);
	    	parseRequests(in);
	    	parseRequestLine(requestLine);
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading socket input stream" + e.toString());
		}
    }
    
    
    /** Parses HTTP request line by line
     * @param buffReader
     * @throws IOException
     */
    public void parseRequests(BufferedReader buffReader) throws IOException {
    	
    	// Get the request line
    	requestLine = buffReader.readLine();
    	
    	String header;
    	// If there is still another line in the Buffered Reader, then keep reading
    	while((header = buffReader.readLine()) != null) {
    		parseHeaders(header);
    		
    	}
    	    	
    }
    
    /** Parses each header after request line and adds parameters to a Hashtable
     * @param header
     * @throws IOException
     */
    public void parseHeaders (String header) throws IOException {
    	// Split based on colon of header
    	int index = header.indexOf(":");
    	if (index == -1) {
    		throw new IOException("Invalid Header: " + header);
    	}
    	
    	// Add parsed headers to Hashtable
    	parsedHeaders.put(header.substring(0,index),header.substring(index+1, header.length()));
    }
    
    
    
    /** Parses request line and adds parameters to a Hashtable
     * @param requestLine
     * @throws IOException
     */
    public void parseRequestLine (String requestLine) throws IOException {
    	try {
    		String[] requestParams = requestLine.split(" ");
    		parsedRequestLine.put(Constants.Method, requestParams[0]);
    		parsedRequestLine.put(Constants.Uri, requestParams[1]);
    		parsedRequestLine.put(Constants.HttpVersion, requestParams[2]);
    	}
    	catch (Exception e){
    		System.out.println("Error parsing request line: " + e);
    	}
    }
    
//    public String getRequestType() throws IOException {
//    	try {
//    		return parsedRequestLine.get(Constants.Method);
//    	}
//    	catch (Exception e){
//    		System.out.println("Error getting request type: " + e);
//    	}
//    }
//    
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
