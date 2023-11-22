package com.farrel.thread;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    void delayedJob() throws InterruptedException {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 2_000);

        Thread.sleep(3_000);
    }

    @Test
    void periodicJob() throws InterruptedException {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 2_000, 2_000);

        Thread.sleep(10_000);
    }
}
