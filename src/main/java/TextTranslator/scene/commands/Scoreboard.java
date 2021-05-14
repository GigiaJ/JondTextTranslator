package TextTranslator.scene.commands;

import lombok.Getter;
import lombok.Setter;

/**
 * A class representing a minecraft command
 */
public class Scoreboard extends Command {

    /**
     * The scoreboard team to target with this command
     */
    @Setter
    @Getter
    String targetScoreboardTeam;

    /**
     * The action of this command
     */
    @Setter
    @Getter
    String action;

    /**
     * The action to take after the initial action is chosen. This can be assigning values or performing an actual
     * sub action
     */
    @Setter
    @Getter
    String subAction;

    /**
     * Generates a new command object with all of the fields in this class as parameters.
     *
     * @param dialogueTag     the dialogue tag value
     * @param triggerScore    the dialogue trigger score
     * @param minimumTrigger  the minimum dialogue trigger score for this command to execute
     * @param minimumTalkTime the minimum talk time for this command to execute at
     * @param talkTime        the talk time for this command
     * @param row             the row from the source of this command
     * @param originalLine    the original text for this command from the source
     */
    public Scoreboard(String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row, String originalLine, String targetScoreboardTeam, String action, String subAction) {
        super(dialogueTag, triggerScore, minimumTrigger, minimumTalkTime, talkTime, row, originalLine, CommandType.SCOREBOARD);
        this.targetScoreboardTeam = targetScoreboardTeam;
        this.action = action;
        this.subAction = subAction;
    }
}
