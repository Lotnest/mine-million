package dev.lotnest.minemillion.util;

import dev.lotnest.minemillion.MineMillionPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class LoggerUtil {

    private static final MineMillionPlugin PLUGIN = MineMillionPlugin.getInstance();

    private LoggerUtil() {
    }

    public static void infoMessage(@NotNull String message) {
        PLUGIN.getLogger().info(message);
    }

    public static void info(@NotNull String languageProviderKey, @Nullable String... placeholders) {
        infoMessage(PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders));
    }

    public static void warning(@NotNull String languageProviderKey, @Nullable String... placeholders) {
        PLUGIN.getLogger().warning(PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders));
    }

    public static void warning(@NotNull String languageProviderKey, @Nullable Throwable throwable, @Nullable String... placeholders) {
        PLUGIN.getLogger().log(Level.WARNING, PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders), throwable);
    }

    public static void severe(@NotNull String languageProviderKey, @Nullable String... placeholders) {
        severe(languageProviderKey, null, placeholders);
    }

    public static void severe(@NotNull String languageProviderKey, @Nullable Throwable throwable, @Nullable String... placeholders) {
        PLUGIN.getLogger().log(Level.SEVERE, PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders), throwable);
    }

    public static void unknownError(@NotNull Throwable throwable) {
        severe("error.unknown", throwable);
    }
}
