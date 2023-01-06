package dev.lotnest.minemillion.language;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {

    ENGLISH_US("english_US", "english_US", "en_US"),
    POLISH("polish", "polski", "pl");

    private final String englishName;
    private final String nativeName;
    private final String code;
}
