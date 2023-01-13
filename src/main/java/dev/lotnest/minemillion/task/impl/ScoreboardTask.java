package dev.lotnest.minemillion.task.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.player.MineMillionPlayer;
import dev.lotnest.minemillion.scoreboard.MineMillionScoreboard;
import dev.lotnest.minemillion.task.ScheduledMineMillionTask;
import dev.lotnest.minemillion.util.ColorConstants;
import dev.lotnest.minemillion.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScoreboardTask extends ScheduledMineMillionTask {

    public ScoreboardTask(@NotNull MineMillionPlugin plugin) {
        super(plugin, 0, 20, false);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            MineMillionPlayer mineMillionPlayer = plugin.getPlayerCache().getOrCreate(player).join();
            MineMillionScoreboard scoreboard = new MineMillionScoreboard(player);

            scoreboard.addEmptyEntry();
            scoreboard.addEntry(ColorConstants.BLUE_STRING + "Language: %s", ColorConstants.GOLD_STRING + plugin.getLanguageProvider().getLanguage().getNativeName());
            scoreboard.addEmptyEntry();
            scoreboard.addEntry(ColorConstants.BLUE_STRING + "Cash: %s", ColorConstants.GOLD_STRING + StringUtil.getSeperatedCash(mineMillionPlayer.getCash()));
            scoreboard.showToPlayer();
        }
    }
}
