package edu.upenn.cis.cis455.m1.server;

public class ThreadPool {
	
	HttpTaskQueue _taskQueue;
	
	public ThreadPool(int numQueueTasks, int numThreads) {
		//this._pool = Executors.newFixedThreadPool(threadPoolSize);
		_taskQueue = new HttpTaskQueue(numQueueTasks);
		executeThreads(numThreads);
	}
	
	public void addTask(HttpTask task) throws InterruptedException {
		_taskQueue.addTask(task);
		
	}
	
	private void executeThreads(int numThreads) {
		for (int i = 0; i < numThreads; i++) {
			HttpWorker worker = new HttpWorker(_taskQueue);   
			
			worker.run();
		}
	}
	
//	public void executeTask(HttpWorker worker) {
//		_pool.execute(worker);
//	}
//	
//	public void shutdownPool () {
//		_pool.shutdown();
//	}

}
