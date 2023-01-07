package dev.lotnest.minemillion.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class MineMillionScoreboard {

    private static final String DEFAULT_TITLE = ChatColor.GOLD.toString() + ChatColor.BOLD + "MineMillion";

    private final Scoreboard scoreboard;
    private final Objective objective;

    public MineMillionScoreboard() {
        this(DEFAULT_TITLE);
    }

    public MineMillionScoreboard(String title) {
        scoreboard = requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        objective = scoreboard.registerNewObjective("MM-" + UUID.randomUUID(), Criteria.DUMMY, title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void addEntry(String text) {
        addEntry(text, "");
    }

    public void addEntry(String text, Object... placeholders) {
        if (placeholders == null) {
            placeholders = new String[0];
        }

        text = text.formatted(placeholders);

        Team team = scoreboard.getTeam(text);
        if (team == null) {
            team = scoreboard.registerNewTeam(text);
        }

        team.addEntry(text);
        team.setDisplayName(text);

        int scoreToSet = scoreboard.getEntries().size() + 1;
        for (String entry : scoreboard.getEntries()) {
            int currentScore = objective.getScore(entry).getScore();
            if (scoreToSet > currentScore) {
                scoreToSet = currentScore - 1;
            }
        }

        Score entryScore = objective.getScore(text);
        entryScore.setScore(scoreToSet);
    }

    public void removeEntry(String text) {
        Team team = scoreboard.getTeam(text);
        if (team != null) {
            team.unregister();
        }

        scoreboard.resetScores(text);
    }

    public void clearEntries() {
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
    }

    public boolean isShownToPlayer(Player player) {
        return player != null && player.getScoreboard().equals(scoreboard);
    }

    public void showToPlayer(Player player) {
        if (player == null) {
            return;
        }

        player.setScoreboard(scoreboard);
    }

    public void showToAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            showToPlayer(player);
        }
    }
}
