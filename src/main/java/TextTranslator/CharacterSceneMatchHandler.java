package TextTranslator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class created to handle the character scene matches and the data within them.
 */
public class CharacterSceneMatchHandler {



    /**
     * Takes a 2d string array and fills the contents within the inner array with
     * the scene matches and their corresponding lines within the text dumps of the
     * English text dump file and another language text dump file
     *
     * Typically this will be ran after CharacterScene.placeCommands()
     *
     * @param map          The 2d string array to map the values in
     * @param sceneMatches The scenes that contain permutation matches from the
     *                     command excel sheet
     * @param text         The English text dump file as an array list based on line
     *                     breaks
     * @param altTexts     The other language dump files as an array of array lists based on
     *                     line breaks
     * @return The 2d string array with the values collected mapped to each other in
     *         the inner array
     */
    public static String[][] translate(String[][] map, ArrayList<CharacterSceneMatch> sceneMatches,
                                       ArrayList<String> text, @SuppressWarnings("rawtypes") ArrayList[] altTexts) {
        for (CharacterSceneMatch sceneMatch : sceneMatches) {
            for (PermutationMatch match : sceneMatch.getPermutationMatches()) {
                for (int x = match.getStart(); x < match.getSize() + match.getStart(); x++) {
                    int row = match.getScene().get(x).getRow() - 1;
                    if (match.hasLineMatches()) {
                        String english = "";
                        String[] other = new String[altTexts.length];
                        for (int line : match.getLineMatches()) {
                            english += text.get(line);
                            for (int i = 0; i < altTexts.length; i++) {
                                other[i] = (String) altTexts[i].get(line);
                            }
                        }
                        ArrayList<String> v = new ArrayList<>(Arrays.asList(map[row][0], map[row][1], match.getText(), english));
                        v.addAll(Arrays.asList(other));
                        if (map[row][0] == null || !map[row][2].equals("")) {
                            v.set(0, map[row][0]);
                            v.set(1, map[row][1]);
                            v.set(2, map[row][2] + " / " + match.getText());
                            v.set(3, map[row][3] + " / " + english);
                            for (int i = 0; i < other.length; i++) {
                                v.set(i + 4, v.get(i + 4) + " / " + other[i]);
                            }
                        }
                        map[row] = v.toArray(new String[0]);
                    }
                }
            }
        }
        return map;
    }

    /**
     * Checks for collisions by iterating over <var>m1</var><var>m2</var> both lists
     * and then performing iterations over
     *
     * the match objects within that list <var>match</var> within that the
     * permutation match list <var>pm</var> within that the line matches list
     * <var>line</var>
     *
     * Within the iterations for <var>match</var> the matches are checked to ensure
     * empty ones are skipped
     *
     * Within the iterations for <var>line</var> the lines are compared to see if
     * there is a collision/equal value in there if so the respective
     * <var>match</var> is then removed and the method is repeated until all the
     * collisions are removed
     *
     * @param matches1 the list to check for collisions in
     * @param matches2 the list to remove collisions from
     */
    public static void removeCollisions(ArrayList<CharacterSceneMatch> matches1,
                                        ArrayList<CharacterSceneMatch> matches2) {
        for (int i = 0; i < matches1.size(); i++) {
            CharacterSceneMatch scene1 = matches1.get(i);
            for (int x = 0; x < matches2.size(); x++) {
                CharacterSceneMatch scene2 = matches2.get(x);
                if (!scene1.getPermutationMatches().isEmpty() && !scene2.getPermutationMatches().isEmpty()) {
                    for (int pmx = 0; pmx < scene1.getPermutationMatches().size(); pmx++) {
                        PermutationMatch pm1 = scene1.getPermutationMatches().get(pmx);
                        if (!scene2.getPermutationMatches().isEmpty()) {
                            for (int pmy = 0; pmy < scene2.getPermutationMatches().size(); pmy++) {
                                PermutationMatch pm2 = scene2.getPermutationMatches().get(pmy);
                                if (removeLineMatch(pm1, pm2)) {
                                    scene2.removePermutationMatch(pm2);
                                    //matches2.set(x, scene2);
                                    pmy = pmy - 1;
                                }
                            }
                        }
                    }
                    if (scene1.getScene().getTrigger() == matches2.get(x).getScene().getTrigger() &&
                            scene1.getScene().get(0).getRow() == scene2.getScene().get(0).getRow()) {
                        scene1.getPermutationMatches().addAll(scene2.getPermutationMatches());
                    }
                } else {
                    if (scene2.getPermutationMatches().isEmpty()) {
                        matches2.remove(x);
                        x = x - 1;
                        continue;
                    }
                    if (scene1.getPermutationMatches().isEmpty()) {
                        matches1.remove(i);
                        i = i - 1;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Compares the line matches from the text dumps within two permutation matches
     * @param pm1		The first permutation match to check
     * @param pm2		The second permutation match to check
     * @return			True if their is a collision and the permutation match needs to be removed
     */
    public static boolean removeLineMatch(PermutationMatch pm1, PermutationMatch pm2) {
        if (pm1.getStart() == pm2.getStart() && pm1.getEnd() == pm2.getEnd()) {
            for (Integer s1 : pm1.getLineMatches()) {
                for (Integer s2 : pm2.getLineMatches()) {
                    if (s1.equals(s2) && pm1.getScene().getTrigger() == pm2.getScene().getTrigger()) {
                        int pm1s = pm1.getScene().get(pm1.getStart()).getRow();
                        int pm2s = pm2.getScene().get(pm2.getStart()).getRow();
                        if  (pm1s == pm2s) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets all the lines in the text dump that match the permutations generated
     * from the dialogue lines
     *
     * @param scenes the list of dialogues
     * @param textDump     the lines in the text file
     * @return the list of character scenes with their potential matching lines
     */
    public static ArrayList<CharacterSceneMatch> getMatchingLines(ArrayList<CharacterScene> scenes,
                                                                  ArrayList<String> textDump, boolean exactMatch) {
        ArrayList<CharacterSceneMatch> sceneMatchList = new ArrayList<>();
        // Iterate through the character scenes to find matches
        for (int i = 0; i < scenes.size(); i++) {
            CharacterScene scene = scenes.get(i);
            CharacterSceneMatch sceneMatch = new CharacterSceneMatch();
            // List of the possible permutations of this text
            ArrayList<PermutationMatch> permutationsList = PermutationMatch.combinations(scene,
                    new ArrayList<PermutationMatch>(), 0);
            // Iterate through all of the permutations
            for (PermutationMatch match : permutationsList) {
                // Now iterate through the English text to find lines that match the
                // permutations
                // K being the line in the text file
                for (int k = 0; k < textDump.size(); k++) {
                    String textToCheck = textDump.get(k); // The line in the text dump we want to check
                    String permutationToCheck = match.getText();
                    if (exactMatch) {
                        if (textToCheck.contains("When I did this, the man asked me")) {
                            if (permutationToCheck.contains("When I did this, the man asked me")) {
                                System.out.println();
                            }
                        }
                        if (textToCheck.equals(permutationToCheck)) {
                            match.addLineMatch(k);
                        }
                    } else {
                        if (textToCheck.contains(permutationToCheck)) {
                            match.addLineMatch(k);
                        }
                    }
                }
                if (match.hasLineMatches()) {
                    sceneMatch.addPermutationMatch(match);
                }
                sceneMatch.setScene(scene);
            }
            sceneMatchList.add(sceneMatch);

        }
        return sceneMatchList;
    }

    /**
     * Outputs to the console a line in a scene that does not contain a match as
     * well as the number of them
     *
     * @param sceneMatchList the list of scenes containing matches
     */
    public static void printNonMatches(ArrayList<CharacterSceneMatch> sceneMatchList) {
        int counter = 0;
        for (CharacterSceneMatch sceneMatch : sceneMatchList) {
            if (sceneMatch.getPermutationMatches().isEmpty()) {
                for (Dialogue dialogue : sceneMatch.getScene()) {
                    System.out.println(dialogue.getText());
                    //counter++;
                }
                System.out.println(sceneMatch.getScene().size());
                System.out.println();
                counter++;
            }
        }
        System.out.println(counter);
    }


    /**
     * Outputs to the console a line in a scene that does a match as well as the
     * number of them
     *
     * @param sceneMatchList the list of scenes containing matches
     * @param text           the text dump as an array list
     */
    public static void printMatches(ArrayList<CharacterSceneMatch> sceneMatchList, ArrayList<String> text) {
        int counter = 0;
        for (CharacterSceneMatch sceneMatch : sceneMatchList) {
            for (PermutationMatch match : sceneMatch.getPermutationMatches()) {
                counter++;
                System.out.println(match.text);
                for (int i : match.getLineMatches()) {
                    System.out.println(text.get(i));
                }

            }
        }
        System.out.println(counter);
    }
}
