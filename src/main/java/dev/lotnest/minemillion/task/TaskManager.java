package dev.lotnest.minemillion.task;

import com.google.common.collect.Sets;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.task.impl.UpdaterTask;

import java.util.Set;

public class TaskManager {

    private final MineMillionPlugin plugin;
    private final Set<MineMillionTask> tasks;

    public TaskManager(MineMillionPlugin plugin) {
        this.plugin = plugin;
        tasks = Sets.newHashSet();

        initializeAndRunDefaultTasks();
    }

    private void initializeAndRunDefaultTasks() {
        tasks.add(new UpdaterTask(plugin));

        runAllTasks();
    }

    public void addTask(MineMillionTask task) {
        tasks.add(task);
    }

    public void cancelAllTasks() {
        tasks.forEach(MineMillionTask::cancel);
    }

    public void cancelTask(MineMillionTask task) {
        if (tasks.contains(task)) {
            task.cancel();
        }
    }

    public void cancelTaskById(int taskId) {
        tasks.stream()
                .filter(task -> task.getTaskId() == taskId)
                .findFirst()
                .ifPresent(MineMillionTask::cancel);
    }

    public void runAllTasks() {
        tasks.forEach(MineMillionTask::run);
    }

    public void runTask(MineMillionTask task) {
        if (tasks.contains(task)) {
            task.run();
        }
    }
}
