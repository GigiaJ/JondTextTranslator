package TextTranslator;

import java.util.ArrayList;

public class CharacterSceneMatchHandler {

    /**
     * Filters the permutations to remove the shorter line matches and leave only
     * the longest possible match as well as removing duplicates.
     *
     * @param sceneMatches the list of scenes match objects
     * @return the filtered list
     */
    public static ArrayList<CharacterSceneMatch> filterPermutations(ArrayList<CharacterSceneMatch> sceneMatches) {
        for (int i = 0; i < sceneMatches.size(); i++) {
            // Iterate through the matches
            int longest = 0;
            // Store the longest length permutation per match
            int longestIndex = -1;
            ArrayList<Integer> indexesToRemove = new ArrayList<>();
            for (int x = 0; x < sceneMatches.get(i).getPermutationMatches().size(); x++) {
                if (!sceneMatches.get(i).getPermutationMatches().get(x).getLineMatches().isEmpty()) {
                    if (longest == 0) {
                        // Fill the values if the longest is 0
                        longest = sceneMatches.get(i).getPermutationMatches().get(x).getSize();
                        longestIndex = x;
                    }
                    else {
                        if (sceneMatches.get(i).getPermutationMatches().get(longestIndex).getStart() == sceneMatches.get(i)
                                .getPermutationMatches().get(x).getStart()) {
                            if (sceneMatches.get(i).getPermutationMatches().get(x).getSize() > longest) {
                                // if longer than the longest
                                // if the index starts match
                                longest = sceneMatches.get(i).getPermutationMatches().get(x).getSize();
                                // this is the new longest
                                indexesToRemove.add(longestIndex);
                                // remove the old longest
                                longestIndex = x ;
                            }
                            else {
                                // remove this as it is smaller than the longest for the start index
                                indexesToRemove.add(x);
                            }
                        }
                        else {
                            // if the index starts do not match
                            if (sceneMatches.get(i).getPermutationMatches().get(longestIndex).getEnd() == sceneMatches.get(i)
                                    .getPermutationMatches().get(x).getEnd()) {
                                if (sceneMatches.get(i).getPermutationMatches().get(x).getSize() > longest) {
                                    // if longer than the longest
                                    // if the index starts match
                                    longest = sceneMatches.get(i).getPermutationMatches().get(x).getSize();
                                    // this is the new longest
                                    indexesToRemove.add(longestIndex);
                                    // remove the old longest
                                    longestIndex = x;
                                }
                                else {
                                    // remove this as it is smaller than the longest for the start index
                                    indexesToRemove.add(x);
                                }
                            }
                            else {
                                longest = 0;
                                x = x - 1;
                                //Restart on this one to begin a new permutation set to check
                            }
                        }

                    }
                } else {
                    indexesToRemove.add(x);
                }
            }

            /*Remove in reverse to avoid having to change the index for the array list removal*/
            for (int x = indexesToRemove.size() - 1; x > 0; x--) {
                //Make a new object for index as otherwise the array list will try to remove
                //an Integer object and not an object at index of int
                int index = indexesToRemove.get(x);
                sceneMatches.get(i).getPermutationMatches().remove(index);
            }

            if (sceneMatches.get(i).getPermutationMatches().isEmpty()) {
                sceneMatches.remove(i);
                i = i - 1;
            }
        }
        return sceneMatches;
    }

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
     * @param altText      The other language dump file as an array list based on
     *                     line breaks
     * @return The 2d string array with the values collected mapped to each other in
     *         the inner array
     */
    public static String[][] translate(String[][] map, ArrayList<CharacterSceneMatch> sceneMatches,
                                       ArrayList<String> text, ArrayList<String> altText) {
        for (CharacterSceneMatch sceneMatch : sceneMatches) {
            for (PermutationMatch match : sceneMatch.getPermutationMatches()) {
                for (int x = match.getStart(); x < match.getSize() + match.getStart(); x++) {
                    int row = match.getScene().get(x).getRow() - 1;
                    if (match.hasLineMatches()) {
                        String english = "";
                        String other = "";
                        for (int line : match.getLineMatches()) {
                            english += text.get(line);
                            other += altText.get(line);
                        }
                        String[] value = new String[]{map[row][0], match.getText(), english, other};
                        if (map[row][1] != null && map[row][1].equals("")) {
                            map[row] = value;
                        } else {
                            value = new String[]{map[row][0], map[row][1] + " / " + match.getText(),
                                    map[row][2] + " / " + english, map[row][3] + " / " + other};
                            map[row] = value;

                        }
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
