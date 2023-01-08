package dev.lotnest.minemillion.task;

import com.google.common.collect.Sets;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.task.impl.ScoreboardTask;
import dev.lotnest.minemillion.task.impl.UpdaterTask;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TaskManager {

    private final MineMillionPlugin plugin;
    private final Set<MineMillionTask> tasks;

    public TaskManager(@NotNull MineMillionPlugin plugin) {
        this.plugin = plugin;
        tasks = Sets.newHashSet();

        initializeAndRunDefaultTasks();
    }

    private void initializeAndRunDefaultTasks() {
        tasks.add(new UpdaterTask(plugin));
        tasks.add(new ScoreboardTask(plugin));

        runAllTasks();
    }

    public void addTask(MineMillionTask mineMillionTask) {
        tasks.add(mineMillionTask);
    }

    public void cancelAllTasks() {
        tasks.forEach(MineMillionTask::cancel);
    }

    public void cancelTask(MineMillionTask mineMillionTask) {
        if (mineMillionTask != null) {
            mineMillionTask.cancel();
        }
    }

    public void cancelTaskById(int mineMillionTaskId) {
        tasks.stream()
                .filter(mineMillionTask -> mineMillionTask.getTaskId() == mineMillionTaskId)
                .findFirst()
                .ifPresent(MineMillionTask::cancel);
    }

    public void runAllTasks() {
        tasks.forEach(this::runTask);
    }

    public void runTask(MineMillionTask mineMillionTask) {
        if (mineMillionTask instanceof ScheduledMineMillionTask scheduledMineMillionTask) {
            scheduledMineMillionTask.schedule();
        } else {
            mineMillionTask.run();
        }
    }
}
