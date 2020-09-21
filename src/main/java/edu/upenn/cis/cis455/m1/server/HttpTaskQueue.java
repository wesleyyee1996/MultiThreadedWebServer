package edu.upenn.cis.cis455.m1.server;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Stub class for implementing the queue of HttpTasks
 */
public class HttpTaskQueue {
	
	public Queue<HttpTask> taskQueue = new LinkedList<HttpTask>();
	
	public HttpTaskQueue (){}
	
	public void addTask (HttpTask task) {
		taskQueue.add(task);
	}
	
	public HttpTask getHeadTask () {
		return taskQueue.poll();
	}
	
}
