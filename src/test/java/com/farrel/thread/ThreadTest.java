package com.farrel.thread;

import org.junit.jupiter.api.Test;

public class ThreadTest {

    @Test
    void mainThread() {
        String name = Thread.currentThread().getName();
        System.out.println(name);
    }

    @Test
    void createThread() {
        Runnable runnable = () -> System.out.println("Hello from Thread: " + Thread.currentThread().getName());

        // running in the same thread main
//        runnable.run();

        // running in different thread
        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("Program End");
    }

    @Test
    void threadSleep() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000);
                System.out.println("Hello from Thread: " + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("Program End");

        Thread.sleep(3_000);
    }

    @Test
    void threadJoin() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000);
                System.out.println("Hello from Thread: " + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("Waiting...");
        thread.join();

        System.out.println("Program End");
    }

    @Test
    void threadInterrupt() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {

                System.out.println("Runnable: " + i);

                try {
                    Thread.sleep(1_000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(5_000);

        thread.interrupt();

        System.out.println("Waiting...");

        thread.join();

        System.out.println("Program End");
    }

    @Test
    void threadInterruptCorrect() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {

                // interrupt manual check
                if (Thread.interrupted()) return;

                System.out.println("Runnable: " + i);

//                try {
//                    Thread.sleep(1_000);
//
//                } catch (InterruptedException e) {
//                    return;
//
//                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(5_000);

        thread.interrupt();

        System.out.println("Waiting...");

        thread.join();

        System.out.println("Program End");
    }

    @Test
    void threadName() {
        Thread thread = new Thread(() -> System.out.println("Run in thread: " + Thread.currentThread().getName()));

        thread.setName("Farrel");
        thread.start();
    }

    @Test
    void threadState() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Run in thread: " + Thread.currentThread().getName());
            System.out.println("thread state: " + Thread.currentThread().getState());
        });

        thread.setName("Farrel");

        System.out.println("thread state: " + thread.getState());

        thread.start();
        thread.join();

        System.out.println("thread state: " + thread.getState());
    }
}
