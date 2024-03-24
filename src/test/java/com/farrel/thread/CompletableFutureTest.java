package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private Random random = new Random();

    public CompletableFuture<String> getValue() {
        CompletableFuture<String> future = new CompletableFuture<>();

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                future.complete("Farrel Athaillah Putra");
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    @Test
    void testCreate() throws ExecutionException, InterruptedException {
        Future<String> future = getValue();
        System.out.println(future.get());
    }

    private void execute(CompletableFuture<String> future, String value) {
        executorService.execute(() -> {
            try {
                Thread.sleep(1000 + random.nextInt(5000));
                future.complete(value);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
    }

    public Future<String> getFastest() {
        CompletableFuture<String> future = new CompletableFuture<>();

        execute(future, "Thread 1");
        execute(future, "Thread 2");
        execute(future, "Thread 3");

        return future;
    }

    @Test
    void testFastest() throws ExecutionException, InterruptedException {
        System.out.println(getFastest().get());
    }

    @Test
    void completionStage() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = getValue();

        // WAITING THE DATA TO BE EXIST
//        String value = future.get();
//        String[] values = value.toUpperCase().split(" ");

        // WITHOUT WAITING THE DATA TO BE EXIST
        CompletableFuture<String[]> future2 = future
                .thenApply(String::toUpperCase)
                .thenApply(s -> s.split(" "));

        String[] values = future2.get();
        for (String value : values) {
            System.out.println(value);
        }
    }
}
