package dev.lotnest.minemillion.question;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import dev.lotnest.minemillion.util.LogUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public class QuestionProvider {

    private final Set<Question> questions;
    private final QuestionDAO questionDAO;

    public QuestionProvider() {
        questionDAO = new QuestionDAOImpl();
        questions = Sets.newHashSet(questionDAO.getAll());
    }

    public @NotNull Optional<Question> getQuestion() {
        return questions.stream().findAny();
    }

    public @NotNull ImmutableList<Question> getQuestions() {
        return ImmutableList.copyOf(questions);
    }

    public void addQuestion(@NotNull Question question) {
        questions.add(question);
        questionDAO.create(question);
    }

    public boolean removeQuestion(@NotNull Question question) {
        questions.remove(question);
        return questionDAO.delete(question);
    }

    public boolean removeQuestion(long id) {
        questions.removeIf(question -> question.getId() == id);
        return questionDAO.delete(id);
    }

    public void updateQuestion(@NotNull Question questionToUpdate, @NotNull Question updatedQuestion) {
        questionDAO.update(questionToUpdate, updatedQuestion);
    }

    public void updateQuestion(long id, @NotNull Question question) {
        questionDAO.update(id, question);
    }

    public boolean reload() {
        try {
            questions.clear();
            questions.addAll(questionDAO.getAll());
            return true;
        } catch (Exception exception) {
            LogUtil.severe("command.question.reload.failedToReloadQuestions", exception);
            return false;
        }
    }
}
