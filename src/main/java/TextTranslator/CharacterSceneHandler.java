package TextTranslator;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterSceneHandler {
    /**
     * Places the commands from the excel sheet into a 2d string array populating the first value of the
     * inner array with the text for the row of the excel sheet and the last value of the inner array with
     * the actual row number as a string
     * If the entry is null after this it is likely a duplicate and was accidentally removed
     * TODO:
     * Remove the duplicate issue
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
                        map[i] =
                                new String[] {
                                        dialogue.getText()/*ROWTEXT*/,
                                        ""/*MATCHED*/,
                                        ""/*ENGLISHFOUND*/,
                                        ""/*ALTLANGUAGEFOUND*/,
                                        String.valueOf(dialogue.getRow())
                                };

                    }
                }
            }
            if (map[i] == null) {
                map[i] =
                        new String[] {
                                "DUPLICATE LINE"/*ROWTEXT*/,
                                ""/*MATCHED*/,
                                ""/*ENGLISHFOUND*/,
                                ""/*ALTLANGUAGEFOUND*/,
                                ""
                        };
            }
        }
        return map;
    }

    /**
     * Gets the scenes that do not contain any matches in it to filter out
     *
     * @param dialogueMatchList		the list to check
     * @return						a list containing the non-matching scenes
     */
    public static ArrayList<CharacterScene> getNonMatchingScenes(ArrayList<CharacterSceneMatch> dialogueMatchList) {
        ArrayList<CharacterScene> scenes = new ArrayList<CharacterScene>();
        for (CharacterSceneMatch dialogueMatch : dialogueMatchList) {
            if (dialogueMatch.getPermutationMatches().isEmpty()) {
                scenes.add(dialogueMatch.getScene());
            }
        }
        return scenes;
    }

    /**
     * Assigns the dialogue in the dialogue list to the correct scenes and then also
     * re-orders that scene to put the dialogue in correct order
     *
     * @param dialogueList 		the list of dialogue to assign to scenes
     * @return 					the scenes containing the matching dialogue
     */
    public static ArrayList<CharacterScene> assignDialogueToScene(ArrayList<Dialogue> dialogueList) {
        ArrayList<CharacterScene> scenes = new ArrayList<CharacterScene>();
        String currentSpeaker = "";
        int currentTalkTime = 0;
        int currentTrigger = 0;

        CharacterScene scene = new CharacterScene();
        for (Dialogue dialogue : dialogueList) {
            // Assigns these values for the first iteration through the loop
            if (currentSpeaker == "" || currentTalkTime == 0 || currentTrigger == 0) {
                currentSpeaker = dialogue.getSpeaker();
                currentTalkTime = dialogue.getTalkTime();
                currentTrigger = dialogue.getTrigger();
                scene.add(dialogue);
            }
            // If the values don't match then we're on a new scene
            if (!dialogue.getSpeaker().equals(currentSpeaker) || dialogue.getTrigger() != currentTrigger) {
                // Sorts the scene by the correct speaking lines
                Collections.sort(scene, (o1, o2) -> o1.getTalkTime() - o2.getTalkTime());
                scene.removeCopies();
                scenes.add(scene);
                currentSpeaker = dialogue.getSpeaker();
                currentTalkTime = dialogue.getTalkTime();
                currentTrigger = dialogue.getTrigger();
                scene = new CharacterScene();
                scene.add(dialogue);
            } else {
                scene.add(dialogue);
            }
        }
        scenes.add(scene); //This scene is the last one and isn't added because there isn't anyway for the
        //the above requirements to be true

        return scenes;
    }
}
