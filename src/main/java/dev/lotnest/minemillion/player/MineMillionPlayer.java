package dev.lotnest.minemillion.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class MineMillionPlayer {

    private final @NotNull UUID uuid;
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

    public long getLifelinesUsed() {
        return lifelineFiftyFiftyUsed + lifelinePhoneAFriendUsed + lifelineAskTheAudienceUsed + lifelineDoubleDipUsed + lifelineSwitchTheQuestionUsed;
    }
}
