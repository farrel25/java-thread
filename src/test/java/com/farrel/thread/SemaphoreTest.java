package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    @Test
    void test() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(2);
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 20; i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    System.out.println("Finish-" + finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            });
        }

        executor.awaitTermination(15, TimeUnit.SECONDS);
    }
}
