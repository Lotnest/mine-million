package dev.lotnest.minemillion.task;

import dev.lotnest.minemillion.MineMillionPlugin;

public abstract class ScheduledMineMillionTask extends MineMillionTask {

    private final long delay;
    private final long period;
    private final boolean isAsync;

    protected ScheduledMineMillionTask(MineMillionPlugin plugin, long delay, long period, boolean isAsync) {
        super(plugin);
        this.delay = delay;
        this.period = period;
        this.isAsync = isAsync;
    }

    public void schedule() {
        if (period < 0) {
            if (isAsync) {
                runTaskAsynchronously(plugin);
            } else {
                runTask(plugin);
            }
        } else {
            if (isAsync) {
                runTaskTimerAsynchronously(plugin, delay, period);
            } else {
                runTaskTimer(plugin, delay, period);
            }
        }
    }
}
