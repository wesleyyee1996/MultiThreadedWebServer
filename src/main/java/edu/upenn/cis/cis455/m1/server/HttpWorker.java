package edu.upenn.cis.cis455.m1.server;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.interfaces.GetRequest;
import edu.upenn.cis.cis455.m1.interfaces.GetResponse;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.ResponseFactory;
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
    		// Dequeue task from Task Queue
    		
    		
    		HttpIoHandler httpHandler = new HttpIoHandler(socket, httpTask);
    		
    		// Parse input stream
    		httpHandler.parseInputStream();
    		
    		// Handle request
    		httpHandler.handleRequest();    		
        	
        	socket.close();
    	}
    	catch (IOException e){
    		System.out.println(e);
    	}  	    	
    	
    	// Call some type of RequestHandler to handle the request
    }
    
	/** Uses RequestFactory to create and get a new Request based on requestType
	 * @param requestType
	 * @return
	 * @throws IOException 
	 */

}
