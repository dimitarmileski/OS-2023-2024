package mk.ukim.finki.os.threads;
/**
 *
 * @author dimitarmileski
 */
public class StaticMethodSynchronization {
    public static void main(String[] args) {
        ThreadSafeStaticMethods inc = new ThreadSafeStaticMethods(10);

        // Create multiple threads to concurrently increment the variable
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                inc.safeIncrementVar();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                inc.safeIncrementVar();
            }
        });

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Safe static method: " + inc.getVar());


        // Create multiple threads to concurrently increment the variable
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                inc.notSafeIncrementVar();
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                inc.notSafeIncrementVar();
            }
        });

        thread3.start();
        thread4.start();

        // Wait for both threads to finish
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("NOT SAFE static method: " + inc.getVar());
    }
}

class ThreadSafeStaticMethods {
    private static int var;

    ThreadSafeStaticMethods(int i) {
        var = i;
    }

    // The incrementVar method is not thread-safe
    public static int safeIncrementVar() {
        synchronized (ThreadSafeStaticMethods.class){
            return var++;  // Race condition here
        }
    }

    public static int notSafeIncrementVar() {
        return var++;  // Race condition here
    }

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
}