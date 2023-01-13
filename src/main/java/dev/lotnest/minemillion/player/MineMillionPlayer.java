package dev.lotnest.minemillion.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Builder
@Getter
@Setter
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
