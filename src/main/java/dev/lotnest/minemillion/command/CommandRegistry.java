package dev.lotnest.minemillion.command;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.CommandHelpFormatter;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.PaperCommandManager;
import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.language.Language;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.util.ColorConstants;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jooq.tools.StringUtils;

public class CommandRegistry {

    private final @NotNull MineMillionPlugin plugin;
    private final @NotNull LanguageProvider languageProvider;
    private PaperCommandManager commandManager;

    public CommandRegistry(@NotNull MineMillionPlugin plugin) {
        this.plugin = plugin;
        this.languageProvider = plugin.getLanguageProvider();
        registerCommands();
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(plugin);

        commandManager.enableUnstableAPI("brigadier");
        commandManager.enableUnstableAPI("help");

        commandManager.setHelpFormatter(new CommandHelpFormatter(commandManager) {
            @Override
            public void printHelpHeader(@NotNull CommandHelp help, @NotNull CommandIssuer issuer) {
                issuer.sendMessage(StringUtils.EMPTY);
                issuer.sendMessage(ColorConstants.BLUE_STRING + "=== " + ColorConstants.GOLD_STRING + languageProvider.get("general.help.header") + ColorConstants.BLUE_STRING + " ===");
                issuer.sendMessage(StringUtils.EMPTY);
            }

            @Override
            public void printHelpFooter(CommandHelp help, CommandIssuer issuer) {
                issuer.sendMessage(StringUtils.EMPTY);
                issuer.sendMessage(ColorConstants.BLUE_STRING + languageProvider.get("general.help.footer", ColorConstants.GOLD_STRING + help.getPage() + ColorConstants.BLUE_STRING,
                        ColorConstants.GOLD_STRING + help.getTotalPages() + ColorConstants.BLUE_STRING,
                        ColorConstants.GREEN_STRING + help.getTotalResults() + ColorConstants.BLUE_STRING
                ));
            }
        });

        commandManager.setDefaultExceptionHandler((command, registeredCommand, sender, args, throwable) -> {
            sender.sendMessage(ChatColor.RED + languageProvider.get("error.command"));
            return true;
        });

        commandManager.registerCommand(new HelpSubCommand());
        commandManager.registerCommand(new LanguageSubCommand(plugin.getLanguageProvider()));
        commandManager.registerCommand(new QuestionSubCommand(plugin.getQuestionManager(), languageProvider, plugin.getPlayerCache()));

        reloadCommandReplacements();

        commandManager.getCommandCompletions().registerAsyncCompletion("languages", context -> Language.getLanguagesAsStrings());
    }

    public void reloadCommandReplacements() {
        commandManager.getCommandReplacements().addReplacement("command.help.description", languageProvider.get("command.help.description"));
        commandManager.getCommandReplacements().addReplacement("command.help.syntax", languageProvider.get("command.help.syntax"));

        commandManager.getCommandReplacements().addReplacement("command.language.description", languageProvider.get("command.language.description"));
        commandManager.getCommandReplacements().addReplacement("command.language.syntax", languageProvider.get("command.language.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.language.get.description", languageProvider.get("command.language.get.description"));
        commandManager.getCommandReplacements().addReplacement("command.language.get.syntax", languageProvider.get("command.language.get.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.language.set.description", languageProvider.get("command.language.set.description"));
        commandManager.getCommandReplacements().addReplacement("command.language.set.syntax", languageProvider.get("command.language.set.syntax"));

        commandManager.getCommandReplacements().addReplacement("command.question.description", languageProvider.get("command.question.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.syntax", languageProvider.get("command.question.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.add.description", languageProvider.get("command.question.add.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.add.syntax", languageProvider.get("command.question.add.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.remove.description", languageProvider.get("command.question.remove.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.remove.syntax", languageProvider.get("command.question.remove.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.edit.description", languageProvider.get("command.question.edit.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.edit.syntax", languageProvider.get("command.question.edit.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.list.description", languageProvider.get("command.question.list.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.list.syntax", languageProvider.get("command.question.list.syntax"));
        commandManager.getCommandReplacements().addReplacement("command.question.reload.description", languageProvider.get("command.question.reload.description"));
        commandManager.getCommandReplacements().addReplacement("command.question.reload.syntax", languageProvider.get("command.question.reload.syntax"));
    }
}
