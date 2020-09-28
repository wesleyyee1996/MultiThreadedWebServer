package edu.upenn.cis.cis455.m1.server;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpWorkerProducer implements Runnable{
	static final Logger logger = LogManager.getLogger(HttpWorkerProducer.class);
	
	private final LinkedList<HttpTask> _taskQueue;
	private final int _queueSize;
	private HttpTask _task;
	
	public HttpWorkerProducer(LinkedList<HttpTask> queue, int size, HttpTask task) {
		this._taskQueue = queue;
		this._queueSize = size;
		this._task = task;
	}
	
	public void run() {
		while (true) {
			try {
				logger.debug("HttpWorkerProducer pushed a task onto the HttpTaskQueue");
				addToQueue(_task);
			}
			catch (InterruptedException ex) {
				logger.error("Interrupt Exception in HttpWorkerProducer thread: " + ex);
			}
		}
	}
	
	private void addToQueue(HttpTask task) throws InterruptedException {
		logger.info("Adding element to queue");
		while(true) {
			synchronized (_taskQueue) {
				if (_taskQueue.size() == _queueSize) {
					logger.debug("Queue full");
					_taskQueue.wait();
				} else {
					_taskQueue.add(task);
					logger.debug("Notifying after add");
					_taskQueue.notifyAll();
					break;
				}
			}
		}
	}
}
