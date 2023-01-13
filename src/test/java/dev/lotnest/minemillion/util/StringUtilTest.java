package dev.lotnest.minemillion.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void getCompactedCash_0() {
        // WHEN
        String result = StringUtil.getCompactedCash(0);

        // THEN
        assertThat(result).isEqualTo("0 USD");
    }

    @Test
    void getCompactedCash_1K() {
        // WHEN
        String result = StringUtil.getCompactedCash(1000);

        // THEN
        assertThat(result).isEqualTo("1K USD");
    }

    @Test
    void getCompactedCash_1M() {
        // WHEN
        String result = StringUtil.getCompactedCash(1000000);

        // THEN
        assertThat(result).isEqualTo("1M USD");
    }

    @Test
    void getSeperatedCash_0() {
        // WHEN
        String result = StringUtil.getSeperatedCash(0);

        // THEN
        assertThat(result).isEqualTo("0 USD");
    }

    @Test
    void getSeperatedCash_1K() {
        // WHEN
        String result = StringUtil.getSeperatedCash(1000);

        // THEN
        assertThat(result).isEqualTo("1,000 USD");
    }

    @Test
    void getSeperatedCash_1M() {
        // WHEN
        String result = StringUtil.getSeperatedCash(1000000);

        // THEN
        assertThat(result).isEqualTo("1,000,000 USD");
    }
}
