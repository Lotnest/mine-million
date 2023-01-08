package dev.lotnest.minemillion.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MineMillionPlayerCache {

    private final Cache<UUID, MineMillionPlayer> cache;
    private final MineMillionPlayerDAO dao;

    public MineMillionPlayerCache(@NotNull MineMillionPlayerDAO dao) {
        this.dao = dao;
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();
    }

    public MineMillionPlayer getOrCreate(@NotNull Player player) {
        return getOrCreate(player.getUniqueId());
    }

    public MineMillionPlayer getOrCreate(@NotNull UUID playerUUID) {
        MineMillionPlayer result = cache.getIfPresent(playerUUID);

        if (result != null) {
            return result;
        }

        result = dao.get(playerUUID).orElseGet(() -> {
            MineMillionPlayer mineMillionPlayer = MineMillionPlayer.builder().playerUUID(playerUUID).build();
            dao.create(mineMillionPlayer);
            return mineMillionPlayer;
        });
        cache.put(playerUUID, result);

        return result;
    }

    public void invalidate(@NotNull UUID uuid) {
        cache.invalidate(uuid);
    }

    public void update(@NotNull MineMillionPlayer updatedMineMillionPlayer) {
        MineMillionPlayer player = cache.getIfPresent(updatedMineMillionPlayer.getPlayerUUID());
        if (player != null) {
            dao.update(player);
        }
    }
}
