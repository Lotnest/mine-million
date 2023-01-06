package dev.lotnest.minemillion.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    private StringUtil() {
    }

    public static String capitalize(String string) {
        if (StringUtils.isBlank(string)) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String capitalizeFully(String string) {
        if (StringUtils.isBlank(string)) {
            return "";
        }

        String[] words = string.split(" ");
        StringBuilder wordsBuilder = new StringBuilder();

        for (String word : words) {
            wordsBuilder.append(capitalize(word)).append(" ");
        }

        return wordsBuilder.toString().trim();
    }

    public static String join(String... strings) {
        return join(", ", strings);
    }

    public static String join(Iterable<String> strings) {
        return join(", ", strings);
    }

    public static String join(String separator, String... strings) {
        return String.join(separator, strings);
    }

    public static String join(String separator, Iterable<String> strings) {
        return String.join(separator, strings);
    }
}
