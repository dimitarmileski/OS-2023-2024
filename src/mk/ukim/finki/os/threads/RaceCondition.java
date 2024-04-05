package mk.ukim.finki.os.threads;
/**
 *
 * @author dimitarmileski
 */
public class RaceCondition {
    public static void main(String[] args) {
        SafeIncrement inc = new SafeIncrement(10);

        // Create multiple threads to concurrently increment the variable
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                inc.incrementVar();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                inc.incrementVar();
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

        System.out.println("Final value of var: " + inc.getVar());
    }
}

class NotSafeIncrement {
    private int var;

    NotSafeIncrement(int i) {
        var = i;
    }

    // The incrementVar method is not thread-safe
    public int incrementVar() {
        return var++; // Race condition here
    }

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
}

class SafeIncrement {
    private int var;

    SafeIncrement(int i) {
        var = i;
    }

    // The incrementVar method is not thread-safe
    public synchronized  int incrementVar() {
        return var++; // Race condition here
    }

    public synchronized int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
}