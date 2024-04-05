package mk.ukim.finki.os.threads;

import java.util.concurrent.Semaphore;

/**
 *
 * @author dimitarmileski
 */
public class ThreadOrderOfExecution {
    public static void main(String[] args) {
        ThrA thrA = new ThrA();
        ThrB thrB = new ThrB();

        Semaphore semaphore = new Semaphore(1);

        thrA.start();
        thrB.start();
    }
}

class ThrA extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("A: " + i);
        }
    }
}

class ThrB extends Thread{
    @Override
    public void run() {
        for (int i = -1; i >= -100; i--) {
            System.out.println("\t\tB: " + i);
        }
    }
}
