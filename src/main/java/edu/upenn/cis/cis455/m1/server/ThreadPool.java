package edu.upenn.cis.cis455.m1.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	ExecutorService pool;
	
	public ThreadPool(int threadPoolSize) {
		this.pool = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	public void executeTask(HttpWorker worker) {
		pool.execute(worker);
	}
	
	public void shutdownPool () {
		pool.shutdown();
	}

}
