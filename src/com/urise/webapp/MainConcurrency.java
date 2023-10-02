package com.urise.webapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    //    private int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
//    private static final Object LOCK1 = new Object();
//    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock();
//    private static final Lock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();
//    private static final Lock READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();

    //    private static final SimpleDateFormat SDF = new SimpleDateFormat();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
//    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

//            private void inc() {
//                synchronized (this) {
//                    counter++;
//                }
//            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);
//        List<Thread> threadList = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() ->
//           Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(DATE_TIME_FORMATTER.format(LocalDateTime.now()));
                }
                latch.countDown();
                return 4;
            });
//        thread.start();
//        threadList.add(thread);

        }

/*        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
*/
        latch.await();
        executorService.shutdown();
//        System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
//        lock.lock();
//        try {
        atomicCounter.incrementAndGet();
//            counter++;
//        } finally {
//            lock.unlock();
//        }
    }
}
