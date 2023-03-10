package dev.lotnest.minemillion.util;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PermissionUtil {

    private PermissionUtil() {
    }

    public static boolean hasPermission(@NotNull CommandSender sender, @NotNull String permission) {
        return sender.hasPermission(permission);
    }

    public static boolean hasPermissionToSeeCorrectAnswers(@NotNull CommandSender sender) {
        return hasPermission(sender, "minemillion.question.seeCorrectAnswers");
    }
}
