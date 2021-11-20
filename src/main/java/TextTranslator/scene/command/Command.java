package TextTranslator.scene.command;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Command {
    /**
     * The dialogue tag for this object
     */
    String dialogueTag;
    /**
     * The minimum trigger tag required for this dialogue to execute
     */
    int minimumTrigger;
    /**
     * The trigger score for this specific line of dialogue.
     */
    int triggerScore;
    /**
     * The talk time (the time in the dialogue trigger (character scene)) in which this dialogue actually executes.
     * Useful for determining the dialogues position in the scene.
     */
    int talkTime;
    /**
     * The minimum talk time for this dialogue to trigger
     */
    int minimumTalkTime;
    /**
     * The row in the excel sheet that this line of dialogue is from.
     */
    int row;
    /**
     * The original line of this command without modification
     */
    String originalLine;
    /**
     * The type of command this object is
     */
    CommandType type;
    /**
     * The command type specific values
     */
    String[] args;

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
    public Command(String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row, String originalLine, CommandType type) {
        this.dialogueTag = dialogueTag;
        this.triggerScore = triggerScore;
        this.minimumTrigger = minimumTrigger;
        this.minimumTalkTime = minimumTalkTime;
        this.talkTime = talkTime;
        this.row = row;
        this.originalLine = originalLine;
        this.type = type;
    }

}
