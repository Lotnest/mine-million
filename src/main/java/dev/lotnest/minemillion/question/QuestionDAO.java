package dev.lotnest.minemillion.question;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.db.BaseDAO;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface QuestionDAO extends BaseDAO {

    String QUESTION_TABLE_NAME = "questions";
    String QUESTION_TABLE_PRIMARY_KEY_NAME = QUESTION_TABLE_NAME + "PK";
    String ID_COLUMN_NAME = "id";
    String TEXT_COLUMN_NAME = "text";
    String ANSWER_COLUMN_NAME = "answer";
    String WRONG_ANSWER_1_COLUMN_NAME = "wrongAnswer1";
    String WRONG_ANSWER_2_COLUMN_NAME = "wrongAnswer2";
    String WRONG_ANSWER_3_COLUMN_NAME = "wrongAnswer3";
    String CATEGORY_COLUMN_NAME = "category";
    String DIFFICULTY_COLUMN_NAME = "difficulty";
    String TIMES_ASKED_COLUMN_NAME = "timesAsked";
    String TIMES_ANSWERED_CORRECTLY_COLUMN_NAME = "timesAnsweredCorrectly";
    String TIMES_ANSWERED_WRONG_COLUMN_NAME = "timesAnsweredWrong";
    String LAST_ASKED_AT_COLUMN_NAME = "lastAskedAt";
    String LAST_ANSWERED_CORRECTLY_AT_COLUMN_NAME = "lastAnsweredCorrectlyAt";
    String LAST_ANSWERED_WRONG_AT_COLUMN_NAME = "lastAnsweredWrongAt";

    @NotNull CompletableFuture<Optional<Question>> get(long id);

    @NotNull CompletableFuture<Optional<Question>> get(@NotNull String question);

    void create(@NotNull Question question);

    void update(@NotNull Question questionToUpdate, @NotNull Question updatedQuestion);

    default void update(long id, @NotNull Question updatedQuestion) {
        update(get(id).join()
                .orElseThrow(() -> new IllegalArgumentException(MineMillionPlugin.getInstance().getLanguageProvider().get("question.questionByIdNotFound", String.valueOf(id)))), updatedQuestion);
    }

    boolean delete(@NotNull Question question);

    default boolean delete(long id) {
        return delete(get(id).join()
                .orElseThrow(() -> new IllegalArgumentException(MineMillionPlugin.getInstance().getLanguageProvider().get("question.questionByIdNotFound", String.valueOf(id)))));
    }

    @NotNull List<Question> getAll();
}
