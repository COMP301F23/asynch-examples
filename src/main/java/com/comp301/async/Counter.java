package com.comp301.async;

public class Counter {
    private int value;

    public Counter() {
        value = 0;
    }

    public void addOne() {
        value = getValue() + 1;
    }

    public void subtractOne() {
        value = getValue() - 1;
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args)
            throws InterruptedException {

        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            for (int i=0; i<100000; i++) {
                counter.addOne();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i=0; i<100000; i++) {
                counter.subtractOne();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter.getValue());
    }
}
