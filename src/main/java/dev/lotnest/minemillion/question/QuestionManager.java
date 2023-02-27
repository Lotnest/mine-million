package dev.lotnest.minemillion.question;

import org.jetbrains.annotations.NotNull;

public record QuestionManager(@NotNull QuestionProvider questionProvider) {

    public Question getQuestion() {
        return questionProvider.getQuestion();
    }
}
