package TextTranslator.scene.command.scoreboard;


import TextTranslator.scene.command.Command;
import TextTranslator.scene.command.CommandType;
import TextTranslator.scene.command.TargetSelector;

/**
 * A class representing a minecraft command
 */
public class Scoreboard extends Command {

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
        this.args = new String[]{operation, action, postTargetSelector};
    }

    /**
     * Converts the command to its minecraft command text based form
     *
     * @return the text based command form of this command
     */
    @Override
    public String toCommandForm() {
        return ScoreboardOutputBuilder.getInstance().generateCommandStart(this.getMainTargetSelector(), this.getScoreboardCommand(), this.getScoreboardAction())
                + this.getPostTargetSelector();
    }

    /**
     * Retrieves the scoreboard command for this object from within the generic argument variable
     *
     * @return the text associated with this command
     */
    public String getScoreboardCommand() {
        return args[0];
    }

    /**
     * Sets the scoreboard command for this object by adjusting the generic args variable
     */
    public void setScoreboardCommand(String s) {
        args[0] = s;
    }

    /**
     * Retrieves the action for this object's scoreboard operation from within the generic argument variable
     *
     * @return the text associated with this command
     */
    public String getScoreboardAction() {
        return args[1];
    }

    /**
     * Sets the action of the object's scoreboard operation by adjusting the generic args variable
     */
    public void setScoreboardAction(String s) {
        args[1] = s;
    }

    /**
     * Retrieves the string data that follows the target selector
     *
     * @return the string following the target selector denoted by @LETTER[info here]
     */
    public String getPostTargetSelector() {
        return this.args[2];
    }

    /**
     * Sets the string data following the target selector
     */
    public void setPostTargetSelector(String s) {
        args[2] = s;
    }


}
