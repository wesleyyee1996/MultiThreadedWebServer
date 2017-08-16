package edu.upenn.cis.cis455.m1.server;

/**
 * Interface for coordination between worker threads
 * and thread control
 */
public interface ThreadManager {
    
    /**
     * Get access to the request queue, to
     * poll, sleep, etc.
     */
    public HttpTaskQueue getRequestQueue();
    
    /**
     * Is the Web server still active, or
     * do we need to shut down?
     */
    public boolean isActive();
    
    /**
     * Tell the coordinator we have started
     * a task
     */
    public void start(HttpWorker worker);
    
    /**
     * Tell the coordinator we have completed
     * a task
     */
    public void done(HttpWorker worker);
    
    /**
     * Tell the coordinator we failed a task 
     */
    public void error(HttpWorker worker);
}
