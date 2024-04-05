package mk.ukim.finki.os.threadssynchronization.slideExamples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerControllerSimpler {

    public static int NUM_RUN = 50;

    static Semaphore producer;
    static Semaphore controller;

    public static void init() {
        producer= new Semaphore(1);
        controller= new Semaphore(10);
    }

    public static class Buffer {

        private boolean producing = false;
        private int checkingCount = 0;

        public void produce() {
            producing = true;
            if (checkingCount > 0) {
                throw new RuntimeException("Can't produce if controllers checking");
            }

            System.out.println("Producer is producing...");

            producing = false;
        }

        public synchronized void check() {
            checkingCount++;

            if (producing) {
                throw new RuntimeException("Can't check if producer is producing");
            }

            if (checkingCount > 10) {
                throw new RuntimeException(
                        "No more than 10 checks can be in progress simultaneously"
                );
            }

            System.out.println("Controller is checking...");

            checkingCount --;
        }
    }

    public static class Producer extends Thread {
        private final Buffer buffer;

        public Producer(Buffer b) {
            this.buffer = b;
        }

        public void execute() throws InterruptedException {
            producer.acquire();
            // Acquires the given number of permits
            // from this semaphore, blocking
            // until all are available

            controller.acquire(10);
            buffer.produce();
            controller.release(10);
            producer.release();
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Controller extends Thread {

        private final Buffer buffer;

        public Controller(Buffer buffer) {
            this.buffer = buffer;
        }

        public void execute() throws InterruptedException {
            controller.acquire();
            buffer.check();
            controller.release();
        }

        @Override
        public void run() {
            for (int i = 0; i < NUM_RUN; i++) {
                try {
                    execute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer p = new Producer(buffer);
        List<Controller> controllers = new ArrayList<>();
        init();
        for (int i = 0; i < 100; i++) {
            controllers.add(new Controller(buffer));
        }
        p.start();
        for (int i = 0; i < 100; i++) {
            controllers.get(i).start();
        }

    }

}
