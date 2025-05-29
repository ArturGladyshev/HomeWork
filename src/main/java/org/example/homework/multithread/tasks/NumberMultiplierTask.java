package org.example.homework.multithread.tasks;

import lombok.SneakyThrows;

public class NumberMultiplierTask implements Runnable {

    private int number;

    public NumberMultiplierTask(int number) {
        this.number = number;
    }

    @SneakyThrows
    @Override
    public void run() {
        int sum = 1;
        for (int i = 0; i < 10; ++i) {
            sum *= number;
            System.out.println(Thread.currentThread().getName() + " multiplier * 10, sum : " + sum);
            Thread.sleep(800);
        }
    }

}



