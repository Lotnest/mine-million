package dev.lotnest.minemillion.question;

import com.google.common.collect.Sets;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class QuestionProvider {

    private final Set<Question> questions;
    private final QuestionDAO questionDAO;

    public QuestionProvider() {
        questions = Sets.newHashSet();
        questionDAO = new QuestionDAOImpl();

        questions.addAll(questionDAO.getAll());
    }

    public Question getQuestion() {
        return questions.stream().findAny().orElse(null);
    }

    public void addQuestion(@NotNull Question question) {
        questions.add(question);
        questionDAO.create(question);
    }

    public void removeQuestion(@NotNull Question question) {
        questions.remove(question);
        questionDAO.delete(question);
    }
}
