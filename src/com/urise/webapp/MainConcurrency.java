package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState() + ": trying to get lock1");
                synchronized (LOCK1) {
                    System.out.println(getName() + " is holding lock1");
                    System.out.println(getName() + ", " + getState() + ": trying to get lock2");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (LOCK2) {
                        System.out.println(getName() + " is holding lock1 and lock2");
                    }
                }
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState() + ": trying to get lock2");
                synchronized (LOCK2) {
                    System.out.println(Thread.currentThread().getName() + " is holding lock2");
                    System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState() + ": trying to get lock1");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (LOCK1) {
                        System.out.println(Thread.currentThread().getName() + " is holding lock1");
                    }
                }
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

//        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threadList = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threadList.add(thread);
        }

        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mainConcurrency.counter);
    }


    private synchronized void inc() {
        counter++;
    }
}
