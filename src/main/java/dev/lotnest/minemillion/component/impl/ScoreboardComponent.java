package dev.lotnest.minemillion.component.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.component.Component;
import dev.lotnest.minemillion.component.ComponentResult;
import dev.lotnest.minemillion.scoreboard.MineMillionScoreboard;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public class ScoreboardComponent extends Component {

    private MineMillionScoreboard scoreboard;
    private boolean isInitialized;

    public ScoreboardComponent(MineMillionPlugin plugin) {
        super(plugin);
    }

    @Override
    public ComponentResult initialize() {
        if (isInitialized) {
            return ComponentResult.ALREADY_INITIALIZED;
        }

        scoreboard = new MineMillionScoreboard();
        scoreboard.addEntry("");
        scoreboard.addEntry(ChatColor.YELLOW + "Using language: %s", ChatColor.GOLD + plugin.getLanguageProvider().getLanguage().getEnglishName());

        isInitialized = true;
        return ComponentResult.INITIALIZED;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public ComponentResult shutdown() {
        if (!isInitialized) {
            return ComponentResult.NOT_INITIALIZED;
        }

        isInitialized = false;
        return ComponentResult.SHUTDOWN;
    }
}
