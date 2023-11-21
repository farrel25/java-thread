package com.farrel.thread;

import org.junit.jupiter.api.Test;

public class DeadlockTest {

    @Test
    void transfer() throws InterruptedException {
        Balance balance1 = new Balance(1_000_000L);
        Balance balance2 = new Balance(1_000_000L);

        Thread thread1 = new Thread(() -> {
            try {
                Balance.transfer(balance1, balance2, 500_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Balance.transfer(balance2, balance1, 500_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Balance 1: " + balance1.getValue());
        System.out.println("Balance 2: " + balance2.getValue());
    }
}
