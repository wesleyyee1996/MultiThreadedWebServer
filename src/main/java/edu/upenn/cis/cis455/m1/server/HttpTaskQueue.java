package edu.upenn.cis.cis455.m1.server;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Stub class for implementing the queue of HttpTasks
 */
public class HttpTaskQueue {
	
	private Queue<HttpTask> _taskQueue = new LinkedList<HttpTask>();
	private int _maxNumTasks;
	
	
	public HttpTaskQueue (int maxNumTasks){
		this._maxNumTasks = maxNumTasks;
	}
	
	public synchronized void addTask (HttpTask task) throws InterruptedException {
		_taskQueue.add(task);
		while(_taskQueue.size() == _maxNumTasks) {
			wait();
		}
		if (_taskQueue.size() < _maxNumTasks) {
			notify();
		}
		if (_taskQueue.size() == 0) {
			notifyAll();
		}
		_taskQueue.offer(task);
	}
	
	public synchronized HttpTask popTask() throws InterruptedException {
		while(_taskQueue.size() == 0) {
			wait();
		}
		if(_taskQueue.size() > 0) {
			notify();
		}
		if (_taskQueue.size() == _maxNumTasks) {
			notifyAll();
		}
		return _taskQueue.poll();
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
