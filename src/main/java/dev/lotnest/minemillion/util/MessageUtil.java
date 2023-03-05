package dev.lotnest.minemillion.util;

import dev.lotnest.minemillion.MineMillionPlugin;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class MessageUtil {

    private MessageUtil() {
    }

    public static void sendMessage(@NotNull CommandSender sender, @Nullable String message) {
        sender.sendMessage(message == null ? StringUtils.EMPTY : message);
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull String languageProviderKey, String... placeholders) {
        sendMessage(sender, StringUtils.EMPTY, languageProviderKey, placeholders);
    }

    public static void sendMessage(@NotNull CommandSender sender, @Nullable ChatColor color, @NotNull String languageProviderKey, String... placeholders) {
        sendMessage(sender, color != null ? color.toString() : StringUtils.EMPTY, languageProviderKey, placeholders);
    }

    public static void sendMessage(@NotNull CommandSender sender, @Nullable String prefix, @NotNull String languageProviderKey, String... placeholders) {
        sendMessage(sender, (prefix != null ? prefix : StringUtils.EMPTY) + MineMillionPlugin.getInstance().getLanguageProvider().get(languageProviderKey, placeholders));
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull Collection<String> messages) {
        messages.forEach(sender::sendMessage);
    }

    public static void sendInvalidUsageMessage(@NotNull CommandSender sender) {
        sendMessage(sender, ChatColor.RED, "command.invalidUsage", StringUtils.EMPTY);
    }

    public static @NotNull String color(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
