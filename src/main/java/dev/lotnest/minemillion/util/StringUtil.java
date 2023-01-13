package dev.lotnest.minemillion.util;

import dev.lotnest.minemillion.MineMillionPlugin;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtil {

    private StringUtil() {
    }

    public static @NotNull String capitalize(String string) {
        if (StringUtils.isBlank(string)) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static @NotNull String capitalizeFully(String string) {
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

    @Contract("_ -> new")
    public static @NotNull String join(@NotNull String... strings) {
        return join(", ", strings);
    }

    @Contract("_ -> new")
    public static @NotNull String join(@NotNull Iterable<String> strings) {
        return join(", ", strings);
    }

    @Contract("_, _ -> new")
    public static @NotNull String join(@NotNull String separator, @NotNull String... strings) {
        return String.join(separator, strings);
    }

    @Contract("_, _ -> new")
    public static @NotNull String join(@NotNull String separator, @NotNull Iterable<String> strings) {
        return String.join(separator, strings);
    }

    public static @NotNull String getCompactedCash(long cash) {
        return NumberFormat.getCompactNumberInstance().format(cash) + " " +
                MineMillionPlugin.getInstance().getLanguageProvider().get("currency.code");
    }

    public static @NotNull String getSeperatedCash(long cash) {
        return NumberFormat.getCurrencyInstance(Locale.US)
                .format(cash)
                .replace("$", "")
                .replace(".00", "")
                + " "
                + MineMillionPlugin.getInstance().getLanguageProvider().get("currency.code");
    }
}
