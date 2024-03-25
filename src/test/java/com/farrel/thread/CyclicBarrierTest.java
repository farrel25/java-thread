package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CyclicBarrierTest {

    @Test
    void test() throws InterruptedException {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    System.out.println("Waiting");
                    cyclicBarrier.await();
                    System.out.println("Done Waiting");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
