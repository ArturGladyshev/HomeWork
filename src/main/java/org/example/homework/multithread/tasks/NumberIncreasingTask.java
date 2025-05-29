package org.example.homework.multithread.tasks;

public class NumberIncreasingTask implements Runnable {

    private int number;

    public NumberIncreasingTask(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        int sum = 0;
        try {
            for (int i = 0; i < 10; ++i) {
                sum += number;
                System.out.println(Thread.currentThread().getName() + " increase + 10, sum : " + sum);
                Thread.sleep(800);
            }
        } catch (InterruptedException e) {
            if (Thread.currentThread().isInterrupted()) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

