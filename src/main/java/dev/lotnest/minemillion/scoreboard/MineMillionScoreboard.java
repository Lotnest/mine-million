package dev.lotnest.minemillion.scoreboard;

import dev.lotnest.minemillion.util.ColorConstants;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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

    private static final String DEFAULT_TITLE = ColorConstants.GOLD_STRING + ChatColor.BOLD + "MineMillion";

    private final Player player;
    private final Scoreboard scoreboard;
    private final Objective objective;

    public MineMillionScoreboard(@NotNull Player player) {
        this(player, DEFAULT_TITLE);
    }

    public MineMillionScoreboard(@NotNull Player player, @NotNull String title) {
        this.player = player;
        scoreboard = requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        objective = scoreboard.registerNewObjective("MM-" + UUID.randomUUID(), Criteria.DUMMY, title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void addEntry(@NotNull String text) {
        addEntry(text, "");
    }

    public void addEntry(@NotNull String text, @Nullable Object... placeholders) {
        text = String.format(text, placeholders);

        Team team = scoreboard.getTeam(text);
        if (team == null) {
            team = scoreboard.registerNewTeam(text);
        }

        String randomEmptyEntry = createRandomEmptyEntry();

        team.addEntry(randomEmptyEntry);
        team.setPrefix(text);

        int scoreToSet = 16 - scoreboard.getEntries().size();
        for (String entry : scoreboard.getEntries()) {
            int currentScore = objective.getScore(entry).getScore();
            if (scoreToSet > currentScore) {
                scoreToSet = currentScore - 1;
            }
        }

        Score entryScore = objective.getScore(randomEmptyEntry);
        entryScore.setScore(scoreToSet);
    }

    private String createRandomEmptyEntry() {
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

        return emptyEntry;
    }

    public void addEmptyEntry() {
        addEntry(createRandomEmptyEntry());
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
