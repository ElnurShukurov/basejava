package com.urise.webapp;

public class DeadlockExample {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        runDeadlock();
    }

    public static void runDeadlock() {
        Thread thread0 = new Thread(() -> performDeadLock(LOCK1, LOCK2));
        Thread thread1 = new Thread(() -> performDeadLock(LOCK2, LOCK1));

        thread0.start();
        thread1.start();

    }

    private static void performDeadLock(Object firstLock, Object secondLock) {
        String threadName = Thread.currentThread().getName();
        Thread.State threadState = Thread.currentThread().getState();

        System.out.println(threadName + ", " + threadState + ": trying to get " + getName(firstLock));
        synchronized (firstLock) {
            System.out.println(threadName + ", " + threadState + " is holding " + getName(firstLock));
            System.out.println(threadName + ", " + threadState + ": trying to get " + getName(secondLock));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (secondLock) {
                System.out.println(threadName + ", " + threadState + " is holding " + getName(firstLock) + getName(secondLock));
            }
        }
    }

    private static String getName(Object lock) {
        if (lock == LOCK1) {
            return "LOCK1";
        } else if (lock == LOCK2) {
            return "LOCK2";
        }
        return "Unknown Lock";
    }
}
