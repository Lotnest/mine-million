package dev.lotnest.minemillion.task;

import dev.lotnest.minemillion.MineMillionPlugin;

public abstract class ScheduledMineMillionTask extends MineMillionTask {

    private final long delay;
    private final long period;
    private final boolean isAsync;

    public ScheduledMineMillionTask(MineMillionPlugin plugin, long delay, long period, boolean isAsync) {
        super(plugin);
        this.delay = delay;
        this.period = period;
        this.isAsync = isAsync;
    }

    @Override
    public void run() {
        if (isAsync) {
            runTaskAsynchronously(plugin);
        } else {
            if (delay < 0) {
                runTask(plugin);
            } else {
                runTaskTimer(plugin, delay, period);
            }
        }
    }
}
