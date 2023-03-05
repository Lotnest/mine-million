package dev.lotnest.minemillion.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.google.common.collect.Lists;
import dev.lotnest.minemillion.gui.GUI;
import dev.lotnest.minemillion.gui.Item;
import dev.lotnest.minemillion.gui.Page;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.question.Question;
import dev.lotnest.minemillion.question.QuestionManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CommandAlias("minemillion|mm|million")
public class QuestionSubCommand extends BaseCommand {

    private final @NotNull QuestionManager questionManager;
    private final @NotNull LanguageProvider languageProvider;

    @Subcommand("question")
    @Description("%command.question.description")
    @Syntax("%command.question.syntax")
    public void handleCommand(@NotNull CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("question add")
    @Description("%command.question.add.description")
    @Syntax("%command.question.add.syntax")
    public void handleAddCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        sender.sendMessage("add");
    }

    @Subcommand("question remove")
    @Description("%command.question.remove.description")
    @Syntax("%command.question.remove.syntax")
    public void handleRemoveCommand(@NotNull CommandSender sender, @Optional Long questionId) {
        if (questionId == null) {
            sendQuestionIqNotProvidedMessage(sender);
            return;
        }

        boolean isQuestionRemoved = questionManager.questionProvider().removeQuestion(questionId);
        sender.sendMessage(isQuestionRemoved ? ChatColor.GREEN + languageProvider.get("command.question.remove.questionRemoved", questionId.toString()) :
                ChatColor.RED + languageProvider.get("command.question.remove.questionNotFound", questionId.toString()));
    }

    @Subcommand("question edit")
    @Description("%command.question.edit.description")
    @Syntax("%command.question.edit.syntax")
    public void handleEditCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        sender.sendMessage("edit");
    }

    @Subcommand("question list")
    @Description("%command.question.list.description")
    @Syntax("%command.question.list.syntax")
    public void handleListCommand(@NotNull CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + languageProvider.get("command.onlyForPlayers"));
            return;
        }

        List<Question> questions = questionManager.questionProvider().getQuestions();
        if (questions.isEmpty()) {
            sender.sendMessage(ChatColor.RED + languageProvider.get("command.question.list.noQuestionsAvailable"));
            return;
        }

        GUI gui = GUI.create(Lists.newArrayList(
                new Page(languageProvider.get("gui.questions.title"), questions.stream()
                        .map(Item::questionToItem)
                        .collect(Collectors.toList())))
        );
        gui.open((Player) sender);
    }

    @Subcommand("question reload")
    @Description("%command.question.reload.description")
    @Syntax("%command.question.reload.syntax")
    public void handleReloadCommand(@NotNull CommandSender sender) {
        boolean areQuestionsReloaded = questionManager.questionProvider().reload();
        sender.sendMessage(areQuestionsReloaded ? ChatColor.GREEN + languageProvider.get("command.question.reload.questionsReloaded") :
                ChatColor.RED + languageProvider.get("command.question.reload.failedToReloadQuestions"));
    }

    private void sendQuestionIqNotProvidedMessage(@NotNull CommandSender sender) {
        sender.sendMessage(ChatColor.RED + languageProvider.get("command.question.questionIdNotProvided"));
    }
}
