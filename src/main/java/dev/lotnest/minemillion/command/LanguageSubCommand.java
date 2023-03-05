package dev.lotnest.minemillion.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import dev.lotnest.minemillion.language.Language;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@CommandAlias("minemillion|mm|million")
public class LanguageSubCommand extends BaseCommand {

    private final @NotNull LanguageProvider languageProvider;

    @Subcommand("language")
    @Description("%command.language.description")
    @Syntax("%command.language.syntax")
    @CommandPermission("minemillion.command.language")
    @CommandCompletion("@languages")
    public void handleCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length != 1) {
            MessageUtil.sendInvalidUsageMessage(sender);
            return;
        }

        String languageEnumName = args[0].toUpperCase();

        if (!EnumUtils.isValidEnum(Language.class, languageEnumName)) {
            sender.sendMessage(ChatColor.RED + languageProvider.get("general.languageNotFound", languageEnumName));
            return;
        }

        Language language = Language.valueOf(languageEnumName);
        if (language == languageProvider.getLanguage()) {
            sender.sendMessage(ChatColor.RED + languageProvider.get("general.languageAlreadySet", language.getNativeName()));
            return;
        }

        languageProvider.setLanguage(language);
        sender.sendMessage(ChatColor.GREEN + languageProvider.get("general.languageChanged", language.getNativeName()));
    }
}
