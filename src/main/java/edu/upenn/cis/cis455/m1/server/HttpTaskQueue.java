package edu.upenn.cis.cis455.m1.server;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;

/**
 * Stub class for implementing the queue of HttpTasks
 */
public class HttpTaskQueue {
	final static Logger logger = LogManager.getLogger(HttpTaskQueue.class);
	
	private final Queue<HttpTask> _taskQueue = new LinkedList<HttpTask>();
	private int _maxNumTasks;
	
	public HttpTaskQueue (int maxNumTasks){
		this._maxNumTasks = maxNumTasks;
	}
	
	public synchronized void addTask (HttpTask task) throws InterruptedException {
		while(true) {
			synchronized(_taskQueue) {
				if (_taskQueue.size() == _maxNumTasks) {
					logger.debug("Queue full");
					_taskQueue.wait();
				}
				else{
					_taskQueue.offer(task);
					logger.debug("Added task to queue");
					_taskQueue.notifyAll();
					break;
				}
			}
		}
		
	}
	
	public synchronized HttpTask popTask() throws InterruptedException {
		while(true) {
			synchronized (_taskQueue) {
				if(_taskQueue.isEmpty()) {
					logger.debug("Queue currently empty");
					wait();
				}
				else {
					logger.debug("Grabbing task from queue");
					HttpTask task =  _taskQueue.poll();
					logger.debug("Grabbed task from queue");
					_taskQueue.notifyAll();
					return task;
				}
			}
		}
	}
		
	public boolean isEmpty() {
		if (_taskQueue.size() == 0) {
			return true;
		}
		return false;
	}
	
	public int getSize() {
		return _taskQueue.size();
	}
}
