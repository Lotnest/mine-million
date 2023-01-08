package dev.lotnest.minemillion.player;

import dev.lotnest.minemillion.db.BaseDAO;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface MineMillionPlayerDAO extends BaseDAO {

    Optional<MineMillionPlayer> get(UUID uuid);

    default Optional<MineMillionPlayer> get(Player player) {
        if (player == null) {
            return Optional.empty();
        }
        return get(player.getUniqueId());
    }

    void create(MineMillionPlayer player);

    void update(MineMillionPlayer updatedPlayer);
}
