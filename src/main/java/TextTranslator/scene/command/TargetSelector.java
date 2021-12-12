package TextTranslator.scene.command;

/**
 * A class to store information relevant to Jond's target selection within minecraft commands
 * @author Jaggar
 *
 * Generates a new target selector object with all the fields in this class as parameters.
 *
 * @param dialogueTag           the dialogue tag value
 * @param dialogueTrigger       the dialogue trigger score
 * @param dialogueTriggerMin    the minimum dialogue trigger score for this command to execute
 * @param talkTimeMin           the minimum talk time for this command to execute at
 * @param talkTime              the talk time for this command
 * @param starterPickMin A value containing the starter selection made by a player
 * @param starterPick    A value containing the starter selection made by a player
 *
 */
public record TargetSelector(String dialogueTag, int dialogueTrigger, int dialogueTriggerMin, int talkTimeMin, int talkTime, int starterPickMin, int starterPick){
    public TargetSelector(String dialogueTag, int dialogueTrigger, int dialogueTriggerMin, int talkTimeMin, int talkTime) {
        this(dialogueTag, dialogueTrigger, dialogueTriggerMin, talkTimeMin, talkTime,-1, -1);
    }
}
