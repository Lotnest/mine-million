package dev.lotnest.minemillion.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MineMillionPlayerCache {

    private final Cache<UUID, MineMillionPlayer> cache;
    private final MineMillionPlayerDAO dao;

    public MineMillionPlayerCache(MineMillionPlayerDAO dao) {
        this.dao = Validate.notNull(dao, "MineMillionPlayerDAO can't be null");
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();
    }

    public MineMillionPlayer getOrCreate(Player player) {
        if (player == null) {
            return null;
        }
        return getOrCreate(player.getUniqueId());
    }

    public MineMillionPlayer getOrCreate(UUID playerUUID) {
        if (playerUUID == null) {
            return null;
        }

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

    public void invalidate(UUID uuid) {
        cache.invalidate(uuid);
    }

    public void update(MineMillionPlayer updatedMineMillionPlayer) {
        MineMillionPlayer player = cache.getIfPresent(updatedMineMillionPlayer.getPlayerUUID());
        if (player != null) {
            dao.update(player);
        }
    }
}
