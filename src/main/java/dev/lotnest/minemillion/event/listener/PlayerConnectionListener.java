package dev.lotnest.minemillion.event.listener;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.player.MineMillionPlayer;
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
        MineMillionPlayer mineMillionPlayer = plugin.getPlayerCache().getOrCreate(event.getPlayer().getUniqueId());
        if (mineMillionPlayer.getFirstPlayedMillis() == 0L) {
            mineMillionPlayer.setFirstPlayedMillis(System.currentTimeMillis());
        }

        mineMillionPlayer.setLastPlayedMillis(System.currentTimeMillis());
        plugin.getPlayerCache().update(mineMillionPlayer);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onQuit(@NotNull PlayerJoinEvent event) {
        MineMillionPlayer mineMillionPlayer = plugin.getPlayerCache().getOrCreate(event.getPlayer().getUniqueId());
        mineMillionPlayer.setLastPlayedMillis(System.currentTimeMillis());
        plugin.getPlayerCache().update(mineMillionPlayer);
        plugin.getPlayerCache().invalidate(event.getPlayer().getUniqueId());
    }
}
