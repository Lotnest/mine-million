package dev.lotnest.minemillion.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class MineMillionPlayer {

    private final UUID playerUUID;
    private long firstPlayedMillis;
    private long lastPlayedMillis;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int correctAnswers;
    private int wrongAnswers;
    private int cashWon;
    private int bestCashWon;
    private int lifelineFiftyFiftyUsed;
    private int lifelinePhoneAFriendUsed;
    private int lifelineAskTheAudienceUsed;
    private int lifelineDoubleDipUsed;
    private int lifelineSwitchTheQuestionUsed;

    public int getLifelinesUsed() {
        return lifelineFiftyFiftyUsed + lifelinePhoneAFriendUsed + lifelineAskTheAudienceUsed + lifelineDoubleDipUsed + lifelineSwitchTheQuestionUsed;
    }
}
