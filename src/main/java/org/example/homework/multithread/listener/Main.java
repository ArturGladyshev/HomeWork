package org.example.homework.multithread.listener;

import org.example.homework.multithread.tasks.NumberIncreasingTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<FutureStatusTask> tasks = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(11);
        TasksListener tasksListener = new TasksListener(tasks);

        FutureStatusTask task = new FutureStatusTask(new NumberIncreasingTask(1000));
        tasks.add(task);
        threadPool.submit(task);

        new Thread(tasksListener).start();

        for (int i = 0; i != 10; ++i) {
            task = new FutureStatusTask(new NumberIncreasingTask(10));
            tasks.add(task);
            threadPool.submit(task);
        }

        Thread.sleep(6000);
        tasks.stream().filter(t -> t.getStatus() == TaskState.WORK)
                .forEach(t -> t.cancel(true));

        tasksListener.setListen(false);
    }

}
