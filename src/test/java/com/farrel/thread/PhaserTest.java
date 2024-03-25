package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class PhaserTest {

    @Test
    void countDownLatch() throws InterruptedException {
        Phaser phaser = new Phaser();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    System.out.println("Start Task-" + finalI);
                    Thread.sleep(2000);
                    System.out.println("Finish Task-" + finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    phaser.arrive();
                }
            });
        }

        executor.execute(() -> {
            try {
                phaser.awaitAdvance(0);
                Thread.sleep(1000);
                System.out.println("All Tasks Finished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    void cyclicBarrier() throws InterruptedException {
        Phaser phaser = new Phaser();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executor.execute(() -> {
                phaser.arriveAndAwaitAdvance();
                System.out.println("Done Task-" + finalI);
            });
        }

        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
