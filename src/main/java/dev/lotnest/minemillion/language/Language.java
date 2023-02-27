package dev.lotnest.minemillion.language;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
@Getter
public enum Language {

    ENGLISH_US("english (US)", "english (US)", "en_US"),
    POLISH_PL("polish", "polski", "pl_PL");

    private static final Language[] VALUES = values();
    private static final Collection<String> LANGUAGES_AS_STRINGS = Arrays.stream(VALUES).map(Language::name).toList();

    private final String englishName;
    private final String nativeName;
    private final String code;

    public static Language fromCode(String code) {
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

    public static Collection<String> getLanguagesAsStrings() {
        return LANGUAGES_AS_STRINGS;
    }
}
