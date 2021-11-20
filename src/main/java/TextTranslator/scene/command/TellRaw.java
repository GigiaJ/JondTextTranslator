package TextTranslator.scene.command;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * A class representing a minecraft command
 */
@Slf4j
public class TellRaw extends Command {

    /**
     * Makes a new tellraw object with the values of the parent class as its own
     *
     * @param command the object that this one will get its values from
     * @param speaker the speaker for this line of dialogue
     * @param text    the actual text for this line of dialogue
     * @param color   the color of the speaker's name for this line of dialogue
     */
    public TellRaw(Command command, @NonNull String speaker, String text, String color) {
        super(command.getDialogueTag(), command.getTriggerScore(),
                command.getMinimumTrigger(), command.getMinimumTalkTime(), command.getTalkTime(),
                command.getRow(), command.getOriginalLine(), CommandType.TELLRAW);
        this.args = new String[]{speaker, text, color};
    }

    /**
     * Generates a new dialogue object with all of the fields in this class as parameters.
     *
     * @param speaker         the speaker for this line of dialogue
     * @param text            the actual text for this line of dialogue
     * @param color           the color of the speaker's name for this line of dialogue
     * @param dialogueTag     the dialogue tag value
     * @param triggerScore    the dialogue trigger score
     * @param minimumTrigger  the minimum dialogue trigger score for this command to execute
     * @param minimumTalkTime the minimum talk time for this command to execute at
     * @param talkTime        the talk time for this command
     * @param row             the row from the source of this command
     * @param originalLine    the original text for this command from the source
     */
    public TellRaw(@NonNull String speaker, String text, String color, String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row, String originalLine) {
        super(dialogueTag, triggerScore, minimumTrigger, minimumTalkTime, talkTime, row, originalLine, CommandType.TELLRAW);
        this.args = new String[]{speaker, text, color};
    }

    /**
     * Generates a new dialogue object with most of the fields in this class as parameters.
     *
     * @param speaker         the speaker for this line of dialogue
     * @param text            the actual text for this line of dialogue
     * @param color           the color of the speaker's name for this line of dialogue
     * @param dialogueTag     the dialogue tag value
     * @param triggerScore    the dialogue trigger score
     * @param minimumTrigger  the minimum dialogue trigger score for this command to execute
     * @param minimumTalkTime the minimum talk time for this command to execute at
     * @param talkTime        the talk time for this command
     * @param row             the row from the source of this command
     */
    public TellRaw(@NonNull String speaker, String text, String color, String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row) {
        super(dialogueTag, triggerScore, minimumTrigger, minimumTalkTime, talkTime, row, null, CommandType.TELLRAW);
        this.args = new String[]{speaker, text, color};
    }

    /**
     * Gets the speaker of the tellraw command by checking the generic args variable
     */
    public String getSpeaker() {
        return args[0];
    }

    /**
     * Sets the speaker of the tellraw command by adjusting the generic args variable
     */
    public void setSpeaker(String s) {
        this.args[0] = s;
    }

    /**
     * Gets the speaker of the tellraw command by checking the generic args variable
     */
    public String getText() {
        return args[1];
    }

    /**
     * Sets the text of the tellraw command by adjusting the generic args variable
     */
    public void setText(String s) {
        this.args[1] = s;
    }

    /**
     * Gets the speaker of the tellraw command by checking the generic args variable
     */
    public String getColor() {
        return args[2];
    }

    /**
     * Sets the color of the tellraw command by adjusting the generic args variable
     */
    public void setColor(String s) {
        this.args[2] = s;
    }
}
