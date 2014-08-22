package edu.exersises.loadbalancer.edu.exersises.loadbalancer.http;

import edu.exersises.loadbalancer.util.edu.exersises.loadbalancer.configdata.ConfigData;

import java.util.concurrent.*;

/**
 * Created by rsmirnov on 8/13/14.
 */
public class ExecutorServiceProvider {
    private static ExecutorServiceProvider executorServiceProvider = null;
    private ThreadPoolExecutor executorService = null;
    private BlockingQueue queue = null;

    private ExecutorServiceProvider() {
    }

    private ExecutorServiceProvider(int initialThreadsNumber, int maxThreadsNumber) {
        this.queue = new ArrayBlockingQueue<Runnable>(maxThreadsNumber);
        this.executorService = new ThreadPoolExecutor(initialThreadsNumber, maxThreadsNumber,
                0L, TimeUnit.MILLISECONDS, queue);
    }

    public static ExecutorServiceProvider getInstance(int initialThreadsNumber, int maxThreadsNumber) {
        if (executorServiceProvider == null) {
            synchronized(ExecutorServiceProvider.class) {
                if (executorServiceProvider == null) {
                    executorServiceProvider = new ExecutorServiceProvider(initialThreadsNumber, maxThreadsNumber);
                }
            }
        }
        return executorServiceProvider;

    }

    public ThreadPoolExecutor getExecutorService() {
        return this.executorService;
    }
}

