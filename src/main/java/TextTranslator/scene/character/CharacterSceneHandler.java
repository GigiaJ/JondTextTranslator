package TextTranslator.scene.character;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class to handle changes performed involving character scenes
 *
 * @author Jaggar
 */
@Slf4j
public class CharacterSceneHandler {
    /**
     * Places the commands from the excel sheet into a 2d string array populating the first value of the
     * inner array with the text for the row of the excel sheet and the last value of the inner array with
     * the actual row number as a string
     *
     * @param map    the 2d string array to populate
     * @param scenes the scenes containing dialogue extracted from the command excel sheet
     * @return the 2d string array containing the lines of text for the row matching
     * the index of the outer string array and the row number itself
     */
    @ExtraInfo(UnitTested = true)
    public static String[][] placeCommands(String[][] map, ArrayList<CharacterSceneMatch> scenes) {
        for (int i = 0; i < map.length; i++) {
            int mapRow = i + 1;
            enterRowData(map, i, iterateThroughAllScenesAndDialogueUntilMatch(scenes,
                    row -> (row == mapRow), Dialogue::getRow));
        }
        return map;
    }

    /**
     * Iterates through all of the scenes and then into every dialogue object and
     * checks one of the parameters inside them against a passed
     * value. If the value matches returns the tested dialogue in the scene.
     * @param scenes             The scenes to iterate through
     * @param checkObjects      The predicate that should be used to test the two values
     * @param objectFunction    The second value to be obtained from the dialogue
     * @param <T>               The generic typing of the parameter to be extracted from the dialogue
     * @return A dialogue matching the search constraints otherwise null
     */
    @ExtraInfo(UnitTested = true)
    public static <T> Dialogue iterateThroughAllScenesAndDialogueUntilMatch(ArrayList<CharacterSceneMatch> scenes,
                                                                            Predicate<T> checkObjects,
                                                                            Function<Dialogue, T> objectFunction) {
        final Dialogue[] d = {null};
        scenes.stream().takeWhile(t -> (d[0] == null)).forEach(scene -> d[0] = iterateThroughScene(scene, checkObjects,
                objectFunction));
        return d[0];
    }

    /**
     * Iterates through the scene into every dialogue object and checks one of the parameters inside them against a passed
     * value. If the value matches returns the tested dialogue in the scene.
     * @param scene             The scene to iterate through
     * @param checkObjects      The predicate that should be used to test the two values
     * @param objectFunction    The second value to be obtained from the dialogue
     * @param <T>               The generic typing of the parameter to be extracted from the dialogue
     * @return A dialogue matching the search constraints otherwise null
     */
    @ExtraInfo(UnitTested = true)
    public static <T> Dialogue iterateThroughScene(CharacterScene scene, Predicate<T> checkObjects,
                                                   Function<Dialogue, T> objectFunction) {
        for (Dialogue dialogue : scene) {
            if (checkObjects.test(objectFunction.apply(dialogue))) {
                return dialogue;
            }
        }
        return null;
    }

    /**
     * Enters the data for the rows into the map
     * @param map       The map to enter the data into
     * @param index     The index in the map to enter the data at
     * @param dialogue  The dialogue to enter the data from
     */
    @ExtraInfo(UnitTested = true)
    public static void enterRowData(String[][] map, int index, Dialogue dialogue) {
        ArrayList<String> v = new ArrayList<>(Arrays.asList((map[index] != null) ? dialogue.getText() : "DUPLICATE", String.valueOf(dialogue.getRow())));
        for (int x = 2; x < map[index].length; x++) {
            v.add("");
        }
        map[index] = v.toArray(new String[0]);
    }

    /**
     * Assigns the dialogue in the dialogue list to the correct scenes and then also
     * re-orders that scene to put the dialogue in correct order
     *
     * @param dialogueList        the list of dialogue to assign to scenes
     * @return 					the scenes containing the matching dialogue
     */
    @ExtraInfo(UnitTested = true)
    public static ArrayList<CharacterSceneMatch> assignDialogueToScene(ArrayList<Dialogue> dialogueList) {
        ArrayList<CharacterSceneMatch> scenes = new ArrayList<>();
        Object[] currentValues = new Object[]{"", 0, 0};
        CharacterSceneMatch scene = new CharacterSceneMatch(new CharacterScene());
        for (Dialogue dialogue : dialogueList) {
            if (currentSceneIsEmpty(currentValues)) {
                setSceneToCurrent(currentValues, dialogue);
            }
            if (isNotCurrentScene(currentValues, dialogue)) {
                scene.sort(Comparator.comparingInt(Dialogue::getTalkTime));
                scene.removeCopies();
                scenes.add(scene);
                setSceneToCurrent(currentValues, dialogue);
                scene = new CharacterSceneMatch(new CharacterScene());
            }
            scene.add(dialogue);
        }
        scenes.add(scene); //Adds the last scene
        return scenes;
    }

    /**
     * Checks the current scene to determine if it is empty or not
     * @param currentValues     The current values for the dialogue
     * @return      True if the current scene is empty
     */
    @ExtraInfo(UnitTested = true)
    protected static boolean currentSceneIsEmpty(Object[] currentValues) {
        return currentValues[0].equals("") || (int) currentValues[1] == 0 || (int) currentValues[2] == 0;
    }

    /**
     * Adjusts the current scene values to be equal to the values within the passed dialogue
     * @param currentValues     The current values for the dialogue
     * @param dialogue          The dialogue to set the current values to
     */
    @ExtraInfo(UnitTested = true)
    protected static void setSceneToCurrent(Object[] currentValues, Dialogue dialogue) {
        currentValues[0] = dialogue.getSpeaker();
        currentValues[1] = dialogue.getTriggerScore();
        currentValues[2] = dialogue.getTalkTime();
    }

    /**
     * Checks the dialogue to determine if it is still in the current scene
     * This means the speaker and the talk trigger are the same.
     *
     * @param currentValues The current values for the dialogue
     * @param dialogue      The dialogue to check
     * @return Whether the dialogue is in the current scene or not
     */
    @ExtraInfo(UnitTested = true)
    protected static boolean isNotCurrentScene(Object[] currentValues, Dialogue dialogue) {
        return !dialogue.getSpeaker().equals(currentValues[0]) || dialogue.getTriggerScore() != (int) currentValues[1];
    }
}
