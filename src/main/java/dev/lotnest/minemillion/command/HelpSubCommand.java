package dev.lotnest.minemillion.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Syntax;
import org.jetbrains.annotations.NotNull;

@CommandAlias("minemillion|mm|million")
public class HelpSubCommand extends BaseCommand {

    @Default
    @HelpCommand
    @Description("%command.help.description")
    @Syntax("%command.help.syntax")
    public void handleCommand(@NotNull CommandHelp help) {
        help.showHelp();
    }
}
