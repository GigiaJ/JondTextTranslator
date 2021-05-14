package TextTranslator.scene.commands;

import lombok.Getter;
import lombok.Setter;

/**
 * A class representing a command with generic functionality
 */
public class Generic extends Command {

    /**
     * The text that this command has.
     * Can be the text's actual action or just simply text
     */
    @Getter
    @Setter
    String text;

    /**
     * Generates a new dialogue object with all of the fields in this class as parameters.
     *
     * @param text            the actual text for this line of dialogue
     * @param dialogueTag     the dialogue tag value
     * @param triggerScore    the dialogue trigger score
     * @param minimumTrigger  the minimum dialogue trigger score for this command to execute
     * @param minimumTalkTime the minimum talk time for this command to execute at
     * @param talkTime        the talk time for this command
     * @param row             the row from the source of this command
     * @param originalLine    the original text for this command from the source
     * @param text            the text that this command has
     * @param type            the type of this command
     */
    public Generic(String dialogueTag, int triggerScore, int minimumTrigger, int minimumTalkTime, int talkTime, int row, String originalLine, String text, CommandType type) {
        super(dialogueTag, triggerScore, minimumTrigger, minimumTalkTime, talkTime, row, originalLine, type);
        this.text = text;
    }

}
