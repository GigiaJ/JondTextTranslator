package TextTranslator.scene.command.generic;

import TextTranslator.scene.command.Command;
import TextTranslator.scene.command.CommandType;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.dialogue.LanguageData;
import TextTranslator.scene.command.dialogue.TellrawOutputBuilder;
import TextTranslator.utils.Language;
import lombok.Getter;

/**
 * A class representing a command with generic functionality
 */
public class Generic extends Command {

    @Getter
    private final String postTargetSelector;

    /**
     * Generates a new dialogue object with all of the fields in this class as parameters.
     *
     * @param mainTargetSelector the main target selector for this command
     * @param row                the row from the source of this command
     * @param originalLine       the original text for this command from the source
     * @param type               the type of this command
     * @param postTargetSelector the string data following the target selector
     */
    public Generic(TargetSelector mainTargetSelector, int row, CommandType type, String originalLine,  String postTargetSelector) {
        super(mainTargetSelector, row, originalLine, type);
        this.postTargetSelector = postTargetSelector;
    }

    /**
     * Converts the command to its minecraft command text based form
     *
     * @return the text based command form of this command
     */
    @Override
    public String toCommandForm(Language language) {
        switch(getType()) {
            case COMMENT, SCENE_DIVIDER -> {
                return this.getOriginalLine();
            }
            case BLANK -> {
                return "";
            }
            default -> {
                return GenericOutputBuilder.getInstance().generateCommandStart(
                        new TargetSelector(this.getMainTargetSelector().dialogueTag(),
                                this.getMainTargetSelector().dialogueTrigger(),
                                this.getMainTargetSelector().dialogueTriggerMin(),
                                this.getLanguageData().getTalkTime(language),
                                this.getLanguageData().getTalkTime(language)),
                        this.getType())
                        + " "  + this.getPostTargetSelector();
            }
        }

    }
}
