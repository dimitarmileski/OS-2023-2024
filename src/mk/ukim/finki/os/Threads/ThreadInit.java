package mk.ukim.finki.os.Threads;

public class ThreadInit {
    public static void main(String[] args) throws InterruptedException {
        T obj = new T();
        obj.start();

        System.out.println("Hello from main method!");

    }
}

class T extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println("Hello from overriden Thread method");
    }
}