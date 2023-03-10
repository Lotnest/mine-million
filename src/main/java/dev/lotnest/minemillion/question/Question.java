package dev.lotnest.minemillion.question;

import com.google.common.collect.Lists;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.util.ColorConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    private long id;
    private String text;
    private String answer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    private Category category;
    private Difficulty difficulty;
    private long timesAsked;
    private long timesAnsweredCorrectly;
    private long timesAnsweredWrong;
    private LocalDateTime lastAskedAt;
    private LocalDateTime lastAnsweredCorrectlyAt;
    private LocalDateTime lastAnsweredWrongAt;

    private List<String> answers;

    public @NotNull List<String> getAnswers(boolean showCorrectAnswer) {
        List<String> shuffledAnswers = Lists.newArrayList(answer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
        Collections.shuffle(shuffledAnswers);

        List<String> result = Lists.newArrayListWithCapacity(4);
        for (int i = 0; i < shuffledAnswers.size(); i++) {
            String option = shuffledAnswers.get(i);
            String prefix = ColorConstants.GOLD + new String[]{"A: ", "B: ", "C: ", "D: "}[i] + ColorConstants.BLUE;
            String formattedAnswer = prefix + option;

            if (option.equals(answer) && showCorrectAnswer) {
                formattedAnswer += ColorConstants.GREEN + " (" + MineMillionPlugin.getInstance().getLanguageProvider().get("question.correctAnswer") + ")";
            }

            result.add(formattedAnswer);
        }

        answers = result;
        return result;
    }

    public @NotNull String[] getAnswersArray(boolean showCorrectAnswer) {
        return getAnswers(showCorrectAnswer).toArray(new String[0]);
    }

    public @NotNull List<String> shuffleAnswers(boolean showCorrectAnswer) {
        answers = null;
        return getAnswers(showCorrectAnswer);
    }

    public @NotNull String[] shuffleAnswersArray(boolean showCorrectAnswer) {
        return shuffleAnswers(showCorrectAnswer).toArray(new String[0]);
    }

    public @NotNull Optional<String> getAnswerFromLetter(@NotNull String letter) {
        return answers.stream()
                .filter(matchingAnswer -> ChatColor.stripColor(matchingAnswer).startsWith(letter.toUpperCase()))
                .map(matchingAnswer -> ChatColor.stripColor(matchingAnswer).substring(3))
                .findFirst();
    }

    public @NotNull String getAnswerWithOption() {
        return answers.stream()
                .filter(matchingAnswer -> ChatColor.stripColor(matchingAnswer).endsWith(answer))
                .findFirst()
                .orElse(answer);
    }

    public enum Category {

        GENERAL_KNOWLEDGE,
        ENTERTAINMENT_BOOKS,
        ENTERTAINMENT_FILM,
        ENTERTAINMENT_MUSIC,
        ENTERTAINMENT_MUSICALS_THEATRES,
        ENTERTAINMENT_TELEVISION,
        ENTERTAINMENT_VIDEO_GAMES,
        ENTERTAINMENT_BOARD_GAMES,
        SCIENCE_NATURE,
        SCIENCE_COMPUTERS,
        SCIENCE_MATHEMATICS,
        MYTHOLOGY,
        SPORTS,
        GEOGRAPHY,
        HISTORY,
        POLITICS,
        ART,
        CELEBRITIES,
        ANIMALS,
        VEHICLES,
        ENTERTAINMENT_COMICS,
        SCIENCE_GADGETS,
        ENTERTAINMENT_ANIME_MANGA,
        ENTERTAINMENT_CARTOONS_ANIMATIONS;

        private static final Category[] VALUES = values();

        public static @NotNull Optional<Category> fromText(@NotNull String text) {
            for (Category value : VALUES) {
                if (value.name().equalsIgnoreCase(text)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }

        public static @NotNull Category fromTextOrDefault(@NotNull String category) {
            return fromText(category).orElse(GENERAL_KNOWLEDGE);
        }

        public static @NotNull String toLanguageLabel(@NotNull Category category) {
            return category.name().toLowerCase();
        }
    }

    @RequiredArgsConstructor
    @Getter
    public enum Difficulty {

        EASY(ColorConstants.GREEN),
        MEDIUM(ColorConstants.YELLOW),
        HARD(ColorConstants.RED),
        EXPERT(ColorConstants.BLUE);

        private static final Difficulty[] VALUES = values();

        private final ChatColor color;

        public static @NotNull Optional<Difficulty> fromText(@NotNull String text) {
            for (Difficulty value : VALUES) {
                if (value.name().equalsIgnoreCase(text)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }

        public static @NotNull Difficulty fromTextOrDefault(@NotNull String difficulty) {
            return fromText(difficulty).orElse(EASY);
        }

        public static @NotNull String toLanguageLabel(@NotNull Difficulty difficulty) {
            return difficulty.name().toLowerCase();
        }
    }
}
