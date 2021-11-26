package TextTranslator.scene.command;

import TextTranslator.scene.character.CharacterScene;
import TextTranslator.scene.character.CharacterSceneMatch;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class designed to sort and separate commands into CommandTriggerSet; see CommandTriggerSet for criteria involved
 * for splitting a set of commands.
 *
 * @author Jaggar
 */
@Slf4j
public class CommandSorter {

    /**
     * Splits the commands passed through into groupings based on their dialogue trigger values but
     * determined by the scene divider
     *
     * @param commands the list of commands loaded into the program
     * @return a list of grouped up commands
     */
    @ExtraInfo(UnitTested = false)
    public static ArrayList<ArrayList<Command>> group(ArrayList<Command> commands) {
        ArrayList<ArrayList<Command>> groups = new ArrayList<>();
        ArrayList<Command> group = new ArrayList<>();
        for (Command command : commands) {
            if (command.getType() != CommandType.SCENE_DIVIDER) {
                group.add(command);
            } else {
                group.add(command);
                groups.add(group);
                group = new ArrayList<>();
            }
        }
        groups.add(group);
        return groups;
    }

    /**
     * Returns a single CommandTriggerSet by splitting the commands within the group into two parts:
     * one part being the tellraw commands and the other part being the remaining.
     *
     * @param group the group to split into two parts
     * @return the split group as a CommandTriggerSet
     */
    @ExtraInfo(UnitTested = false)
    public static CommandTriggerSet split(ArrayList<Command> group) {
        ArrayList<Command> commands = new ArrayList<>();
        CharacterScene tellRaws = new CharacterScene();
        for (Command command : group) {
            if (command instanceof TellRaw) {
                tellRaws.add((TellRaw) command);
            }
            commands.add(command);
        }
        return new CommandTriggerSet(commands, new CharacterSceneMatch(tellRaws));
    }
}
