package edu.upenn.cis.cis455.m1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Stub for your HTTP server, which listens on a ServerSocket and handles
 * requests
 */
public class HttpListener implements Runnable {

	private int port;
	private String root_dir;
	private ServerSocket serverSocket;
	
	public HttpListener(int port, String root_dir) throws IOException {
		this.port = port;
		this.root_dir = root_dir;
		this.serverSocket = new ServerSocket(this.port);
	}
	
    @Override
    public void run() {
        // TODO Auto-generated method stub
    	
    	try {
    	    // Make sure socket is bound to an address and has not already been closed
    		while(serverSocket.isBound() && !serverSocket.isClosed()) {
        		Socket socket = serverSocket.accept();
        		
        		HttpTask task = new HttpTask(socket);
        		
        		HttpTaskQueue taskQueue = new HttpTaskQueue();
        		taskQueue.addTask(task);
        		
        		
        	}
    	}
    	catch (IOException ex){
    		// TODO Add logging functionality
    		System.out.println("Error creating connection" + ex);
    	}
    	
    	
    }
}
