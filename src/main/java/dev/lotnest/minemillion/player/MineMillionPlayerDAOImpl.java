package dev.lotnest.minemillion.player;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class MineMillionPlayerDAOImpl implements MineMillionPlayerDAO {

    private static final String CREATE_PLAYER_TABLE_IF_NOT_EXISTS_SQL = "CREATE TABLE IF NOT EXISTS `player` (" +
            "`player_uuid` VARCHAR(36) NOT NULL," +
            "`first_played_millis` BIGINT NOT NULL," +
            "`last_played_millis` BIGINT NOT NULL," +
            "`games_played` INT NOT NULL," +
            "`games_won` INT NOT NULL," +
            "`games_lost` INT NOT NULL," +
            "`correct_answers` INT NOT NULL," +
            "`wrong_answers` INT NOT NULL," +
            "`cash_won` INT NOT NULL," +
            "`best_cash_won` INT NOT NULL," +
            "`lifeline_fifty_fifty_used` INT NOT NULL," +
            "`lifeline_phone_a_friend_used` INT NOT NULL," +
            "`lifeline_ask_the_audience_used` INT NOT NULL," +
            "`lifeline_double_dip_used` INT NOT NULL," +
            "`lifeline_switch_the_question_used` INT NOT NULL," +
            "PRIMARY KEY (`player_uuid`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";

    private static final String CREATE_NEW_PLAYER_SQL = "INSERT INTO player (" +
            "uuid, first_played, last_played, games_played, games_won, games_lost, correct_answers, wrong_answers, cash_won," +
            " best_cash_won, lifeline_fifty_fifty_used, lifeline_phone_a_friend_used, lifeline_ask_the_audience_used, lifeline_double_dip_used," +
            " lifeline_switch_the_question_used) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_PLAYER_SQL = "SELECT * FROM player WHERE uuid = ?";

    private static final String UPDATE_PLAYER_SQL = "UPDATE player SET " +
            "first_played = ?, last_played = ?, games_played = ?, games_won = ?, games_lost = ?, correct_answers = ?, wrong_answers = ?, cash_won = ?," +
            " best_cash_won = ?, lifeline_fifty_fifty_used = ?, lifeline_phone_a_friend_used = ?, lifeline_ask_the_audience_used = ?, lifeline_double_dip_used = ?," +
            " lifeline_switch_the_question_used = ? WHERE uuid = ?";

    public MineMillionPlayerDAOImpl() {
        executeQuery(CREATE_PLAYER_TABLE_IF_NOT_EXISTS_SQL);
    }

    @Override
    public Optional<MineMillionPlayer> get(UUID uuid) {
        if (uuid == null) {
            return Optional.empty();
        }

        MineMillionPlayer[] player = {null};

        selectQuery(GET_PLAYER_SQL, resultSet -> {
            try {
                if (resultSet.next()) {
                    player[0] = MineMillionPlayer.builder()
                            .playerUUID(uuid)
                            .firstPlayedMillis(resultSet.getLong("first_played"))
                            .lastPlayedMillis(resultSet.getLong("last_played"))
                            .gamesPlayed(resultSet.getInt("games_played"))
                            .gamesWon(resultSet.getInt("games_won"))
                            .gamesLost(resultSet.getInt("games_lost"))
                            .correctAnswers(resultSet.getInt("correct_answers"))
                            .wrongAnswers(resultSet.getInt("wrong_answers"))
                            .cashWon(resultSet.getInt("cash_won"))
                            .bestCashWon(resultSet.getInt("best_cash_won"))
                            .lifelineFiftyFiftyUsed(resultSet.getInt("lifeline_fifty_fifty_used"))
                            .lifelinePhoneAFriendUsed(resultSet.getInt("lifeline_phone_a_friend_used"))
                            .lifelineAskTheAudienceUsed(resultSet.getInt("lifeline_ask_the_audience_used"))
                            .lifelineDoubleDipUsed(resultSet.getInt("lifeline_double_dip_used"))
                            .lifelineSwitchTheQuestionUsed(resultSet.getInt("lifeline_switch_the_question_used"))
                            .build();
                }
            } catch (SQLException exception) {
                plugin.getLogger().log(Level.SEVERE, languageProvider.get("database.sqlQueryFailed", GET_PLAYER_SQL), exception);
            }
        }, uuid.toString());

        return Optional.ofNullable(player[0]);
    }

    @Override
    public void create(MineMillionPlayer player) {
        if (player == null) {
            return;
        }

        executeQuery(CREATE_NEW_PLAYER_SQL, player.getPlayerUUID().toString(), player.getFirstPlayedMillis(), player.getLastPlayedMillis(),
                player.getGamesPlayed(), player.getGamesWon(), player.getGamesLost(), player.getCorrectAnswers(), player.getWrongAnswers(),
                player.getCashWon(), player.getBestCashWon(), player.getLifelineFiftyFiftyUsed(), player.getLifelinePhoneAFriendUsed(),
                player.getLifelineAskTheAudienceUsed(), player.getLifelineDoubleDipUsed(), player.getLifelineSwitchTheQuestionUsed());
    }

    @Override
    public void update(MineMillionPlayer updatedPlayer) {
        if (updatedPlayer == null) {
            return;
        }

        executeQuery(UPDATE_PLAYER_SQL, updatedPlayer.getFirstPlayedMillis(), updatedPlayer.getLastPlayedMillis(), updatedPlayer.getGamesPlayed(),
                updatedPlayer.getGamesWon(), updatedPlayer.getGamesLost(), updatedPlayer.getCorrectAnswers(), updatedPlayer.getWrongAnswers(),
                updatedPlayer.getCashWon(), updatedPlayer.getBestCashWon(), updatedPlayer.getLifelineFiftyFiftyUsed(),
                updatedPlayer.getLifelinePhoneAFriendUsed(), updatedPlayer.getLifelineAskTheAudienceUsed(), updatedPlayer.getLifelineDoubleDipUsed(),
                updatedPlayer.getLifelineSwitchTheQuestionUsed(), updatedPlayer.getPlayerUUID().toString());
    }
}
