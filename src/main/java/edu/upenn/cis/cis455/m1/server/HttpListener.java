package edu.upenn.cis.cis455.m1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import edu.upenn.cis.cis455.m1.server.*;

/**
 * Stub for your HTTP server, which listens on a ServerSocket and handles
 * requests. 
 * This is the actual HTTP server w/ ThreadPool and thread management
 * - Creates and manages thread pool
 * - Listens on sockets
 * - Invokes Handlers
 */
public class HttpListener implements Runnable {

	private int port;
	private String root_dir;
	private ServerSocket serverSocket;
	int numQueueTasks = 10;
	int numThreads = 10;
	
	public HttpListener(int port, String root_dir) throws IOException {
		this.port = port;
		this.root_dir = root_dir;
		this.serverSocket = new ServerSocket(this.port);
		System.out.println("Listener started");
	}
	
    @Override
    public void run() {
        // TODO Auto-generated method stub
    	
    	try {
    		System.out.println("running listener");
    	    // Make sure socket is bound to an address and has not already been closed
    		while(serverSocket.isBound() && !serverSocket.isClosed()) {
        		System.out.println("in serverSocket loop");
        		
    			Socket socket = serverSocket.accept();
        		System.out.println("accepted socket");
    			
        		HttpTask task = new HttpTask(socket, port, root_dir);
        		
        		//HttpTaskQueue taskQueue = new HttpTaskQueue();
        		//taskQueue.addTask(task);
        		
        		//TODO: use thread pool to assign worker to task from task queue
        		ThreadPool threadPool = new ThreadPool(numQueueTasks, numThreads);
        		
        		HttpWorker worker = new HttpWorker(task);    
        		worker.run();
        		
        		serverSocket.close();
        	}
    		System.out.println("out of listener loop");
    	}
    	catch (IOException ex){
    		// TODO Add logging functionality
    		System.out.println("Error creating connection" + ex);
    	}
    	
    	
    }
}
