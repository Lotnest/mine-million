package dev.lotnest.minemillion.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
class StringUtilTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "a", "ab", "ab cd"})
    void capitalize_variousInputs(String string) {
        // WHEN
        String result = StringUtil.capitalize(string);

        // THEN
        assertThat(result).isEqualTo(buildExpectedResult(string, false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "ab", "ab cd"})
    void capitalizeFully_variousInputs(String string) {
        // WHEN
        String result = StringUtil.capitalizeFully(string);

        // THEN
        assertThat(result).isEqualTo(buildExpectedResult(string, true));
    }

    private String buildExpectedResult(String string, boolean fully) {
        if (StringUtils.isBlank(string)) {
            return string;
        }

        String[] words = string.split(" ");
        StringBuilder wordsBuilder = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                wordsBuilder.append(" ");
            }

            if (i == 0 || fully) {
                wordsBuilder.append(Character.toUpperCase(words[i].charAt(0)));
                wordsBuilder.append(words[i].substring(1).toLowerCase());
            } else {
                wordsBuilder.append(words[i]);
            }
        }

        return wordsBuilder.toString();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "a;b"})
    void join_variousInputs(String expectedResult) {
        // WHEN
        String[] strings = expectedResult.split(";");
        String separator = ";";
        String result = StringUtil.join(separator, strings);

        // THEN
        assertThat(result).isEqualTo(expectedResult);
    }
}
