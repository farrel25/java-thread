package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    void create() {

        int corePool = 10;
        int maxPool = 100;
        long keepAliveTime = 1;
        TimeUnit timeUnit = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
        
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePool, maxPool, keepAliveTime, timeUnit, queue);
    }

    @Test
    void executeRunnable() throws InterruptedException {

        int corePool = 10;
        int maxPool = 100;
        long keepAliveTime = 1;
        TimeUnit timeUnit = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePool, maxPool, keepAliveTime, timeUnit, queue);

        Runnable runnable = () -> {
            try {
                Thread.sleep(5_000);
                System.out.println("Runnable from thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        executor.execute(runnable);

        Thread.sleep(6_000);
    }

    @Test
    void shutdown() throws InterruptedException {

        int corePool = 10;
        int maxPool = 100;
        long keepAliveTime = 1;
        TimeUnit timeUnit = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1_000);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePool, maxPool, keepAliveTime, timeUnit, queue);

        for (int i = 0; i < 1000; i++) {
            final int task = i;

            Runnable runnable = () -> {
                try {
                    Thread.sleep(1_000);
                    System.out.println("Task " + task + " from thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            executor.execute(runnable);
        }

        //executor.shutdown();
        //executor.shutdownNow();

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void rejected() throws InterruptedException {

        int corePool = 10;
        int maxPool = 100;
        long keepAliveTime = 1;
        TimeUnit timeUnit = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        logRejectedExecutionHandler rejectedExecutionHandler = new logRejectedExecutionHandler();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePool, maxPool, keepAliveTime, timeUnit, queue, rejectedExecutionHandler);

        for (int i = 0; i < 1000; i++) {
            final int task = i;

            Runnable runnable = () -> {
                try {
                    Thread.sleep(1_000);
                    System.out.println("Task " + task + " from thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            executor.execute(runnable);
        }

        //executor.shutdown();
        //executor.shutdownNow();

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    public static class logRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task " + r + " is rejected");
        }
    }
}
