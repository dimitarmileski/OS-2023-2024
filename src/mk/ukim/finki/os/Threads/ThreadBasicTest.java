package mk.ukim.finki.os.Threads;

public class ThreadBasicTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadA threadAObj = new ThreadA();
        ThreadB threadBObj = new ThreadB();

        threadAObj.start();
        threadBObj.start();

        System.out.println("Main done!");
    }
}

class ThreadA extends Thread{
    @Override
    public void run() {
        for (int i = 1; i < 20; i++) {
            System.out.println("A: " + i);
        }

        System.out.println("A done!");
    }
}

class ThreadB extends Thread{
    @Override
    public void run() {
        for (int i = -1; i >= -20; i--) {
            System.out.println("\t\tB: " + i);
        }
        System.out.println("B done!");
    }
}
