package org.example.homework.multithread.executor;

import org.example.homework.multithread.listener.FutureStatusTask;
import org.example.homework.multithread.listener.TaskState;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Пул потоков фиксированного размера. Потоки выполняют задачу,
 * которой были проинициализированы, а после пытаются получить задачу
 * из списка сервиса. При получении создается новый поток, которому задача делегируется -
 * текущий поток прерывается. Также есть возможность отменить все задачи списка. Если задач в списке нет,
 * поток засыпает. После заданного кол-ва засыпаний поток прерывается.
 */
public class FutureExecutorService extends AbstractExecutorService {

    private static final int DEFAULT_ASLEEP_NUMBERS = 5;

    private static final int DEFAULT_SLEEP_TIME = 2000;

    private final int maxAsleepNumbers;

    private final int sleepTime;

    public FutureExecutorService(int poolSize) {
        this(poolSize, DEFAULT_ASLEEP_NUMBERS, DEFAULT_SLEEP_TIME);
    }

    public FutureExecutorService(int poolSize, int asleepNumbers, int sleepTime) {
        this.maxAsleepNumbers = asleepNumbers;
        this.sleepTime = sleepTime;
        Stream.generate(ServiceThread::new).limit(poolSize).forEach(Thread::start);
    }

    @Override
    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.add(new FutureStatusTask(task));
        }
    }

    @Override
    public void stop() {
        tasks.stream().map(task -> (FutureStatusTask) task).filter(t -> t.getStatus() == TaskState.WORK
                        || t.getStatus() == TaskState.NEW)
                .forEach(t -> t.cancel(true));
    }

    private void updateThread(Runnable task) {
        new ServiceThread(task).start();
        Thread.currentThread().interrupt();
    }

    private class ServiceThread extends Thread {

        public ServiceThread(Runnable task) {
            super(task);
        }

        public ServiceThread() {
        }

        @Override
        public void run() {
            try {
                super.run();
                int asleepNumbers = 0;
                while (!Thread.currentThread().isInterrupted() && asleepNumbers < maxAsleepNumbers) {
                    Optional<Runnable> task = getTask();
                    task.ifPresent(FutureExecutorService.this::updateThread);
                    sleep(sleepTime);
                    ++asleepNumbers;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
