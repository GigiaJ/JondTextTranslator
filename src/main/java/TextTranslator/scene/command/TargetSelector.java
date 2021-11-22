package TextTranslator.scene.command;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * A class to store information relevant to Jond's target selection within minecraft commands
 * @author Jaggar
 */
@Slf4j
@Data
public class TargetSelector
{
    /**
     * The dialogue tag for this object
     */
    private String dialogueTag;
    /**
     * The minimum trigger tag required for this dialogue to execute
     */
    private int dialogueTriggerMin;
    /**
     * The trigger score for this specific line of dialogue.
     */
    private int dialogueTrigger;
    /**
     * The talk time (the time in the dialogue trigger (character scene)) in which this dialogue actually executes.
     * Useful for determining the dialogues position in the scene.
     */
    private int talkTime;
    /**
     * The minimum talk time for this dialogue to trigger
     */
    private int talkTimeMin;

    /**
     * Generates a new target selector object with all the fields in this class as parameters.
     *
     * @param dialogueTag     the dialogue tag value
     * @param dialogueTrigger    the dialogue trigger score
     * @param dialogueTriggerMin  the minimum dialogue trigger score for this command to execute
     * @param talkTimeMin the minimum talk time for this command to execute at
     * @param talkTime        the talk time for this command
     *
     */
    public TargetSelector(String dialogueTag, int dialogueTrigger, int dialogueTriggerMin, int talkTimeMin, int talkTime) {
        this.dialogueTag = dialogueTag;
        this.dialogueTrigger = dialogueTrigger;
        this.dialogueTriggerMin = dialogueTriggerMin;
        this.talkTime = talkTime;
        this.talkTimeMin = talkTimeMin;
    }
}
