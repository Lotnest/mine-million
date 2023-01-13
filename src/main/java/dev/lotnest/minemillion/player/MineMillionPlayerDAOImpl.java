package dev.lotnest.minemillion.player;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MineMillionPlayerDAOImpl implements MineMillionPlayerDAO {

    public MineMillionPlayerDAOImpl() {
        DSLContext dslContext = getConnectionHolder().getDSLContext();
        dslContext.createTableIfNotExists(PLAYER_TABLE_NAME)
                .column(UUID_COLUMN_NAME, SQLDataType.UUID.nullable(false))
                .column(FIRST_PLAYED_MILLIS_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(LAST_PLAYED_MILLIS_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(GAMES_PLAYED_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(GAMES_WON_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(GAMES_LOST_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(CORRECT_ANSWERS_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(WRONG_ANSWERS_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(CASH_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(CASH_WON_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(HIGHEST_CASH_WON_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(LIFELINE_FIFTY_FIFTY_USED_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(LIFELINE_PHONE_A_FRIEND_USED_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(LIFELINE_ASK_THE_AUDIENCE_USED_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(LIFELINE_DOUBLE_DIP_USED_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(LIFELINE_SWITCH_THE_QUESTION_USED_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .constraints(DSL.constraint(PLAYER_TABLE_PRIMARY_KEY_NAME).primaryKey(UUID_COLUMN_NAME))
                .execute();
    }

    @Override
    public @NotNull CompletableFuture<Optional<MineMillionPlayer>> get(@NotNull UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            Record jooqRecord = getConnectionHolder().getDSLContext()
                    .select()
                    .from(PLAYER_TABLE_NAME)
                    .where(DSL.field(UUID_COLUMN_NAME).eq(uuid.toString()))
                    .fetchOne();
            if (jooqRecord == null) {
                return Optional.empty();
            }

            return Optional.of(
                    MineMillionPlayer.builder()
                            .uuid(UUID.fromString(jooqRecord.get(UUID_COLUMN_NAME, String.class)))
                            .firstPlayedMillis(jooqRecord.get(FIRST_PLAYED_MILLIS_COLUMN_NAME, Long.class))
                            .lastPlayedMillis(jooqRecord.get(LAST_PLAYED_MILLIS_COLUMN_NAME, Long.class))
                            .gamesPlayed(jooqRecord.get(GAMES_PLAYED_COLUMN_NAME, Long.class))
                            .gamesWon(jooqRecord.get(GAMES_WON_COLUMN_NAME, Long.class))
                            .gamesLost(jooqRecord.get(GAMES_LOST_COLUMN_NAME, Long.class))
                            .correctAnswers(jooqRecord.get(CORRECT_ANSWERS_COLUMN_NAME, Long.class))
                            .wrongAnswers(jooqRecord.get(WRONG_ANSWERS_COLUMN_NAME, Long.class))
                            .cash(jooqRecord.get(CASH_COLUMN_NAME, Long.class))
                            .cashWon(jooqRecord.get(CASH_WON_COLUMN_NAME, Long.class))
                            .highestCashWon(jooqRecord.get(HIGHEST_CASH_WON_COLUMN_NAME, Long.class))
                            .lifelineFiftyFiftyUsed(jooqRecord.get(LIFELINE_FIFTY_FIFTY_USED_COLUMN_NAME, Long.class))
                            .lifelinePhoneAFriendUsed(jooqRecord.get(LIFELINE_PHONE_A_FRIEND_USED_COLUMN_NAME, Long.class))
                            .lifelineAskTheAudienceUsed(jooqRecord.get(LIFELINE_ASK_THE_AUDIENCE_USED_COLUMN_NAME, Long.class))
                            .lifelineDoubleDipUsed(jooqRecord.get(LIFELINE_DOUBLE_DIP_USED_COLUMN_NAME, Long.class))
                            .lifelineSwitchTheQuestionUsed(jooqRecord.get(LIFELINE_SWITCH_THE_QUESTION_USED_COLUMN_NAME, Long.class))
                            .build()
            );
        });
    }

    @Override
    public void create(@NotNull MineMillionPlayer player) {
        getConnectionHolder().getDSLContext()
                .insertInto(DSL.table(PLAYER_TABLE_NAME))
                .columns(
                        DSL.field(UUID_COLUMN_NAME),
                        DSL.field(FIRST_PLAYED_MILLIS_COLUMN_NAME),
                        DSL.field(LAST_PLAYED_MILLIS_COLUMN_NAME),
                        DSL.field(GAMES_PLAYED_COLUMN_NAME),
                        DSL.field(GAMES_WON_COLUMN_NAME),
                        DSL.field(GAMES_LOST_COLUMN_NAME),
                        DSL.field(CORRECT_ANSWERS_COLUMN_NAME),
                        DSL.field(WRONG_ANSWERS_COLUMN_NAME),
                        DSL.field(CASH_COLUMN_NAME),
                        DSL.field(CASH_WON_COLUMN_NAME),
                        DSL.field(HIGHEST_CASH_WON_COLUMN_NAME),
                        DSL.field(LIFELINE_FIFTY_FIFTY_USED_COLUMN_NAME),
                        DSL.field(LIFELINE_PHONE_A_FRIEND_USED_COLUMN_NAME),
                        DSL.field(LIFELINE_ASK_THE_AUDIENCE_USED_COLUMN_NAME),
                        DSL.field(LIFELINE_DOUBLE_DIP_USED_COLUMN_NAME),
                        DSL.field(LIFELINE_SWITCH_THE_QUESTION_USED_COLUMN_NAME)
                )
                .executeAsync();
    }

    @Override
    public void update(@NotNull MineMillionPlayer updatedPlayer) {
        getConnectionHolder().getDSLContext()
                .update(DSL.table(PLAYER_TABLE_NAME))
                .set(DSL.field(FIRST_PLAYED_MILLIS_COLUMN_NAME), updatedPlayer.getFirstPlayedMillis())
                .set(DSL.field(LAST_PLAYED_MILLIS_COLUMN_NAME), updatedPlayer.getLastPlayedMillis())
                .set(DSL.field(GAMES_PLAYED_COLUMN_NAME), updatedPlayer.getGamesPlayed())
                .set(DSL.field(GAMES_WON_COLUMN_NAME), updatedPlayer.getGamesWon())
                .set(DSL.field(GAMES_LOST_COLUMN_NAME), updatedPlayer.getGamesLost())
                .set(DSL.field(CORRECT_ANSWERS_COLUMN_NAME), updatedPlayer.getCorrectAnswers())
                .set(DSL.field(WRONG_ANSWERS_COLUMN_NAME), updatedPlayer.getWrongAnswers())
                .set(DSL.field(CASH_COLUMN_NAME), updatedPlayer.getCash())
                .set(DSL.field(CASH_WON_COLUMN_NAME), updatedPlayer.getCashWon())
                .set(DSL.field(HIGHEST_CASH_WON_COLUMN_NAME), updatedPlayer.getHighestCashWon())
                .set(DSL.field(LIFELINE_FIFTY_FIFTY_USED_COLUMN_NAME), updatedPlayer.getLifelineFiftyFiftyUsed())
                .set(DSL.field(LIFELINE_PHONE_A_FRIEND_USED_COLUMN_NAME), updatedPlayer.getLifelinePhoneAFriendUsed())
                .set(DSL.field(LIFELINE_ASK_THE_AUDIENCE_USED_COLUMN_NAME), updatedPlayer.getLifelineAskTheAudienceUsed())
                .set(DSL.field(LIFELINE_DOUBLE_DIP_USED_COLUMN_NAME), updatedPlayer.getLifelineDoubleDipUsed())
                .set(DSL.field(LIFELINE_SWITCH_THE_QUESTION_USED_COLUMN_NAME), updatedPlayer.getLifelineSwitchTheQuestionUsed())
                .where(DSL.field(UUID_COLUMN_NAME).eq(updatedPlayer.getUuid().toString()))
                .executeAsync();
    }
}
