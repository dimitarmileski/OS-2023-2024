package mk.ukim.finki.os.threadssynchronization.slideExamples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Composite {

    private Composite c = null;

    int x = 0;

    private Lock lock = new ReentrantLock();

    private Semaphore semaphore = new Semaphore(1);

    public Composite(int b) { x=b; }

    public Composite(Composite a, int b) { c=a; x=b; }

    public void move() throws InterruptedException {
        semaphore.acquire();
        if(c!=null) c.move(); x++;
        semaphore.release();
    }

    public int getValue() {
        return x;
    }
}

public class CompositeExample {

    private static final Composite shared=new Composite(0);

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread() {

                public void run() {
                    Composite local = new Composite(shared, 0);
                    try {
                        local.move();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(shared.getValue());
    }

}

