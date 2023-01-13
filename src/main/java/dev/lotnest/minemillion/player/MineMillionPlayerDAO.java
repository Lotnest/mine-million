package dev.lotnest.minemillion.player;

import dev.lotnest.minemillion.db.BaseDAO;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MineMillionPlayerDAO extends BaseDAO {

    String PLAYER_TABLE_NAME = "player";
    String PLAYER_TABLE_PRIMARY_KEY_NAME = PLAYER_TABLE_NAME + "_pk";
    String UUID_COLUMN_NAME = "uuid";
    String FIRST_PLAYED_MILLIS_COLUMN_NAME = "first_played_millis";
    String LAST_PLAYED_MILLIS_COLUMN_NAME = "last_played_millis";
    String GAMES_PLAYED_COLUMN_NAME = "games_played";
    String GAMES_WON_COLUMN_NAME = "games_won";
    String GAMES_LOST_COLUMN_NAME = "games_lost";
    String CORRECT_ANSWERS_COLUMN_NAME = "correct_answers";
    String WRONG_ANSWERS_COLUMN_NAME = "wrong_answers";
    String CASH_COLUMN_NAME = "cash";
    String CASH_WON_COLUMN_NAME = "cash_won";
    String HIGHEST_CASH_WON_COLUMN_NAME = "highest_cash_won";
    String LIFELINE_FIFTY_FIFTY_USED_COLUMN_NAME = "lifeline_fifty_fifty_used";
    String LIFELINE_PHONE_A_FRIEND_USED_COLUMN_NAME = "lifeline_phone_a_friend_used";
    String LIFELINE_ASK_THE_AUDIENCE_USED_COLUMN_NAME = "lifeline_ask_the_audience_used";
    String LIFELINE_DOUBLE_DIP_USED_COLUMN_NAME = "lifeline_double_dip_used";
    String LIFELINE_SWITCH_THE_QUESTION_USED_COLUMN_NAME = "lifeline_switch_the_question_used";

    @NotNull CompletableFuture<Optional<MineMillionPlayer>> get(@NotNull UUID uuid);

    default @NotNull CompletableFuture<Optional<MineMillionPlayer>> get(@NotNull Player player) {
        return get(player.getUniqueId());
    }

    void create(@NotNull MineMillionPlayer player);

    void update(@NotNull MineMillionPlayer updatedPlayer);
}
