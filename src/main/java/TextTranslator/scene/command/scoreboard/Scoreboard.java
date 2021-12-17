package TextTranslator.scene.command.scoreboard;


import TextTranslator.scene.command.Command;
import TextTranslator.scene.command.CommandType;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.dialogue.TellrawOutputBuilder;
import TextTranslator.utils.Language;
import lombok.Getter;

/**
 * A class representing a minecraft command
 */
public class Scoreboard extends Command {

    @Getter
    private final String operation, action, postTargetSelector;

    /**
     * Generates a new command object with all of the fields in this class as parameters.
     *
     * @param mainTargetSelector the main target selector for this command
     * @param row                the row from the source of this command
     * @param originalLine       the original text for this command from the source
     * @param operation          the scoreboard command being used
     * @param action             the action to invoke for this operation
     * @param postTargetSelector the string data following the target selector
     */
    public Scoreboard(TargetSelector mainTargetSelector, int row, String originalLine, String operation, String action, String postTargetSelector) {
        super(mainTargetSelector, row, originalLine, CommandType.SCOREBOARD);
        this.operation = operation;
        this.action = action;
        this.postTargetSelector = postTargetSelector;
    }

    /**
     * Converts the command to its minecraft command text based form
     *
     * @return the text based command form of this command
     */
    @Override
    public String toCommandForm(Language language) {
        return ScoreboardOutputBuilder.getInstance().generateCommandStart(
                new TargetSelector(this.getMainTargetSelector().dialogueTag(),
                        this.getMainTargetSelector().dialogueTrigger(),
                        this.getMainTargetSelector().dialogueTriggerMin(),
                        this.getLanguageData().getTalkTime(language), this.getLanguageData().getTalkTime(language)),
                this.getOperation(), this.getAction())
                + this.getPostTargetSelector();
    }
}
