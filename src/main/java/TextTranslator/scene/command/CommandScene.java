package TextTranslator.scene.command;

import TextTranslator.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static TextTranslator.utils.Library.ExtraInfo;

public class CommandScene extends ArrayList<Command> implements Scene {

    public CommandScene() {
    }

    public CommandScene(List<Command> list) {
        super(list);
    }

    /**
     * Returns all the text in this objects list in order of appearance in the excel sheet
     *
     * @return the text in order of appearance in the commands excel sheet
     */
    @Override
    @ExtraInfo(UnitTested = true)
    public ArrayList<String> getText() {
        ArrayList<String> text = new ArrayList<>();
        for (Command command : this) {
            text.add(command.toCommandForm());
        }
        return text;
    }

    /**
     * Gets the excel row for the dialogue at the index of this character scene object
     *
     * @param index The index of the dialogue
     * @return The row of the dialogue at the given index
     */
    @Override
    public int getRow(int index) {
        return this.get(index).getRow();
    }

    /**
     * Gets the trigger tag for the scene based on the dialogue at the given index of the scene. All trigger tags
     * should be identical.
     *
     * @param index The index in this character scene associated with a dialogue
     * @return The trigger tag for this scene
     */
    @Override
    public int getTrigger(int index) {
        return this.get(index).getTrigger();
    }
}
