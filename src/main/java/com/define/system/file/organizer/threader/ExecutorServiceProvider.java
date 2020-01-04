package com.define.system.file.organizer.threader;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Custom Executor Service
 *
 * @param <T> the generic type
 */
public class ExecutorServiceProvider<T> {

	/** The thread pool executor. */
	private ThreadPoolExecutor threadPoolExecutor;
	
	/**
	 * Gets the executor service.
	 *
	 * @return the executor service
	 */
	public ExecutorService getExecutorService() {
		return threadPoolExecutor = (null == threadPoolExecutor || threadPoolExecutor.isShutdown()) ? (ThreadPoolExecutor) Executors.newFixedThreadPool(5) : threadPoolExecutor;
	}

	/**
	 * Gets the executor service.
	 *
	 * @param threadCount the thread count
	 * @return the executor service
	 */
	public ExecutorService getExecutorService(int threadCount) {
		return threadPoolExecutor = (null == threadPoolExecutor || threadPoolExecutor.isShutdown()) ? (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount) : threadPoolExecutor;
	}

	/**
	 * Shutdown executor service.
	 */
	public void shutdownExecutorService() {
		if(null != threadPoolExecutor && !threadPoolExecutor.isShutdown()) {
			threadPoolExecutor.shutdown();
		}
	}

	/**
	 * Shutdown now executor service.
	 */
	public void shutdownNowExecutorService() {
		if(null != threadPoolExecutor && !threadPoolExecutor.isShutdown()) {
			threadPoolExecutor.shutdownNow();
		}
	}

	/**
	 * Gets the result.
	 *
	 * @param size the size
	 * @param executorCompletionService the executor completion service
	 * @param timeout the timeout
	 * @param timeUnit the time unit
	 * @return the result
	 */
	public Future<T> getResult(int size, final ExecutorCompletionService<T> executorCompletionService, final long timeout, final TimeUnit timeUnit) {
		Future<T> futureList = null;

		long globalWaitTime = timeUnit.toNanos(timeout);
		for(int i = 0; i < size; i++) {
			final long waitStart = System.nanoTime();

			try {
				futureList = executorCompletionService.take();
				if(futureList != null) {
					try {
						futureList.get(globalWaitTime, TimeUnit.NANOSECONDS);
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						futureList.cancel(true);
					}
				}
			} catch (InterruptedException e) {
				futureList.cancel(true);
				Thread.currentThread().interrupt();
			} finally {
				final long waitFinish = System.nanoTime() - waitStart;
				globalWaitTime = Math.max(globalWaitTime - waitFinish, 0);
			}
		}
		return futureList;
	}
	
	/**
	 * Shutdown and await termination.
	 */
	public void shutdownAndAwaitTermination() {
		threadPoolExecutor.shutdown();
	    try {
	        if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
	        	threadPoolExecutor.shutdownNow();
	            if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
	            	System.err.println("Pool did not terminate");
	            }
	        }
	    } catch (InterruptedException ie) {
	    	threadPoolExecutor.shutdownNow();
	        Thread.currentThread().interrupt();
	    }
	}
}