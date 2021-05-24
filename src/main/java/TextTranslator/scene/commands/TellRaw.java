package TextTranslator.scene.commands;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * A class representing a minecraft command
 */
@Slf4j
public class TellRaw extends Command {

    /**
     * The speaker of the text of dialogue
     */
    @NonNull
    @Setter
    @Getter
    String speaker;
    /**
     * The text in this line of dialogue
     */
    @Setter
    @Getter
    String text;
    /**
     * The color of the speaker tag in this line of dialogue
     */
    @Setter
    @Getter
    String color;

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
        this.speaker = speaker;
        this.text = text;
        this.color = color;
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
        this.speaker = speaker;
        this.text = text;
        this.color = color;
    }
}
