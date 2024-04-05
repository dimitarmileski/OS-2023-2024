package mk.ukim.finki.os.threads;
/**
 *
 * @author dimitarmileski
 */
public class ThreadBasicTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadA threadAObj = new ThreadA();
        ThreadB threadBObj = new ThreadB();

        threadAObj.start();
        threadBObj.start();

        System.out.println("Main done!");
//        Thread.sleep(15000);
//        threadAObj.interrupt();
//        threadBObj.interrupt();
//        threadAObj.destroy();
//        threadBObj.destroy();
    }
}

class ThreadA extends Thread{
    @Override
    public void run() {
        for (int i = 1; i < 20; i++) {
            System.out.println("A: " + i);
        }

//        try {
//            //boolean isInterupted = interrupted();
//
//            while(!interrupted()){
//                System.out.println("Thread A is not interupted");
//                Thread.sleep(1000);
//            }
//             // Wait for 1 minute (60,000 milliseconds)
//        } catch (InterruptedException e) {
//            // Handle the InterruptedException if necessary
//            System.out.println("Thread A is interupted! Housekeeping.");
//            e.printStackTrace();
//        }

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
