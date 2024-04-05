package mk.ukim.finki.os.threads;

import java.util.concurrent.Semaphore;

/**
 *
 * @author dimitarmileski
 */
public class DeadLock {

    public static void main(String[] args) throws InterruptedException {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);

        Thread th = new Thread(() -> {
            try {
                s1.acquire();
                System.out.println("Working 1s");
                Thread.sleep(1000);
                s1.release();

                s1.acquire();
                System.out.println("Working 4s");
                Thread.sleep(4000);
                s1.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        th.start();

        try {
            th.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        Deadlock
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(1);

        Thread th3 = new Thread(() -> {
            try {
                semaphore1.acquire();
                System.out.println("Thread 1 acquired lock1");
                Thread.sleep(100);
                System.out.println("Thread 1 waiting for lock2");

                semaphore2.acquire(); // Acquiring lock2
                System.out.println("Thread 1 acquired lock2");
                semaphore2.release(); // Releasing lock2

                semaphore1.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread th4 = new Thread(() -> {
            try {
                semaphore2.acquire(); // Acquiring lock2
                System.out.println("Thread 2 acquired lock2");
                Thread.sleep(100);
                System.out.println("Thread 2 waiting for lock1");

                semaphore1.acquire(); // Acquiring lock1
                System.out.println("Thread 2 acquired lock1");
                semaphore1.release(); // Releasing lock1

                semaphore2.release(); // Releasing lock2
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        th3.start();
        th4.start();

        try {
            th3.join();
            th4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
