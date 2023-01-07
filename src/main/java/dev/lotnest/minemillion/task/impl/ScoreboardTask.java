package dev.lotnest.minemillion.task.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.component.impl.ScoreboardComponent;
import dev.lotnest.minemillion.scoreboard.MineMillionScoreboard;
import dev.lotnest.minemillion.task.ScheduledMineMillionTask;

public class ScoreboardTask extends ScheduledMineMillionTask {

    private MineMillionScoreboard scoreboard;

    public ScoreboardTask(MineMillionPlugin plugin) {
        super(plugin, 0, 20, false);
    }

    @Override
    public void run() {
        if (scoreboard == null) {
            ScoreboardComponent scoreboardComponent = plugin.getComponentRegistry().getComponent(ScoreboardComponent.class);
            if (scoreboardComponent.isInitialized()) {
                scoreboard = scoreboardComponent.getScoreboard();
            }
        }

        if (scoreboard != null) {
            scoreboard.showToAllPlayers();
        }
    }
}
