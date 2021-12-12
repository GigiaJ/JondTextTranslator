package TextTranslator.scene.command;

import TextTranslator.scene.command.generic.Generic;
import TextTranslator.scene.command.scoreboard.Scoreboard;
import TextTranslator.scene.command.dialogue.TellRaw;
import TextTranslator.scene.command.dialogue.TellRawText;

import java.util.Arrays;

/**
 * A factory class to create commands
 */
public class CommandFactory {
    /**
     * Instantiates a command object based on the command type passed and provides that object with the
     * its necessary values
     *
     * @param type              The sub-type of command being created
     * @param row               The row of the given command
     * @param targetSelector    The target selector for the command
     * @param args              The unique args for each command
     * @return A command re-instantiated to fit the functionality of a main type
     */
    public static Command create(CommandType type, int row, TargetSelector targetSelector, String... args) {
        return switch (type) {
            case TELLRAW -> new TellRaw(targetSelector, row, new TellRawText(args[1], args[2], args[3]));
            case SCOREBOARD -> new Scoreboard(targetSelector, row,  args[0], args[4], args[5], args[6]);
            default -> new Generic(targetSelector, row, type, args[0], args[6]);
        };
    }

    /**
     * Identifies the type of a command using the line passed to the method by comparing the line start with
     * the string values associated to command types
     *
     * @param line The line of the file to check for commands
     * @return The type of the command associated with this line
     */
    public static CommandType identifyCommandType(String line) {
        return Arrays.stream(CommandType.values()).dropWhile(commandType -> (!line.startsWith(commandType.getCommandWord()))).toArray(CommandType[]::new)[0];
    }
}
