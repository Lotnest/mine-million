package dev.lotnest.minemillion.util;

import dev.lotnest.minemillion.MineMillionPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.tools.StringUtils;

public class MessageUtil {

    private MessageUtil() {
    }

    public static void sendMessage(@NotNull CommandSender sender, @Nullable String message) {
        sender.sendMessage(message == null ? StringUtils.EMPTY : message);
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull String languageProviderKey, @Nullable String... placeholders) {
        sendMessage(sender, MineMillionPlugin.getInstance().getLanguageProvider().get(languageProviderKey, placeholders));
    }
}
