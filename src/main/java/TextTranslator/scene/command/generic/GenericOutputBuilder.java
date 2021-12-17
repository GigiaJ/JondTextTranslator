package TextTranslator.scene.command.generic;

import TextTranslator.scene.command.CommandOutputBuilder;
import TextTranslator.scene.command.CommandType;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.TargetSelectorType;

import static TextTranslator.utils.Library.ExtraInfo;

public class GenericOutputBuilder extends CommandOutputBuilder {

    /**
     * The singleton of this object
     */
    static GenericOutputBuilder instance = new GenericOutputBuilder();

    GenericOutputBuilder(){}

    /**
     * Returns this instance of the generic output builder
     *
     * @return      the singleton
     */
    public static GenericOutputBuilder getInstance() {
        return instance;
    }

    /**
     * Generates the start of the generic command
     *
     * @param targetSelector    the target selector for this command
     * @return the start of the generic command
     */
    @ExtraInfo(UnitTested = false)
    public String generateCommandStart(TargetSelector targetSelector, CommandType type) {
        return type.getCommandWord() + " " + TargetSelectorType.ALL_PLAYERS.getValue() + CommandOutputBuilder.generateTagPortion(targetSelector);
    }
}
