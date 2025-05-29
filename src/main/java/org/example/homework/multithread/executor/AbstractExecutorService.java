package org.example.homework.multithread.executor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public abstract class AbstractExecutorService {

    protected final Deque<Runnable> tasks = new ArrayDeque<>();

    abstract public void submit(Runnable task);

    abstract public void stop();

    protected Optional<Runnable> getTask() {
        synchronized (tasks) {
            return Optional.ofNullable(tasks.pollFirst());
        }
    }
}
