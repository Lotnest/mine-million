package dev.lotnest.minemillion.question;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class QuestionDAOImpl implements QuestionDAO {

    public QuestionDAOImpl() {
        DSLContext dslContext = getConnectionHolder().getDSLContext();
        dslContext.createTableIfNotExists(QUESTION_TABLE_NAME)
                .column(ID_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(TEXT_COLUMN_NAME, SQLDataType.VARCHAR(255).nullable(false))
                .column(ANSWER_COLUMN_NAME, SQLDataType.VARCHAR(255).nullable(false))
                .column(WRONG_ANSWER_1_COLUMN_NAME, SQLDataType.VARCHAR(255).nullable(false))
                .column(WRONG_ANSWER_2_COLUMN_NAME, SQLDataType.VARCHAR(255).nullable(false))
                .column(WRONG_ANSWER_3_COLUMN_NAME, SQLDataType.VARCHAR(255).nullable(false))
                .column(DIFFICULTY_COLUMN_NAME, SQLDataType.VARCHAR(255).nullable(false))
                .column(TIMES_ASKED_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(TIMES_ANSWERED_CORRECTLY_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(TIMES_ANSWERED_WRONG_COLUMN_NAME, SQLDataType.BIGINT.nullable(false))
                .column(LAST_ASKED_AT_COLUMN_NAME, SQLDataType.LOCALDATETIME.nullable(true))
                .column(LAST_ANSWERED_CORRECTLY_AT_COLUMN_NAME, SQLDataType.LOCALDATETIME.nullable(true))
                .column(LAST_ANSWERED_WRONG_AT_COLUMN_NAME, SQLDataType.LOCALDATETIME.nullable(true))
                .column(CATEGORY_COLUMN_NAME, SQLDataType.VARCHAR(255).nullable(false))
                .constraints(DSL.constraint(QUESTION_TABLE_PRIMARY_KEY_NAME).primaryKey(ID_COLUMN_NAME))
                .execute();
    }

    @Override
    public @NotNull CompletableFuture<Optional<Question>> get(@NotNull Long id) {
        return CompletableFuture.supplyAsync(() -> {
            DSLContext dslContext = getConnectionHolder().getDSLContext();
            return dslContext.selectFrom(QUESTION_TABLE_NAME)
                    .where(DSL.field(ID_COLUMN_NAME).eq(id))
                    .fetchOptionalInto(Question.class);
        });
    }

    @Override
    public @NotNull CompletableFuture<Optional<Question>> get(@NotNull String question) {
        return CompletableFuture.supplyAsync(() -> {
            DSLContext dslContext = getConnectionHolder().getDSLContext();
            return dslContext.selectFrom(QUESTION_TABLE_NAME)
                    .where(DSL.field(TEXT_COLUMN_NAME).eq(question))
                    .fetchOptionalInto(Question.class);
        });
    }

    @Override
    public void create(@NotNull Question question) {
        DSLContext dslContext = getConnectionHolder().getDSLContext();
        dslContext.insertInto(DSL.table(QUESTION_TABLE_NAME))
                .columns(DSL.field(ID_COLUMN_NAME),
                        DSL.field(TEXT_COLUMN_NAME),
                        DSL.field(ANSWER_COLUMN_NAME),
                        DSL.field(WRONG_ANSWER_1_COLUMN_NAME),
                        DSL.field(WRONG_ANSWER_2_COLUMN_NAME),
                        DSL.field(WRONG_ANSWER_3_COLUMN_NAME),
                        DSL.field(DIFFICULTY_COLUMN_NAME),
                        DSL.field(TIMES_ASKED_COLUMN_NAME),
                        DSL.field(TIMES_ANSWERED_CORRECTLY_COLUMN_NAME),
                        DSL.field(TIMES_ANSWERED_WRONG_COLUMN_NAME),
                        DSL.field(LAST_ASKED_AT_COLUMN_NAME),
                        DSL.field(LAST_ANSWERED_CORRECTLY_AT_COLUMN_NAME),
                        DSL.field(LAST_ANSWERED_WRONG_AT_COLUMN_NAME),
                        DSL.field(CATEGORY_COLUMN_NAME))
                .values(question.getId(),
                        question.getText(),
                        question.getAnswer(),
                        question.getWrongAnswer1(),
                        question.getWrongAnswer2(),
                        question.getWrongAnswer3(),
                        question.getDifficulty().name(),
                        question.getTimesAsked(),
                        question.getTimesAnsweredCorrectly(),
                        question.getTimesAnsweredWrong(),
                        question.getLastAskedAt(),
                        question.getLastAnsweredCorrectlyAt(),
                        question.getLastAnsweredWrongAt(),
                        question.getCategory().name())
                .executeAsync();
    }

    @Override
    public void update(@NotNull Question question) {
        DSLContext dslContext = getConnectionHolder().getDSLContext();
        dslContext.update(DSL.table(QUESTION_TABLE_NAME))
                .set(DSL.field(TEXT_COLUMN_NAME), question.getText())
                .set(DSL.field(ANSWER_COLUMN_NAME), question.getAnswer())
                .set(DSL.field(WRONG_ANSWER_1_COLUMN_NAME), question.getWrongAnswer1())
                .set(DSL.field(WRONG_ANSWER_2_COLUMN_NAME), question.getWrongAnswer2())
                .set(DSL.field(WRONG_ANSWER_3_COLUMN_NAME), question.getWrongAnswer3())
                .set(DSL.field(DIFFICULTY_COLUMN_NAME), question.getDifficulty().name())
                .set(DSL.field(TIMES_ASKED_COLUMN_NAME), question.getTimesAsked())
                .set(DSL.field(TIMES_ANSWERED_CORRECTLY_COLUMN_NAME), question.getTimesAnsweredCorrectly())
                .set(DSL.field(TIMES_ANSWERED_WRONG_COLUMN_NAME), question.getTimesAnsweredWrong())
                .set(DSL.field(LAST_ASKED_AT_COLUMN_NAME), question.getLastAskedAt())
                .set(DSL.field(LAST_ANSWERED_CORRECTLY_AT_COLUMN_NAME), question.getLastAnsweredCorrectlyAt())
                .set(DSL.field(LAST_ANSWERED_WRONG_AT_COLUMN_NAME), question.getLastAnsweredWrongAt())
                .set(DSL.field(CATEGORY_COLUMN_NAME), question.getCategory().name())
                .where(DSL.field(ID_COLUMN_NAME).eq(question.getId()))
                .executeAsync();
    }

    @Override
    public void delete(@NotNull Question question) {
        DSLContext dslContext = getConnectionHolder().getDSLContext();
        dslContext.delete(DSL.table(QUESTION_TABLE_NAME))
                .where(DSL.field(ID_COLUMN_NAME).eq(question.getId()))
                .executeAsync();
    }

    @Override
    public void delete(@NotNull Long id) {
        DSLContext dslContext = getConnectionHolder().getDSLContext();
        dslContext.delete(DSL.table(QUESTION_TABLE_NAME))
                .where(DSL.field(ID_COLUMN_NAME).eq(id))
                .executeAsync();
    }

    @Override
    public @NotNull List<Question> getAll() {
        return getConnectionHolder().getDSLContext()
                .selectFrom(QUESTION_TABLE_NAME)
                .fetchInto(Question.class);
    }
}
