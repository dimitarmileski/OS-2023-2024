package mk.ukim.finki.os.Threads;

public class InterfaceRunnable {
    public static void main(String[] args) throws InterruptedException {
        Runnable obj = new T2();

        Thread tobj = new Thread(obj);
        tobj.start();
        //tobj runs in parallel
        //with the main thread
        System.out.println("Hello from Main Thread!");
    }
}

class Base {}

class T2 extends Base implements Runnable {

    @Override
    public void run() {
        //thread main logic
        System.out.println("Hello from T2 thread!");
    }
}