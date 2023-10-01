package com.urise.webapp;

public class DeadlockExample {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        runDeadlock();
    }

    public static void runDeadlock() {
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

        Thread thread1 = new Thread(() -> {
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
        });

        thread0.start();
        thread1.start();

    }
}
