package dev.lotnest.minemillion.event.listener;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.question.Question;
import dev.lotnest.minemillion.util.ColorConstants;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.jooq.tools.StringUtils;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final @NotNull MineMillionPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(@NotNull AsyncPlayerChatEvent event) {
        plugin.getPlayerCache()
                .get(event.getPlayer().getUniqueId())
                .join()
                .ifPresent(mineMillionPlayer -> {
                    Question currentQuestion = mineMillionPlayer.getCurrentQuestion();
                    if (currentQuestion != null) {
                        event.setCancelled(true);

                        String playerAnswer = event.getMessage();
                        if (!playerAnswer.matches("[a-dA-D]")) {
                            mineMillionPlayer.sendMessage(ColorConstants.RED + "Your answer must be A, B, C or D (case insensitive).");
                            return;
                        }

                        String matchingAnswerFromLetter = currentQuestion.getAnswerFromLetter(playerAnswer).orElse(StringUtils.EMPTY);
                        if (currentQuestion.getAnswer().equalsIgnoreCase(matchingAnswerFromLetter)) {
                            mineMillionPlayer.sendMessage(ColorConstants.GREEN + "Correct answer!");
                        } else {
                            mineMillionPlayer.sendMessage(ColorConstants.RED + "Wrong answer!");
                        }

                        mineMillionPlayer.setLastAskedQuestion(mineMillionPlayer.getCurrentQuestion());
                        mineMillionPlayer.setCurrentQuestion(null);
                    }
                });
    }
}
