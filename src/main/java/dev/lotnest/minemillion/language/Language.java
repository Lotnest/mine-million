package dev.lotnest.minemillion.language;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Language {

    ENGLISH_US("English (US)", "English (US)", "en_US"),
    POLISH_PL("Polish", "polski", "pl_PL");

    private static final Language[] VALUES = values();
    private static final List<String> LANGUAGES_AS_STRINGS = Arrays.stream(VALUES).map(Language::name).toList();

    private final String englishName;
    private final String nativeName;
    private final String code;

    public static @NotNull Language fromCode(@Nullable String code) {
        if (StringUtils.isBlank(code)) {
            return ENGLISH_US;
        }

        for (Language language : VALUES) {
            if (language.getCode().equalsIgnoreCase(code)) {
                return language;
            }
        }

        return ENGLISH_US;
    }

    public static @NotNull List<String> getLanguagesAsStrings() {
        return LANGUAGES_AS_STRINGS;
    }
}
