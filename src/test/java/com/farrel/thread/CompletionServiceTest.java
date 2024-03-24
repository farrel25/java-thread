package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceTest {

    private Random random = new Random();

    @Test
    void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executor);

        // submit task
        Executors.newSingleThreadExecutor().execute(() -> {
            for (int i = 0; i < 100; i++) {
                int finalI = i;

                completionService.submit(() -> {
                    Thread.sleep(random.nextInt(2000));

                    return "Task-" + finalI;
                });
            }
        });

        // poll task
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                try {
                    Future<String> future = completionService.poll(5, TimeUnit.SECONDS);

                    if (future == null) break;
                    else System.out.println(future.get());

                } catch (InterruptedException | ExecutionException e) {
                    break;
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
