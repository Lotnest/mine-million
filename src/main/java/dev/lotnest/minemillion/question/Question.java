package dev.lotnest.minemillion.question;

import dev.lotnest.minemillion.util.ColorConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

    public @NotNull List<String> getAnswers() {
        List<String> options = Arrays.asList(answer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
        Collections.shuffle(options);
        return IntStream.range(0, options.size())
                .mapToObj(i -> ColorConstants.GOLD + new String[]{"A: ", "B: ", "C: ", "D: "}[i] + ColorConstants.BLUE + options.get(i))
                .toList();
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

        public static @NotNull Optional<Category> fromLanguageLabel(@NotNull String category) {
            return fromText(category);
        }

        public static @NotNull Category fromLanguageLabelOrDefault(@NotNull String category) {
            return fromLanguageLabel(category).orElse(GENERAL_KNOWLEDGE);
        }
    }

    @RequiredArgsConstructor
    @Getter
    public enum Difficulty {

        EASY(ChatColor.GREEN),
        MEDIUM(ChatColor.YELLOW),
        HARD(ChatColor.RED),
        EXPERT(ChatColor.BLUE);

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

        public static @NotNull Optional<Difficulty> fromLanguageLabel(@NotNull String difficulty) {
            return fromText(difficulty);
        }
    }
}
