package org.example.homework.multithread.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Класс отслеживает статусы списка FutureStatusTask, проверяя их
 * через равные интервалы времени.
 */
@AllArgsConstructor
@Getter
@Setter
public class TasksListener implements Runnable {

    private List<FutureStatusTask> tasks;

    private boolean isListen = true;

    private int waitTime = 3000;

    public TasksListener(List<FutureStatusTask> tasks) {
        this.tasks = tasks;
    }

    public TasksListener(List<FutureStatusTask> tasks, int waitTime) {
        this.tasks = tasks;
        this.waitTime = waitTime;
    }

    public int countTasksWithState(TaskState state) {
        return (int) tasks.stream().filter(task -> task.getStatus() == state).count();
    }

    @Override
    public void run() {
        while (isListen) {
            getTasksStates();
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getTasksStates() {
        System.out.println(new StringBuffer().append("NEW Threads: " + countTasksWithState(TaskState.NEW) + '\n'
                + "WORK Threads: " + countTasksWithState(TaskState.WORK) + '\n'
                + "CANCELED Threads: " + countTasksWithState(TaskState.CANCELED) + '\n'
                + "COMPLETED Threads: " + countTasksWithState(TaskState.COMPLETED) + '\n'));

    }

}
