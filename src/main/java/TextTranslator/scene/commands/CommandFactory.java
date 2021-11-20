package TextTranslator.scene.commands;

import java.util.Arrays;

/**
 * A factory class to create commands
 */
public class CommandFactory {
    /**
     * Instantiates a command object based on the command type passed and provides that object with the
     * its necessary values
     *
     * @param type
     * @param command
     * @param args
     * @return
     */
    public static Command create(CommandType type, Command command, String... args) {
        return switch (type) {
            case TELLRAW -> new TellRaw(command, args[1], args[2], args[3]);
            case SCOREBOARD -> new Scoreboard(command, args[4], args[5], args[6]);
            default -> new Generic(command, type, args[0]);
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
