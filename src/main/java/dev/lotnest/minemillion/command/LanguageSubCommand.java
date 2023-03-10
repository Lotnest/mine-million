package dev.lotnest.minemillion.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import dev.lotnest.minemillion.language.Language;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.util.ColorConstants;
import dev.lotnest.minemillion.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@CommandAlias("minemillion|mm|million")
public class LanguageSubCommand extends BaseCommand {

    private final @NotNull LanguageProvider languageProvider;

    @Subcommand("language")
    @Description("%command.language.description")
    @Syntax("%command.language.syntax")
    @CommandPermission("minemillion.language")
    public void handleCommand(@NotNull CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("language get")
    @Description("%command.language.get.description")
    @Syntax("%command.language.get.syntax")
    @CommandPermission("minemillion.language.get")
    public void handleGetCommand(@NotNull CommandSender sender) {
        Language language = languageProvider.getLanguage();
        sender.sendMessage(ColorConstants.GREEN + languageProvider.get("general.currentLanguage", language.getNativeName()));
    }

    @Subcommand("language set")
    @Description("%command.language.set.description")
    @Syntax("%command.language.set.syntax")
    @CommandPermission("minemillion.language.set")
    @CommandCompletion("@languages")
    public void handleSetCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length != 1) {
            MessageUtil.sendInvalidUsageMessage(sender);
            return;
        }

        String languageEnumName = args[0].toUpperCase();

        if (!EnumUtils.isValidEnum(Language.class, languageEnumName)) {
            sender.sendMessage(ColorConstants.RED + languageProvider.get("general.languageNotFound", languageEnumName));
            return;
        }

        Language language = Language.valueOf(languageEnumName);
        if (language == languageProvider.getLanguage()) {
            sender.sendMessage(ColorConstants.RED + languageProvider.get("general.languageAlreadySet", language.getNativeName()));
            return;
        }

        languageProvider.setLanguage(language);
        sender.sendMessage(ColorConstants.GREEN + languageProvider.get("general.languageChanged", language.getNativeName()));
    }
}
