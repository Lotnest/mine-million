package dev.lotnest.minemillion.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.google.common.collect.Lists;
import dev.lotnest.minemillion.gui.GUI;
import dev.lotnest.minemillion.gui.Item;
import dev.lotnest.minemillion.gui.Page;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.player.MineMillionPlayer;
import dev.lotnest.minemillion.player.MineMillionPlayerCache;
import dev.lotnest.minemillion.question.Question;
import dev.lotnest.minemillion.question.QuestionManager;
import dev.lotnest.minemillion.util.ColorConstants;
import dev.lotnest.minemillion.util.PermissionUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiredArgsConstructor
@CommandAlias("minemillion|mm|million")
public class QuestionSubCommand extends BaseCommand {

    private final @NotNull QuestionManager questionManager;
    private final @NotNull LanguageProvider languageProvider;
    private final @NotNull MineMillionPlayerCache playerCache;

    @Subcommand("question")
    @Description("%command.question.description")
    @Syntax("%command.question.syntax")
    @CommandPermission("minemillion.question")
    public void handleCommand(@NotNull CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("question add")
    @Description("%command.question.add.description")
    @Syntax("%command.question.add.syntax")
    @CommandPermission("minemillion.question.add")
    public void handleAddCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        sender.sendMessage("add");
    }

    @Subcommand("question remove")
    @Description("%command.question.remove.description")
    @Syntax("%command.question.remove.syntax")
    @CommandPermission("minemillion.question.remove")
    public void handleRemoveCommand(@NotNull CommandSender sender, @Optional Long questionId) {
        if (questionId == null) {
            sendQuestionIdNotProvidedMessage(sender);
            return;
        }

        boolean isQuestionRemoved = questionManager.questionProvider().removeQuestion(questionId);
        sender.sendMessage(isQuestionRemoved ? ColorConstants.GREEN + languageProvider.get("command.question.remove.questionRemoved", questionId.toString()) :
                ColorConstants.RED + languageProvider.get("command.question.remove.questionNotFound", questionId.toString()));
    }

    @Subcommand("question edit")
    @Description("%command.question.edit.description")
    @Syntax("%command.question.edit.syntax")
    @CommandPermission("minemillion.question.edit")
    public void handleEditCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        sender.sendMessage("edit");
    }

    @Subcommand("question list")
    @Description("%command.question.list.description")
    @Syntax("%command.question.list.syntax")
    @CommandPermission("minemillion.question.list")
    public void handleListCommand(@NotNull CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorConstants.RED + languageProvider.get("command.onlyForPlayers"));
            return;
        }

        List<Question> questions = questionManager.questionProvider().getQuestions();
        if (questions.isEmpty()) {
            sender.sendMessage(ColorConstants.RED + languageProvider.get("command.question.list.noQuestionsAvailable"));
            return;
        }

        GUI gui = GUI.create(Lists.newArrayList(
                new Page(languageProvider.get("gui.questions.title"), questions.stream()
                        .map(question -> Item.questionToItem(question, PermissionUtil.hasPermissionToSeeCorrectAnswers(sender)))
                        .toList()))
        );
        gui.open((Player) sender);
    }

    @Subcommand("question reload")
    @Description("%command.question.reload.description")
    @Syntax("%command.question.reload.syntax")
    @CommandPermission("minemillion.question.reload")
    public void handleReloadCommand(@NotNull CommandSender sender) {
        boolean areQuestionsReloaded = questionManager.questionProvider().reload();
        sender.sendMessage(areQuestionsReloaded ? ColorConstants.GREEN + languageProvider.get("command.question.reload.questionsReloaded") :
                ColorConstants.RED + languageProvider.get("command.question.reload.failedToReloadQuestions"));
    }

    @Subcommand("question test")
    @Description("")
    @Syntax("")
    @CommandPermission("minemillion.question.test")
    public void testCommand(@NotNull Player player) {
        questionManager.questionProvider().getQuestion().ifPresentOrElse(question -> {
            MineMillionPlayer mineMillionPlayer = playerCache.getOrCreate(player.getUniqueId()).join();
            mineMillionPlayer.setCurrentQuestion(question);
            playerCache.update(mineMillionPlayer);

            player.sendMessage(ColorConstants.RED + question.getText());
            player.sendMessage(question.getAnswersArray(false));
        }, () -> player.sendMessage(ColorConstants.RED + languageProvider.get("command.question.list.noQuestionsAvailable")));
    }

    private void sendQuestionIdNotProvidedMessage(@NotNull CommandSender sender) {
        sender.sendMessage(ColorConstants.RED + languageProvider.get("command.question.questionIdNotProvided"));
    }
}
