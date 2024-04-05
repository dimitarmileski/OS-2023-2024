package mk.ukim.finki.os.threads;

import java.util.ArrayList;
import java.util.Random;

public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Exam rules: Max time spend on exam 10 sec.");
        ArrayList<Exam> students = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Exam vru = new Exam();
            System.out.println("StudentID: " + vru.getName() + " enters exam");

            vru.start();
            students.add(vru);
        }

        students.forEach(u -> {
            try {
                u.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("All students finished exam!");
        //sa.interrupt();
    }
}


class Exam extends Thread{
    @Override
    public void run() {
        Random randomMiliseconds = new Random();
        int waitTime = randomMiliseconds.nextInt(5000, 10000) + 1;
        try {
            System.out.println(this.getName() + " is inside exam solving problems: " + waitTime + "miliseconds.");
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}