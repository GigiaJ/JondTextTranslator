package TextTranslator.scene.commands;

/**
 * A factory class to create commands
 */
public class CommandFactory {
    public static Command create(CommandType type, Command command, String... args) {
        switch (type) {
            case TELLRAW:
                return new TellRaw(command, args[0], args[1], args[2]);
            case SCOREBOARD:
                return new Scoreboard(command, args[0], args[1], args[2]);
            default:
                return new Generic(command, type, args[0]);
        }

    }
}
