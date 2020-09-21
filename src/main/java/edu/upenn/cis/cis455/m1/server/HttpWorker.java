package edu.upenn.cis.cis455.m1.server;

/**
 * Stub class for a thread worker that handles Web requests
 */
public class HttpWorker implements Runnable {

	private HttpTask httpTask;
	
	public HttpWorker (HttpTask task) {
		this.httpTask = task;
	}
	
    @Override
    public void run() {
        // TODO Auto-generated method stub
    	
    	// Use HttpIoHandler to parse socket data
    	
    	// Create a request
    	
    	// Call some type of RequestHandler to handle the request
    	
    	// Create a response
    }
    
//    public Response createResponse() {
//    	
//    	
//    }

}
