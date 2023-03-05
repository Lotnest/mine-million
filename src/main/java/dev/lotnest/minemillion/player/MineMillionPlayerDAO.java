package dev.lotnest.minemillion.player;

import dev.lotnest.minemillion.db.BaseDAO;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MineMillionPlayerDAO extends BaseDAO {

    String PLAYER_TABLE_NAME = "player";
    String PLAYER_TABLE_PRIMARY_KEY_NAME = PLAYER_TABLE_NAME + "PK";
    String UUID_COLUMN_NAME = "uuid";
    String FIRST_PLAYED_MILLIS_COLUMN_NAME = "firstPlayedMillis";
    String LAST_PLAYED_MILLIS_COLUMN_NAME = "lastPlayedMillis";
    String GAMES_PLAYED_COLUMN_NAME = "gamesPlayed";
    String GAMES_WON_COLUMN_NAME = "gamesWon";
    String GAMES_LOST_COLUMN_NAME = "gamesLost";
    String CORRECT_ANSWERS_COLUMN_NAME = "correctAnswers";
    String WRONG_ANSWERS_COLUMN_NAME = "wrongAnswers";
    String CASH_COLUMN_NAME = "cash";
    String CASH_WON_COLUMN_NAME = "cashWon";
    String HIGHEST_CASH_WON_COLUMN_NAME = "highestCashWon";
    String LIFELINE_FIFTY_FIFTY_USED_COLUMN_NAME = "lifelineFiftyFiftyUsed";
    String LIFELINE_PHONE_A_FRIEND_USED_COLUMN_NAME = "lifelinePhoneAFriendUsed";
    String LIFELINE_ASK_THE_AUDIENCE_USED_COLUMN_NAME = "lifelineAskTheAudienceUsed";
    String LIFELINE_DOUBLE_DIP_USED_COLUMN_NAME = "lifelineDoubleDipUsed";
    String LIFELINE_SWITCH_THE_QUESTION_USED_COLUMN_NAME = "lifelineSwitchTheQuestionUsed";

    @NotNull CompletableFuture<Optional<MineMillionPlayer>> get(@NotNull UUID uuid);

    default @NotNull CompletableFuture<Optional<MineMillionPlayer>> get(@NotNull Player player) {
        return get(player.getUniqueId());
    }

    void create(@NotNull MineMillionPlayer mineMillionPlayer);

    void update(@NotNull MineMillionPlayer updatedMineMillionPlayer);
}
