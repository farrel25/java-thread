package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    @Test
    void testExecutorService() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable in thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void testExecutorServiceFix() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable in thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
