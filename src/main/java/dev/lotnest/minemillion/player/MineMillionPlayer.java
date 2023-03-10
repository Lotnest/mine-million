package dev.lotnest.minemillion.player;

import dev.lotnest.minemillion.question.Question;
import dev.lotnest.minemillion.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class MineMillionPlayer {

    private UUID uuid;
    private long firstPlayedMillis;
    private long lastPlayedMillis;
    private long gamesPlayed;
    private long gamesWon;
    private long gamesLost;
    private long correctAnswers;
    private long wrongAnswers;
    private long cash;
    private long cashWon;
    private long highestCashWon;
    private long lifelineFiftyFiftyUsed;
    private long lifelinePhoneAFriendUsed;
    private long lifelineAskTheAudienceUsed;
    private long lifelineDoubleDipUsed;
    private long lifelineSwitchTheQuestionUsed;

    private @Nullable Question currentQuestion;
    private @Nullable Question lastAskedQuestion;

    public long getLifelinesUsed() {
        return lifelineFiftyFiftyUsed + lifelinePhoneAFriendUsed + lifelineAskTheAudienceUsed + lifelineDoubleDipUsed + lifelineSwitchTheQuestionUsed;
    }

    public void sendMessage(@Nullable String message) {
        MessageUtil.sendMessage(Bukkit.getPlayer(uuid), message);
    }
}
