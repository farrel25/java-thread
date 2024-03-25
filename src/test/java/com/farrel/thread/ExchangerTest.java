package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    @Test
    void test() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Thread 1 Send: First");
                String result = exchanger.exchange("First");
                System.out.println("Thread 1 Receive: " + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.execute(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Thread 2 Send: Second");
                String result = exchanger.exchange("Second");
                System.out.println("Thread 2 Receive: " + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.awaitTermination(6, TimeUnit.SECONDS);
    }
}
