package dev.lotnest.minemillion.event.listener;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.player.MineMillionPlayer;
import dev.lotnest.minemillion.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {

    private final @NotNull MineMillionPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(@NotNull PlayerJoinEvent event) {
        plugin.getPlayerCache().getOrCreate(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(@NotNull PlayerQuitEvent event) {
        plugin.getPlayerCache()
                .get(event.getPlayer().getUniqueId())
                .whenComplete((optionalMineMillionPlayer, throwable) -> {
                    if (throwable != null) {
                        LogUtil.unknownError(throwable);
                        return;
                    }

                    if (optionalMineMillionPlayer.isPresent()) {
                        MineMillionPlayer mineMillionPlayer = optionalMineMillionPlayer.get();
                        mineMillionPlayer.setLastPlayedMillis(System.currentTimeMillis());

                        plugin.getPlayerCache().update(mineMillionPlayer);
                        plugin.getPlayerCache().invalidate(mineMillionPlayer.getUuid());
                    }
                });
    }
}
