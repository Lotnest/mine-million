package dev.lotnest.minemillion.event.listener;

import dev.lotnest.minemillion.MineMillionPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {

    private final @NotNull MineMillionPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(@NotNull PlayerJoinEvent event) {
        plugin.getPlayerCache().getOrCreate(event.getPlayer().getUniqueId());
    }
}
