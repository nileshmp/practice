package com.nilesh.threads;

import java.util.concurrent.Semaphore;

public class ThreadSample {
    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(0);
        Worker t = new Worker(sem);
        t.start();
        System.out.println("After thread starts");
        sem.release();
    }


}

class Worker extends Thread {
    private final Semaphore sem;

    public Worker(Semaphore sem) {
        this.sem = sem;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Before Acquiring...");
                sem.acquire();
                System.out.println("proceed to work");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
