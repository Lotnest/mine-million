package dev.lotnest.minemillion.player;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
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
            DSLContext dslContext = getConnectionHolder().getDSLContext();
            return dslContext.selectFrom(PLAYER_TABLE_NAME)
                    .where(DSL.field(UUID_COLUMN_NAME).eq(uuid.toString()))
                    .fetchOptionalInto(MineMillionPlayer.class);
        });
    }

    @Override
    public void create(@NotNull MineMillionPlayer mineMillionPlayer) {
        getConnectionHolder().getDSLContext()
                .insertInto(DSL.table(PLAYER_TABLE_NAME))
                .columns(DSL.field(UUID_COLUMN_NAME),
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
                .values(mineMillionPlayer.getUuid().toString(),
                        mineMillionPlayer.getFirstPlayedMillis(),
                        mineMillionPlayer.getLastPlayedMillis(),
                        mineMillionPlayer.getGamesPlayed(),
                        mineMillionPlayer.getGamesWon(),
                        mineMillionPlayer.getGamesLost(),
                        mineMillionPlayer.getCorrectAnswers(),
                        mineMillionPlayer.getWrongAnswers(),
                        mineMillionPlayer.getCash(),
                        mineMillionPlayer.getCashWon(),
                        mineMillionPlayer.getHighestCashWon(),
                        mineMillionPlayer.getLifelineFiftyFiftyUsed(),
                        mineMillionPlayer.getLifelinePhoneAFriendUsed(),
                        mineMillionPlayer.getLifelineAskTheAudienceUsed(),
                        mineMillionPlayer.getLifelineDoubleDipUsed(),
                        mineMillionPlayer.getLifelineSwitchTheQuestionUsed()
                )
                .executeAsync();
    }

    @Override
    public void update(@NotNull MineMillionPlayer updatedMineMillionPlayer) {
        getConnectionHolder().getDSLContext()
                .update(DSL.table(PLAYER_TABLE_NAME))
                .set(DSL.field(FIRST_PLAYED_MILLIS_COLUMN_NAME), updatedMineMillionPlayer.getFirstPlayedMillis())
                .set(DSL.field(LAST_PLAYED_MILLIS_COLUMN_NAME), updatedMineMillionPlayer.getLastPlayedMillis())
                .set(DSL.field(GAMES_PLAYED_COLUMN_NAME), updatedMineMillionPlayer.getGamesPlayed())
                .set(DSL.field(GAMES_WON_COLUMN_NAME), updatedMineMillionPlayer.getGamesWon())
                .set(DSL.field(GAMES_LOST_COLUMN_NAME), updatedMineMillionPlayer.getGamesLost())
                .set(DSL.field(CORRECT_ANSWERS_COLUMN_NAME), updatedMineMillionPlayer.getCorrectAnswers())
                .set(DSL.field(WRONG_ANSWERS_COLUMN_NAME), updatedMineMillionPlayer.getWrongAnswers())
                .set(DSL.field(CASH_COLUMN_NAME), updatedMineMillionPlayer.getCash())
                .set(DSL.field(CASH_WON_COLUMN_NAME), updatedMineMillionPlayer.getCashWon())
                .set(DSL.field(HIGHEST_CASH_WON_COLUMN_NAME), updatedMineMillionPlayer.getHighestCashWon())
                .set(DSL.field(LIFELINE_FIFTY_FIFTY_USED_COLUMN_NAME), updatedMineMillionPlayer.getLifelineFiftyFiftyUsed())
                .set(DSL.field(LIFELINE_PHONE_A_FRIEND_USED_COLUMN_NAME), updatedMineMillionPlayer.getLifelinePhoneAFriendUsed())
                .set(DSL.field(LIFELINE_ASK_THE_AUDIENCE_USED_COLUMN_NAME), updatedMineMillionPlayer.getLifelineAskTheAudienceUsed())
                .set(DSL.field(LIFELINE_DOUBLE_DIP_USED_COLUMN_NAME), updatedMineMillionPlayer.getLifelineDoubleDipUsed())
                .set(DSL.field(LIFELINE_SWITCH_THE_QUESTION_USED_COLUMN_NAME), updatedMineMillionPlayer.getLifelineSwitchTheQuestionUsed())
                .where(DSL.field(UUID_COLUMN_NAME).eq(updatedMineMillionPlayer.getUuid().toString()))
                .executeAsync();
    }
}
