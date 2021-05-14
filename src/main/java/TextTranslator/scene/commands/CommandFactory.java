package TextTranslator.scene.commands;

/**
 * A factory class to create commands
 */
public class CommandFactory {

    public static Class<?> create(CommandType type) {
        switch (type) {
            case COMMENT:
                break;
            case TP:
                break;
            case TELLRAW:
                return CommandType.TELLRAW.getClazz();
            case SCOREBOARD:
                break;
            case SCENE_DIVIDER:
                break;
            case EXECUTE:
                break;
            case BLANK:
                break;
            case GIVE:
                break;
            case CLEAR:
                break;
        }

    }
}
