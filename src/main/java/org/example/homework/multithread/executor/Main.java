package org.example.homework.multithread.executor;

import org.example.homework.multithread.tasks.NumberMultiplierTask;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        FutureExecutorService futureExService = new FutureExecutorService(3);
        for (int i = 0; i < 10; ++i) {
            futureExService.submit(new NumberMultiplierTask(10));
        }

        Thread.sleep(20000);

        SimpleExecutorService simpleExService = new SimpleExecutorService(10);
        for (int i = 0; i < 10; ++i) {
            futureExService.submit(new NumberMultiplierTask(10));
        }
        Thread.sleep(2000);
        for (int i = 0; i < 10; ++i) {
            simpleExService.submit(new NumberMultiplierTask(10));
        }

        Thread.sleep(2000);
        simpleExService.stop();
    }

}
