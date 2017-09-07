package edu.upenn.cis.cis455.m1.server;

/**
 * Stub for your HTTP server, which
 * listens on a ServerSocket and handles
 * requests
 */
public class HttpServer implements ThreadManager {

    @Override
    public HttpTaskQueue getRequestQueue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void start(HttpWorker worker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void done(HttpWorker worker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void error(HttpWorker worker) {
        // TODO Auto-generated method stub
        
    }
}
