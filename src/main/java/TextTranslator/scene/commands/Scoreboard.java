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
     * Makes a new scoreboard object with the values of the parent class as its own
     *
     * @param command              the object that this one will get its values from
     * @param targetScoreboardTeam the targeted team on the scoreboard
     * @param action               the action to invoke on this scoreboard team
     * @param subAction            the sub action to invoke for this action on the scoreboard team
     */
    public Scoreboard(Command command, String targetScoreboardTeam, String action, String subAction) {
        super(command.getDialogueTag(), command.getTriggerScore(),
                command.getMinimumTrigger(), command.getMinimumTalkTime(), command.getTalkTime(),
                command.getRow(), command.getOriginalLine(), CommandType.SCOREBOARD);
        this.targetScoreboardTeam = targetScoreboardTeam;
        this.action = action;
        this.subAction = subAction;
    }

    /**
     * Generates a new command object with all of the fields in this class as parameters.
     *
     * @param dialogueTag          the dialogue tag value
     * @param triggerScore         the dialogue trigger score
     * @param minimumTrigger       the minimum dialogue trigger score for this command to execute
     * @param minimumTalkTime      the minimum talk time for this command to execute at
     * @param talkTime             the talk time for this command
     * @param row                  the row from the source of this command
     * @param originalLine         the original text for this command from the source
     * @param targetScoreboardTeam the targeted team on the scoreboard
     * @param action               the action to invoke on this scoreboard team
     * @param subAction            the sub action to invoke for this action on the scoreboard team
     */
    public Scoreboard(String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row, String originalLine, String targetScoreboardTeam, String action, String subAction) {
        super(dialogueTag, triggerScore, minimumTrigger, minimumTalkTime, talkTime, row, originalLine, CommandType.SCOREBOARD);
        this.targetScoreboardTeam = targetScoreboardTeam;
        this.action = action;
        this.subAction = subAction;
    }
}
