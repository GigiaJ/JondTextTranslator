package TextTranslator.scene.command;

/**
 * A class representing a command with generic functionality
 */
public class Generic extends Command {

    /**
     * Makes a new generic object with the values of the parent class as its own
     *
     * @param command            the object that this one will get its values from
     * @param type               the type of this command
     * @param text               the text in this command
     * @param postTargetSelector the string data following the target selector
     */
    public Generic(Command command, CommandType type, String text, String postTargetSelector) {
        super(command.getDialogueTag(), command.getTriggerScore(),
                command.getMinimumTrigger(), command.getMinimumTalkTime(), command.getTalkTime(),
                command.getRow(), command.getOriginalLine(), type);
        this.args = new String[]{text, postTargetSelector};
    }

    /**
     * Generates a new dialogue object with all of the fields in this class as parameters.
     *
     * @param text               the actual text for this line of dialogue
     * @param dialogueTag        the dialogue tag value
     * @param triggerScore       the dialogue trigger score
     * @param minimumTrigger     the minimum dialogue trigger score for this command to execute
     * @param minimumTalkTime    the minimum talk time for this command to execute at
     * @param talkTime           the talk time for this command
     * @param row                the row from the source of this command
     * @param originalLine       the original text for this command from the source
     * @param text               the text that this command has
     * @param type               the type of this command
     * @param postTargetSelector the string data following the target selector
     */
    public Generic(String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row, String originalLine, String text, CommandType type, String postTargetSelector) {
        super(dialogueTag, triggerScore, minimumTrigger, minimumTalkTime, talkTime, row, originalLine, type);
        this.args = new String[]{text, postTargetSelector};
    }

    /**
     * Retrieves the text for this command from within the generic argument variable
     *
     * @return the text associated with this command
     */
    public String getText() {
        return this.args[0];
    }

    /**
     * Sets the text for the generic command
     */
    public void setText(String s) {
        args[0] = s;
    }

    /**
     * Retrieves the string data that follows the target selector
     *
     * @return the string following the target selector denoted by @a[info here]
     */
    public String getPostTargetSelector() {
        return this.args[1];
    }

    /**
     * Sets the string data following the target selector
     */
    public void setPostTargetSelector(String s) {
        args[1] = s;
    }

    /**
     * Outputs the command in the format utilized for that particular command type
     */
    public String outputCommand() {
        return null;
    }

}
