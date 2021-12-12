package TextTranslator.scene.command.scoreboard;

import TextTranslator.scene.command.CommandOutputBuilder;
import TextTranslator.scene.command.CommandType;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.TargetSelectorType;

import static TextTranslator.utils.Library.ExtraInfo;

public class ScoreboardOutputBuilder extends CommandOutputBuilder {

    /**
     * The singleton of this object
     */
    static ScoreboardOutputBuilder instance = new ScoreboardOutputBuilder();

    ScoreboardOutputBuilder(){}

    /**
     * Returns this instance of the scoreboard output builder
     * @return      the singleton
     */
    public static ScoreboardOutputBuilder getInstance() {
        return instance;
    }

    /**
     * Generates the start of the scoreboard command
     * @param targetSelector    the target selector for this command
     * @return the start of the scoreboard command
     */
    @ExtraInfo(UnitTested = false)
    public String generateCommandStart(TargetSelector targetSelector, String scoreboardCommand, String scoreboardAction) {
        return CommandType.SCOREBOARD.getCommandWord() + " "  +
                scoreboardCommand + " " +
                scoreboardAction + " " +
                TargetSelectorType.ALL_PLAYERS.getValue() +
                CommandOutputBuilder.generateTagPortion(targetSelector);
    }


}
