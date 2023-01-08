package dev.lotnest.minemillion.util;

import dev.lotnest.minemillion.MineMillionPlugin;

import java.util.logging.Level;

public class LoggerUtil {

    private static final MineMillionPlugin PLUGIN = MineMillionPlugin.getInstance();

    private LoggerUtil() {
    }

    public static void infoMessage(String message) {
        PLUGIN.getLogger().info(message);
    }

    public static void info(String languageProviderKey, String... placeholders) {
        infoMessage(PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders));
    }

    public static void warning(String languageProviderKey, String... placeholders) {
        PLUGIN.getLogger().warning(PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders));
    }

    public static void warning(String languageProviderKey, Throwable throwable, String... placeholders) {
        PLUGIN.getLogger().log(Level.WARNING, PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders), throwable);
    }

    public static void severe(String languageProviderKey, String... placeholders) {
        severe(languageProviderKey, null, placeholders);
    }

    public static void severe(String languageProviderKey, Throwable throwable, String... placeholders) {
        PLUGIN.getLogger().log(Level.SEVERE, PLUGIN.getLanguageProvider().get(languageProviderKey, placeholders), throwable);
    }
}
