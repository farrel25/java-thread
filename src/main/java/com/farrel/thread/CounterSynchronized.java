package com.farrel.thread;

public class CounterSynchronized {

    private Long value = 0L;

    // synchronized method
    public synchronized void increment() {
        value++;
    }

    // synchronized statement
    public void increment2() {
        synchronized (this) {
            value++;
        }
    }

    public Long getValue() {
        return value;
    }
}
