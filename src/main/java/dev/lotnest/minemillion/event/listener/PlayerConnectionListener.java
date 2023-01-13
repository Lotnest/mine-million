package dev.lotnest.minemillion.event.listener;

import dev.lotnest.minemillion.MineMillionPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PlayerConnectionListener implements Listener {

    private final MineMillionPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(@NotNull PlayerJoinEvent event) {
        plugin.getPlayerCache().getOrCreate(event.getPlayer().getUniqueId());
    }
}
