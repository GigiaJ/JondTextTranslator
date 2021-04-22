package TextTranslator;

import java.util.ArrayList;
import java.util.Arrays;
import static TextTranslator.Library.ExtraInfo;
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
    protected static boolean removeLineMatch(PermutationMatch pm1, PermutationMatch pm2) {
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
     * @param scenes        The scenes to get the matching lines for
     * @param textDump      The English text dump lines
     * @param exactMatch    Whether or not the results should be an exact match or a partial match
     * @return              A list of Character Scene Match objects with the data filled by getMatchingLines()
     */
    @ExtraInfo(UnitTested = true)
    public static ArrayList<CharacterSceneMatch> getAllMatchingLines(ArrayList<CharacterScene> scenes,
                                                                  ArrayList<String> textDump, boolean exactMatch) {
        ArrayList<CharacterSceneMatch> sceneMatchList = new ArrayList<>();
        for (int i = 0; i < scenes.size(); i++) {
            sceneMatchList.add(getMatchingLines(scenes.get(i), textDump, exactMatch));
        }
        return sceneMatchList;
    }

    /**
     * Gets the matching lines for the given scenes dialogue by iterating through  a generated permutations list
     * of dialogue texts
     * @param scene         The scene to get the matching lines for
     * @param textDump      The English text dump lines
     * @param exactMatch    Whether or not the results should be an exact match or a partial match
     * @return              The character scene passed as a Character Scene Match object containing the permutations
     *                      that have matches and their respective matching line numbers in the English text dump
     */
    @ExtraInfo(UnitTested = true, Review=true)
    protected static CharacterSceneMatch getMatchingLines(CharacterScene scene, ArrayList<String> textDump, boolean exactMatch) {
        CharacterSceneMatch sceneMatch = new CharacterSceneMatch();
        ArrayList<PermutationMatch> permutationsList = PermutationMatch.combinations(scene,
                new ArrayList<>(), 0);
        for (PermutationMatch match : permutationsList) {
            addMatchingLines(match, textDump, exactMatch);
            if (match.hasLineMatches()) {
                sceneMatch.addPermutationMatch(match);
            }
            sceneMatch.setScene(scene);
        }
        return sceneMatch;
    }


    /**
     * Iterates through the English text to find lines in the dump that match the text in the permutation passed.
     * Based on the boolean the results can be an exact match or a partial match to the text of the permutation.
     * @param match         The permutation match to check for any matches
     * @param textDump      The English text dump to search for matches in
     * @param exactMatch     A boolean to determine whether to get a partial match or an exact match
     */
    @ExtraInfo(UnitTested = true)
    protected static void addMatchingLines(PermutationMatch match, ArrayList<String> textDump, boolean exactMatch) {
        String permutationToCheck = match.getText();
        for (int k = 0; k < textDump.size(); k++) {
            String textToCheck = textDump.get(k);
            if ((exactMatch) ? textToCheck.equals(permutationToCheck) : textToCheck.contains(permutationToCheck)) {
                    match.addLineMatch(k);
            }
        }
    }
}
