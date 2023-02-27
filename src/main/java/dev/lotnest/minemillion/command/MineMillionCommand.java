package dev.lotnest.minemillion.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.Language;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@CommandAlias("minemillion|mm|million")
public class MineMillionCommand extends BaseCommand {

    private final MineMillionPlugin plugin;

    @Default
    @HelpCommand
    @Description("%command.help.description")
    @Syntax("%command.help.syntax")
    public void onHelpSubCommand(@NotNull CommandSender sender, @NotNull CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("language")
    @Description("%command.language.description")
    @Syntax("%command.language.syntax")
    @CommandPermission("minemillion.command.language")
    @CommandCompletion("@languages")
    public void onLanguageSubCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + plugin.getLanguageProvider().get("command.invalidUsage"));
            return;
        }

        String languageEnumName = args[0].toUpperCase();

        if (!EnumUtils.isValidEnum(Language.class, languageEnumName)) {
            sender.sendMessage(ChatColor.RED + plugin.getLanguageProvider().get("general.languageNotFound", languageEnumName));
            return;
        }

        Language language = Language.valueOf(languageEnumName);
        plugin.getLanguageProvider().setLanguage(language);
        sender.sendMessage(ChatColor.GREEN + plugin.getLanguageProvider().get("general.languageChanged", language.getNativeName()));
    }
}
