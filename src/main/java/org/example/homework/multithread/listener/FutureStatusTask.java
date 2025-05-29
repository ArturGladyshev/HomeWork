package org.example.homework.multithread.listener;

import java.util.concurrent.FutureTask;

/**
 * Класс расширяет FutureTask, переопределяет методы run и done,
 * позволяя упростить отслеживание состояния работоспособности
 * потока через поле TaskState.
 */
public class FutureStatusTask extends FutureTask<Runnable> {

    private TaskState status = TaskState.NEW;

    public FutureStatusTask(Runnable task) {
        super(task, null);
    }

    public TaskState getStatus() {
        return status;
    }

    @Override
    public void run() {
        status = TaskState.WORK;
        super.run();
    }

    @Override
    protected void done() {
        status = !isCancelled() ? TaskState.COMPLETED : TaskState.CANCELED;
    }

}
