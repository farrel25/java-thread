package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {

    @Test
    void testFuture() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

//        Callable<String> callable = new Callable<>() {
//            @Override
//            public String call() throws Exception {
//                Thread.sleep(5000);
//                return "Hi";
//            }
//        };

        Callable<String> callable = () -> {
            Thread.sleep(5000);
            return "Hi";
        };

        Future<String> future = executorService.submit(callable);
        System.out.println("Selesai Future");

        while (!future.isDone()) {
            System.out.println("Waiting future");
            Thread.sleep(1000);
        }

        String value = future.get();
        System.out.println(value);
    }

    @Test
    void testFutureCancel() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(5000);
            return "Hi";
        };

        Future<String> future = executorService.submit(callable);
        System.out.println("Selesai Future");

        Thread.sleep(2000);
        future.cancel(true);

        System.out.println(future.isCancelled());

        String value = future.get();
        System.out.println(value);
    }

    @Test
    void testInvokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = IntStream.range(1, 11)
                .mapToObj(value -> (Callable<String>) () -> {
                    System.out.println("Running in thread: " + Thread.currentThread().getName());
                    Thread.sleep(value * 500L);
                    return String.valueOf(value);
                })
                .toList();



        // SUBMIT CALLABLE ONE BY ONE
//        List<Future<String>> futures = new ArrayList<>();
//
//        callables.forEach(stringCallable -> {
//            Future<String> future = executorService.submit(stringCallable);
//            futures.add(future);
//        });


        // SUBMIT ALL CALLABLE AT ONCE
        List<Future<String>> futures = executorService.invokeAll(callables);


        System.out.println("\nSHOW RESULT");
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }
    }

    @Test
    void testInvokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = IntStream.range(1, 11)
                .mapToObj(value -> (Callable<String>) () -> {
                    System.out.println("Running in thread: " + Thread.currentThread().getName());
                    Thread.sleep(value * 500L);
                    return String.valueOf(value);
                })
                .toList();


        String result = executorService.invokeAny(callables);
        System.out.println(result);
    }
}
