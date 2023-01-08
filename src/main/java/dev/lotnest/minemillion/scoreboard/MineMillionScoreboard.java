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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.requireNonNull;

public class MineMillionScoreboard {

    private static final String DEFAULT_TITLE = ChatColor.GOLD.toString() + ChatColor.BOLD + "MineMillion";

    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;

    public MineMillionScoreboard(Player player) {
        this(player, DEFAULT_TITLE);
    }

    public MineMillionScoreboard(Player player, String title) {
        this.player = player;
        scoreboard = requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        objective = scoreboard.registerNewObjective("MM-" + UUID.randomUUID(), Criteria.DUMMY, title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void addEntry(String text) {
        addEntry(text, "");
    }

    public void addEntry(@NotNull String text, @Nullable Object... placeholders) {
        text = String.format(text, placeholders);

        Team team = scoreboard.getTeam(text);
        if (team == null) {
            team = scoreboard.registerNewTeam(text);
        }
        team.addEntry(text);
        team.setDisplayName(text);

        int scoreToSet = 16 - scoreboard.getEntries().size();
        for (String entry : scoreboard.getEntries()) {
            int currentScore = objective.getScore(entry).getScore();
            if (scoreToSet > currentScore) {
                scoreToSet = currentScore - 1;
            }
        }

        Score entryScore = objective.getScore(text);
        entryScore.setScore(scoreToSet);
    }

    public void addEmptyEntry() {
        StringBuilder emptyEntryBuilder = new StringBuilder();
        ChatColor[] chatColors = ChatColor.values();

        for (int i = 0; i < 10; i++) {
            ChatColor randomColor = chatColors[ThreadLocalRandom.current().nextInt(chatColors.length)];
            emptyEntryBuilder.append(randomColor);
        }

        String emptyEntry = emptyEntryBuilder.toString();

        while (scoreboard.getEntries().contains(emptyEntry)) {
            emptyEntryBuilder.append(" ");
            emptyEntry = emptyEntryBuilder.toString();
        }

        addEntry(emptyEntry);
    }

    public void clearEntries() {
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
    }

    private boolean isPlayerOnline() {
        return player != null && player.isOnline();
    }

    public boolean isShownToPlayer() {
        return isPlayerOnline() && player.getScoreboard().equals(scoreboard);
    }

    public void showToPlayer() {
        if (isPlayerOnline()) {
            player.setScoreboard(scoreboard);
        }
    }
}
