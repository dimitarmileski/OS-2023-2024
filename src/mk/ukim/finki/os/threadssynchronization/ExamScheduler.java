package mk.ukim.finki.os.threadssynchronization;

import java.util.concurrent.Semaphore;

public class ExamScheduler {
    public static void main(String[] args) {
        int numLabs = 10;
        int studentsPerLab = 10;
        int totalStudents = 840;

        // Creating an array of semaphores, one for each lab
        Semaphore[] labSemaphores = new Semaphore[numLabs];
        for (int i = 0; i < numLabs; i++) {
            labSemaphores[i] = new Semaphore(studentsPerLab);
        }

        // Creating threads for each student
        for (int student = 1; student <= totalStudents; student++) {
            Thread studentThread = new Thread(new Student(student, labSemaphores));
            studentThread.start();
        }
    }
}

class Student implements Runnable {
    private int studentId;
    private Semaphore[] labSemaphores;

    public Student(int studentId, Semaphore[] labSemaphores) {
        this.studentId = studentId;
        this.labSemaphores = labSemaphores;
    }

    @Override
    public void run() {
        boolean finished = false;
        while (!finished) {
            for (int lab = 0; lab < labSemaphores.length; lab++) {
                try {
                    if (labSemaphores[lab].tryAcquire()) {
                        // Student entered the lab successfully
                        System.out.println("Student " + studentId + " entered Lab " + (lab + 1) + " to take the exam.");
                        // Simulate time taken for exam
                        Thread.sleep(10000); // Adjust time as per requirement
                        // Release permit after exam
                        System.out.println("Student " + studentId + " finished exam in Lab " + (lab + 1) + ".");
                        labSemaphores[lab].release();
                        finished = true; // Exit loop after successfully entering a lab
                        break; // Exit the for loop
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

