package edu.upenn.cis.cis455.m1.server;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.interfaces.GetRequest;
import edu.upenn.cis.cis455.m1.interfaces.GetResponse;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.server.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

/**
 * Stub class for a thread worker that handles Web requests
 */
public class HttpWorker implements Runnable {

	private HttpTask httpTask;
	private Socket socket;
	private Hashtable<String,String> headers;
	
	public HttpWorker (HttpTask task) {
		this.httpTask = task;
		this.socket = httpTask.getSocket();
		System.out.println("worker started");
	}
	
    @Override
    public void run() {
        // TODO Auto-generated method stub
    	InputStream inputStream = null;
    	OutputStream outputStream = null;
    	
    	// Use HttpIoHandler to parse socket data
    	try {
    		HttpIoHandler httpHandler = new HttpIoHandler();
    		
    		// Parse input stream
    		httpHandler.parseInputStream(socket);
    		
    		// Create a request based on the request type
    		Request request = createRequest(httpHandler.parsedHeaders, httpTask);
    		
    		String test = request.requestMethod();
    		
    	    String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my Simple Java HTTP Server"+test+"</h1></body></html>";
    		
    		String CRLF = "\r\n";
    		
        	String response = 
        			"HTTP/1.0 200 OK" + CRLF +
        			"Content-Length: " + html.getBytes().length + CRLF +
        			CRLF + 
        			html + 
        			CRLF + CRLF;
        	
        	System.out.println(response);
        	
        	outputStream.write(response.getBytes());
        	
        	inputStream.close();
        	outputStream.close();
        	socket.close();
    	}
    	catch (IOException e){
    		System.out.println(e);
    	}
//    	finally {
//    		if (inputStream != null) {
//    			try {
//    				outputStream.close();
//    			}
//    			catch (IOException e) {
//    				System.out.println(e);
//    			}
//    		}
//    		if (outputStream != null) {
//    			try {
//    				outputStream.close();
//    			} catch (IOException e) {
//    				System.out.println(e);
//    			}
//    		}
//    		if (socket != null) {
//    			try {
//    				socket.close();
//    			}
//    			catch (IOException e){
//    				System.out.println(e);
//    			}
//    		}
//    	}
    	
    	
    	
    	// Call some type of RequestHandler to handle the request
    	
    	// Create a response
    }
    
	/** Uses RequestFactory to create and get a new Request based on requestType
	 * @param requestType
	 * @return
	 * @throws IOException 
	 */
	public Request createRequest(Hashtable<String,String> parsedHeaders, HttpTask httpTask) throws IOException {
		RequestFactory requestFactory = new RequestFactory();
    	return requestFactory.getRequest(parsedHeaders, httpTask);
	}
    
    public GetResponse createGetResponse() {
    	GetResponse response = new GetResponse();
    	
    	return response;
    	
    }

}
