package TextTranslator.scene.command.generic;

import TextTranslator.scene.command.Command;
import TextTranslator.scene.command.CommandType;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.scoreboard.ScoreboardOutputBuilder;

/**
 * A class representing a command with generic functionality
 */
public class Generic extends Command {

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
        this.args = new String[]{postTargetSelector};
    }

    /**
     * Converts the command to its minecraft command text based form
     *
     * @return the text based command form of this command
     */
    @Override
    public String toCommandForm() {
        switch(getType()) {
            case COMMENT, SCENE_DIVIDER -> {
                return this.getOriginalLine();
            }
            case BLANK -> {
                return "";
            }
            default -> {
                return GenericOutputBuilder.getInstance().generateCommandStart(this.getMainTargetSelector(), this.getType())
                        + " "  + this.getPostTargetSelector();
            }
        }

    }

    /**
     * Retrieves the string data that follows the target selector
     *
     * @return the string following the target selector denoted by @a[info here]
     */
    public String getPostTargetSelector() {
        return this.args[0];
    }

    /**
     * Sets the string data following the target selector
     */
    public void setPostTargetSelector(String s) {
        args[0] = s;
    }


}
