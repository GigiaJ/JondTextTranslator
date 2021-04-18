package TextTranslator;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A class to handle changes performed involving character scenes
 */
@Slf4j
public class CharacterSceneHandler {
    /**
     * Places the commands from the excel sheet into a 2d string array populating the first value of the
     * inner array with the text for the row of the excel sheet and the last value of the inner array with
     * the actual row number as a string
     *
     * @param map			the 2d string array to populate
     * @param scenes		the scenes containing dialogue extracted from the command excel sheet
     * @return				the 2d string array containing the lines of text for the row matching
     * 						the index of the outer string array and the row number itself
     */
    public static String[][] placeCommands(String[][] map, ArrayList<CharacterScene> scenes) {
        for (int i = 0; i < map.length; i++) {
            for (CharacterScene scene : scenes) {
                for (Dialogue dialogue : scene) {
                    if (dialogue.getRow() == i + 1) {
                        ArrayList<String> v = new ArrayList<>(List.of(dialogue.getText(), String.valueOf(dialogue.getRow())));
                        for (int x = 2; x < map[i].length; x++) {
                            v.add("");
                        }
                        map[i] = v.toArray(new String[0]);
                    }
                }
            }
            if (map[i] == null) {
                ArrayList<String> v = new ArrayList<>(List.of("DUPLICATE LINE"));
                for (int x = 1; x < map[i].length; x++) {
                    v.add("");
                }
                map[i] = v.toArray(new String[0]);
            }
        }
        return map;
    }

    /**
     * Assigns the dialogue in the dialogue list to the correct scenes and then also
     * re-orders that scene to put the dialogue in correct order
     *
     * @param dialogueList 		the list of dialogue to assign to scenes
     * @return 					the scenes containing the matching dialogue
     */
    public static ArrayList<CharacterScene> assignDialogueToScene(ArrayList<Dialogue> dialogueList) {
        ArrayList<CharacterScene> scenes = new ArrayList<>();
        String currentSpeaker = "";
        int currentTalkTime = 0;
        int currentTrigger = 0;

        CharacterScene scene = new CharacterScene();
        for (Dialogue dialogue : dialogueList) {
            // Assigns these values for the first iteration through the loop
            if (currentSpeaker.equals("") || currentTalkTime == 0 || currentTrigger == 0) {
                currentSpeaker = dialogue.getSpeaker();
                currentTalkTime = dialogue.getTalkTime();
                currentTrigger = dialogue.getTrigger();
            }
            // If the values don't match then we're on a new scene
            if (!dialogue.getSpeaker().equals(currentSpeaker) || dialogue.getTrigger() != currentTrigger) {
                // Sorts the scene by the correct speaking lines
                scene.sort(Comparator.comparingInt(Dialogue::getTalkTime));
                scene.removeCopies();
                scenes.add(scene);
                currentSpeaker = dialogue.getSpeaker();
                currentTalkTime = dialogue.getTalkTime();
                currentTrigger = dialogue.getTrigger();
                scene = new CharacterScene();
            }
            scene.add(dialogue);
        }
        scenes.add(scene); //This scene is the last one and isn't added because there isn't anyway for the
        //the above requirements to be true
        return scenes;
    }
}
