package edu.upenn.cis.cis455.m1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.server.*;
import edu.upenn.cis.cis455.Constants;

/**
 * Stub for your HTTP server, which listens on a ServerSocket and handles
 * requests. 
 * This is the actual HTTP server w/ ThreadPool and thread management
 * - Creates and manages thread pool
 * - Listens on sockets
 * - Invokes Handlers
 */
public class HttpListener implements Runnable {
	 final static Logger logger = LogManager.getLogger(HttpListener.class);
	
	private int port;
	private String root_dir;
	private ServerSocket serverSocket;
	private final HttpTaskQueue taskQueue;
	private boolean exit = true;
	
	public HttpListener(int port, String root_dir, HttpTaskQueue taskQueue){
		this.port = port;
		this.root_dir = root_dir;
		this.taskQueue = taskQueue;
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Listener started");
	}
	
    @Override
    public void run() {
    	while(exit) {
    		try {
        		System.out.println("running listener");
        	    // Make sure socket is bound to an address and has not already been closed
        		while(serverSocket.isBound() && !serverSocket.isClosed()) {
            		System.out.println("in serverSocket loop");
            		
        			Socket socket = serverSocket.accept();
            		System.out.println("accepted socket");
        			
            		HttpTask task = new HttpTask(socket, port, root_dir);
            		
            		this.taskQueue.addTask(task);
            		System.out.println("task added to Queue");
            		
            		//serverSocket.close();
            	}
        		System.out.println("out of listener loop");
        	}
        	catch (IOException ex){
        		// TODO Add logging functionality
        		System.out.println("Error creating connection" + ex);
        	}
        	catch (InterruptedException ex){
        		System.out.println("Thread error: " + ex);
        	}
    	}
    	
    }
    
    public void closeServerSocket() throws IOException {
    	logger.info("Turning off the Listener");
    	this.exit = false;
//    	logger.info("Closing the server socket");
//    	this.serverSocket.close();
    }
}
