package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest {

    @Test
    void delayedJob() throws InterruptedException {
        //ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(
                () -> System.out.println("Hello Scheduled"),
                3,
                TimeUnit.SECONDS
        );

        System.out.println(future.getDelay(TimeUnit.MILLISECONDS));

        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    void periodicJob() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.scheduleAtFixedRate(
                () -> System.out.println("Hello Scheduled"),
                2,
                2,
                TimeUnit.SECONDS
        );

        System.out.println(future.getDelay(TimeUnit.MILLISECONDS));

        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
