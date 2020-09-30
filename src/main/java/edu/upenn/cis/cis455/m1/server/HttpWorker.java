package edu.upenn.cis.cis455.m1.server;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.interfaces.GetRequest;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.server.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Stub class for a thread worker that handles Web requests
 */
public class HttpWorker implements Runnable {

	static final Logger logger = LogManager.getLogger(HttpWorker.class);
		
	private HttpTask _httpTask;
	//private Socket _socket;
	private Hashtable<String,String> _headers;
	private final HttpTaskQueue _httpTaskQueue;
	public static String threadStatus;
	
	public HttpWorker (HttpTaskQueue httpTaskQueue) {
		//this._socket = _httpTask.getSocket();
		this._httpTaskQueue = httpTaskQueue;
		System.out.println("worker started");
	}
	
    @Override
    public void run() {
        // TODO Auto-generated method stub
    	InputStream inputStream = null;
    	OutputStream outputStream = null;
    	
    	while(true) {
    		// Use HttpIoHandler to parse socket data
        	try {
        		// Dequeue task from Task Queue
        		HttpTask _httpTask = _httpTaskQueue.popTask();
        		
        		// HttpIoHandler parses data on socket, creates Request   		
        		HttpIoHandler httpHandler = new HttpIoHandler(_httpTask.getSocket(), _httpTask);
        		
        		// Handle request
        		httpHandler.handleRequest();    		
            	
            	_httpTask.getSocket().close();
            	threadStatus = "waiting";
        	}
        	catch (InterruptedException e){
        		System.out.println(e);
        	}  	    	
        	catch (IOException ex ) {
        		System.out.println(ex);
        	}
    	}
    	
    }
    
    public static String workerStatus() {
    	return threadStatus;
    }
    
//    private HttpTask readFromQueue() throws InterruptedException {
//    	while (true) {
//    		synchronized (_httpTaskQueue) {
//    			if (_httpTaskQueue.isEmpty()) {
//    				logger.debug("Queue is currently empty");
//    				_httpTaskQueue.wait();
//    			}
//    			else {
//    				HttpTask task = _httpTaskQueue.popTask();
//    				logger.debug("Removed task from queue and notifying other Workers");
//    				task.notifyAll();
//    				logger.debug("Exiting queue with return");
//    				return task;
//    			}
//    		}
//    	}
//    }
    
	/** Uses RequestFactory to create and get a new Request based on requestType
	 * @param requestType
	 * @return
	 * @throws IOException 
	 */

}
