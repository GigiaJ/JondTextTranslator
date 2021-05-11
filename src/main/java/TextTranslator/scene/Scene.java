package TextTranslator.scene;

import TextTranslator.scene.character.Dialogue;

import java.util.ArrayList;

import static TextTranslator.utils.Library.ExtraInfo;

public interface Scene {

    /**
     * Returns all the text in this objects list in order of appearance in the excel sheet
     *
     * @return the text in order of appearance in the commands excel sheet
     */
    ArrayList<String> getText();

    /**
     * Gets the row for the first dialogue at in this character scene object
     *
     * @return Gets the row for the first dialogue in this character scene object
     */
    default int getRow() {
        return getRow(0);
    }

    /**
     * Gets the excel row for the dialogue at the index of this character scene object
     *
     * @param index The index of the dialogue
     * @return The row of the dialogue at the given index
     */
    int getRow(int index);

    /**
     * Gets the trigger tag for this scene. All dialogues in a scene contain the same trigger tag so
     * simply grabbing the first entry in the list is fine.
     *
     * @return The trigger tag for this scene
     */
    default int getTrigger() {
        return getTrigger(0);
    }

    /**
     * Gets the trigger tag for the scene based on the dialogue at the given index of the scene. All trigger tags
     * should be identical.
     *
     * @param index The index in this character scene associated with a dialogue
     * @return The trigger tag for this scene
     */
    int getTrigger(int index);

    /**
     * Checks for a matching entry in a second array based on the contents of a dialogue passed
     *
     * @param toCheck a dialogue to check for in the second array
     * @param list    the second array of dialogues
     * @return true if the list contains the dialogue otherwise false
     */
    @ExtraInfo(UnitTested = true)
    default boolean checkContains(Dialogue toCheck, ArrayList<Dialogue> list) {
        for (Dialogue toCheckAgainst : list) {
            if (toCheckAgainst.getText().equals(toCheck.getText())
                    && toCheckAgainst.getRow() == toCheck.getRow()
                    && toCheckAgainst.getSpeaker().equals(toCheck.getSpeaker())
                    && toCheckAgainst.getTrigger() == toCheck.getTrigger()) {
                return true;
            }
        }
        return false;
    }

}
