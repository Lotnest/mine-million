package dev.lotnest.minemillion.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
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

    public @NotNull CompletableFuture<MineMillionPlayer> getOrCreate(@NotNull Player player) {
        return getOrCreate(player.getUniqueId());
    }

    public @NotNull CompletableFuture<MineMillionPlayer> getOrCreate(@NotNull UUID playerUUID) {
        return CompletableFuture.supplyAsync(() -> {
            MineMillionPlayer cachedResult = cache.getIfPresent(playerUUID);

            if (cachedResult != null) {
                return cachedResult;
            }

            return dao.get(playerUUID)
                    .thenApply(optionalMineMillionPlayer -> {
                        MineMillionPlayer result = null;

                        if (optionalMineMillionPlayer.isEmpty()) {
                            result = MineMillionPlayer.builder()
                                    .uuid(playerUUID)
                                    .firstPlayedMillis(System.currentTimeMillis())
                                    .build();

                            dao.create(result);
                        }

                        if (result == null) {
                            result = optionalMineMillionPlayer.get();
                        }

                        cache.put(playerUUID, result);
                        return result;
                    })
                    .join();
        });
    }

    public @NotNull CompletableFuture<Optional<MineMillionPlayer>> get(@NotNull Player player) {
        return get(player.getUniqueId());
    }

    public @NotNull CompletableFuture<Optional<MineMillionPlayer>> get(@NotNull UUID playerUUID) {
        MineMillionPlayer cachedResult = cache.getIfPresent(playerUUID);

        if (cachedResult != null) {
            return CompletableFuture.completedFuture(Optional.of(cachedResult));
        }

        return dao.get(playerUUID);
    }

    public void invalidate(@NotNull UUID uuid) {
        cache.invalidate(uuid);
    }

    public void update(@NotNull MineMillionPlayer updatedMineMillionPlayer) {
        MineMillionPlayer player = cache.getIfPresent(updatedMineMillionPlayer.getUuid());
        dao.update(Objects.requireNonNullElse(player, updatedMineMillionPlayer));
        cache.put(updatedMineMillionPlayer.getUuid(), updatedMineMillionPlayer);
    }
}
