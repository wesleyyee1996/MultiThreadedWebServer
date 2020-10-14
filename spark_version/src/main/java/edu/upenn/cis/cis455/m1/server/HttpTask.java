package edu.upenn.cis.cis455.m1.server;

import java.net.Socket;

import edu.upenn.cis.cis455.m2.interfaces.Request;

public class HttpTask {
    private Socket requestSocket;
    private int port;
    private String root_dir;
    private Request request;
    
    public HttpTask(Socket socket, int port, String root_dir) {
        requestSocket = socket;
        this.port = port;
        this.root_dir = root_dir;
    }
    
    public HttpTask() {}

    public Socket getSocket() {
        return requestSocket;
    }
    
    public String getRootDir() {
    	return root_dir;
    }
    
    public Request getRequest() {
    	return request;
    }
    
    public int getPort() {
    	return port;
    }
}
