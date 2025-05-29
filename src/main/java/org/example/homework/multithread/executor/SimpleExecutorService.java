package org.example.homework.multithread.executor;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Простой пул фиксированного количества потоков,
 * который последовательно выполняет добавленные задачи.
 * Метод stop завершит работу потоков по окончанию находящихся в
 * работе задач.
 */
public class SimpleExecutorService extends AbstractExecutorService {

    private static final int DEFAULT_SLEEP_TIME = 4000;

    private boolean isWork = true;

    private final int sleepTime;

    public SimpleExecutorService(int poolSize) {
        this(poolSize, DEFAULT_SLEEP_TIME);
    }

    public SimpleExecutorService(int poolSize, int sleepTime) {
        this.sleepTime = sleepTime;
        Stream.generate(ServiceThread::new).limit(poolSize).forEach(Thread::start);
    }

    @Override
    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
        }
    }

    @Override
    public void stop() {
        isWork = false;
    }

    private class ServiceThread extends Thread {

        @Override
        public void run() {
            while (isWork) {
                try {
                    Optional<Runnable> task = getTask();
                    task.ifPresent(Runnable::run);
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    if (isInterrupted()) {
                        interrupt();
                    }
                }
            }
        }

    }

}
