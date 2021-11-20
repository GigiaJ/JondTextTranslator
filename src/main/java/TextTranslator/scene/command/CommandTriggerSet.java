package TextTranslator.scene.command;

import TextTranslator.scene.character.CharacterSceneMatch;
import lombok.Data;

import java.util.ArrayList;

/**
 * A class designed to store a grouping of commands based on their positioning within a mcfunction file
 * If the commands share a general position denoted by a separator and matching dialogue trigger values
 * then they are considered a part of a set and are to be stored within an object of this class
 *
 * @author Jaggar
 */
@Data
public class CommandTriggerSet {
    /**
     * The non-tellraw commands
     */
    ArrayList<Command> commands;
    /**
     * The tellraw commands as a character scene object
     */
    CharacterSceneMatch characterScene;

    CommandTriggerSet(ArrayList<Command> commands, CharacterSceneMatch characterScene) {
        this.commands = commands;
        this.characterScene = characterScene;
    }
}
