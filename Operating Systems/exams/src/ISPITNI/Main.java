package ISPITNI;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static int count = 0; // Shared variable
    private static final Semaphore semaphore = new Semaphore(5); // Semaphore for 5 threads
    private static final Lock lock = new ReentrantLock(); // Lock for synchronization

    public static void increment() throws InterruptedException {
        for (int i = 0; i < 2; i++) { // Each thread increments count twice
            semaphore.acquire(); // Acquire the semaphore before incrementing

            lock.lock(); // Ensure that only one thread can modify count at a time
                count++; // Increment the count
                System.out.println("Count incremented to: " + count + " by " + Thread.currentThread().getName());

                lock.unlock(); // Unlock the lock after incrementing

            semaphore.release(); // Release the semaphore after incrementing
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Create 5 threads, each calling the increment method
        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start(); // Start the thread
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final count value: " + count);
    }
}
