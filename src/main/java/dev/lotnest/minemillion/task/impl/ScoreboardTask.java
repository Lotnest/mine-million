package dev.lotnest.minemillion.task.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.scoreboard.MineMillionScoreboard;
import dev.lotnest.minemillion.task.ScheduledMineMillionTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScoreboardTask extends ScheduledMineMillionTask {

    public ScoreboardTask(@NotNull MineMillionPlugin plugin) {
        super(plugin, 0, 20, false);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            MineMillionScoreboard scoreboard = new MineMillionScoreboard(player);
            scoreboard.addEmptyEntry();
            scoreboard.addEntry(ChatColor.YELLOW + "Using language: %s", ChatColor.GOLD + plugin.getLanguageProvider().getLanguage().getNativeName());
            scoreboard.addEmptyEntry();
            scoreboard.addEntry(ChatColor.YELLOW + "First played millis: %s", ChatColor.GOLD.toString() + plugin.getPlayerCache().getOrCreate(player).getFirstPlayedMillis());
            scoreboard.showToPlayer();
        }
    }
}
