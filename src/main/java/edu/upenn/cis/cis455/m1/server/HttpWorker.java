package edu.upenn.cis.cis455.m1.server;

//import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
//import edu.upenn.cis.cis455.m1.interfaces.GetRequest;
//import edu.upenn.cis.cis455.m1.interfaces.Request;
//import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
//import edu.upenn.cis.cis455.m1.interfaces.Response;
//import edu.upenn.cis.cis455.m1.server.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.net.Socket;
import java.util.Hashtable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Stub class for a thread worker that handles Web requests
 */
public class HttpWorker extends Thread {

	static final Logger logger = LogManager.getLogger(HttpWorker.class);
		
	private HttpTask _httpTask;
	//private Socket _socket;
	private Hashtable<String,String> _headers;
	private final HttpTaskQueue _httpTaskQueue;
	//private static String threadStatus;
	
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
    	
    	//ThreadLocal<String> threadStatus = new ThreadLocal<String>();
    	
    	while(true) {
    		// Use HttpIoHandler to parse socket data
        	try {
        		WebService.getInstance().threadStatuses.put(Thread.currentThread().getName(), "waiting");
        		// Dequeue task from Task Queue
        		HttpTask _httpTask = _httpTaskQueue.popTask();
        		
        		// HttpIoHandler parses data on socket, creates Request   		
        		HttpIoHandler httpHandler = new HttpIoHandler(_httpTask.getSocket(), _httpTask);
        		
        		// Handle request
        		httpHandler.handleRequest();    		
            	
            	_httpTask.getSocket().close();
            	WebService.getInstance().threadStatuses.put(Thread.currentThread().getName(), "waiting");
            	//threadStatus.set("waiting");
        	}
        	catch (InterruptedException e){
        		System.out.println(e);
        	}  	    	
        	catch (IOException ex ) {
        		System.out.println(ex);
        	}
    	}
    	
    }
    
//    public String workerStatus() {
//    	return threadStatus;
//    }
//    
//    public static void setWorkerStatus(String url) {
//    	threadStatus = url;
//    }

}
