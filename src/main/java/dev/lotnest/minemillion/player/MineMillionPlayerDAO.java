package dev.lotnest.minemillion.player;

import dev.lotnest.minemillion.db.BaseDAO;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface MineMillionPlayerDAO extends BaseDAO {

    Optional<MineMillionPlayer> get(@NotNull UUID uuid);

    default Optional<MineMillionPlayer> get(@NotNull Player player) {
        return get(player.getUniqueId());
    }

    void create(@NotNull MineMillionPlayer player);

    void update(@NotNull MineMillionPlayer updatedPlayer);
}
