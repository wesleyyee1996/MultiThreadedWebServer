package edu.upenn.cis.cis455.m1.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	HttpTaskQueue _taskQueue;
	
	public ThreadPool(int numQueueTasks, int numThreads) {
		//this._pool = Executors.newFixedThreadPool(threadPoolSize);
		_taskQueue = new HttpTaskQueue(numQueueTasks);
		
		while()
	}
	
	public void addTask(HttpTask task) throws InterruptedException {
		_taskQueue.addTask(task);
		
	}
	
//	public void executeTask(HttpWorker worker) {
//		_pool.execute(worker);
//	}
//	
//	public void shutdownPool () {
//		_pool.shutdown();
//	}

}
