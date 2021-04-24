package TextTranslator;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static TextTranslator.Library.ExtraInfo;
/**
 * A class created to handle the character scene matches and the data within them.
 */
@Slf4j
public class CharacterSceneMatchHandler {

    /**
     * Takes a 2d string array and fills the contents within the inner array with
     * the scene matches and their corresponding lines within the text dumps of the
     * English text dump file and another language text dump file
     * <p>
     * Typically this will be ran after CharacterScene.placeCommands()
     *
     * @param map                The 2d string array to map the values in
     *                           The inner array values are in order of row, text from the command excel sheet,
     *                           the permutation match text that found a match, the English text dump line
     *                           that the permutation match was found on, and then any other languages for which
     *                           the line is the same as the English text line that was found by the
     *                           permutation match.
     *                           This is due to the text dumps for languages being identical line for line in
     *                           terms of actual dialogue. They're simply in different spoken languages.
     * @param sceneMatches       The scenes that contain permutation matches from the
     *                           command excel sheet
     * @param englishText        The English text dump file as an array list based on line
     *                           breaks
     * @param extraLanguagesText The other language dump files as an array of array lists based on
     *                           line breaks
     * @return The 2d string array with the values collected mapped to each other in
     * the inner array
     */
    @ExtraInfo(UnitTested = true)
    public static String[][] translate(String[][] map, ArrayList<CharacterSceneMatch> sceneMatches, ArrayList<String> englishText, @SuppressWarnings("rawtypes") ArrayList[] extraLanguagesText) {
        log.info("Beginning translation and assignment.");
        sceneMatches.forEach(sceneMatch -> sceneMatch.getPermutationMatches().forEach(permutationMatch -> {
            for (int x = permutationMatch.getStart(); x < permutationMatch.getSize() + permutationMatch.getStart(); x++) {
                if (permutationMatch.hasLineMatches()) {
                    int row = permutationMatch.getScene().get(x).getRow() - 1;
                    StringBuilder english = new StringBuilder();
                    String[] extraLanguages = new String[extraLanguagesText.length];
                    setLineMatchText(english, extraLanguages, englishText, extraLanguagesText, permutationMatch);
                    assignRowEntry(map, row, english, extraLanguages, permutationMatch);
                }
            }
        }));
        return map;
    }

    /**
     * Assigns the line match text to the respective variables. English text with line matches will be given
     * assigned via the line match in the PermutationMatch. The same line is then used to find the matching text
     * for other languages.
     *
     * @param english            The StringBuilder to fill with the English text
     * @param extraLanguages     The array to fill with the respective language text
     * @param englishText        The English text dump file as an array list based on line breaks
     * @param extraLanguagesText The other language dump files as an array of array lists based on line breaks
     * @param match              The permutation match to get the matching text lines from
     */
    @ExtraInfo(UnitTested = true)
    protected static void setLineMatchText(StringBuilder english, String[] extraLanguages,
                                           ArrayList<String> englishText,
                                           @SuppressWarnings("rawtypes") ArrayList[] extraLanguagesText,
                                           PermutationMatch match) {
        for (int line : match.getLineMatches()) {
            english.append(englishText.get(line));
            for (int i = 0; i < extraLanguagesText.length; i++) {
                extraLanguages[i] = (String) extraLanguagesText[i].get(line);
            }
        }
    }

    /**
     * Assigns the output entry if the output entry is already pre-populated with the data extracted from
     * the excel sheets. The data is entered into an arraylist for ease of adding the extra language data
     * and then is simply assigned to the corresponding entry in the output array by converting it back to an array.
     *
     * @param map            The output array to populate with data
     * @param row            The row in the excel sheet that this match is for from the scene object
     * @param english        The StringBuilder to fill with the English text
     * @param extraLanguages The array to fill with the respective language text
     * @param match          The permutation match to get the matching text lines from
     */
    @ExtraInfo(UnitTested = true)
    protected static void assignRowEntry(String[][] map, int row, StringBuilder english,
                                         String[] extraLanguages, PermutationMatch match) {
        ArrayList<String> v = new ArrayList<>(Arrays.asList(map[row][0], map[row][1], match.getText(),
                english.toString()));
        v.addAll(Arrays.asList(extraLanguages));
        if (map[row][0] == null || !map[row][2].equals("")) {
            v.set(0, map[row][0]);
            v.set(1, map[row][1]);
            v.set(2, map[row][2] + " / " + match.getText());
            v.set(3, map[row][3] + " / " + english);
            for (int i = 0; i < extraLanguages.length; i++) {
                v.set(i + 4, v.get(i + 4) + " / " + extraLanguages[i]);
            }
        }
        map[row] = v.toArray(new String[0]);
    }

    /**
     * Checks for collisions by iterating over <var>m1</var><var>m2</var> both lists
     * and then performing iterations over
     * the match objects within that list <var>match</var> within that the
     * permutation match list <var>pm</var> within that the line matches list
     * <var>line</var>
     * Within the iterations for <var>match</var> the matches are checked to ensure
     * empty ones are skipped
     * Within the iterations for <var>line</var> the lines are compared to see if
     * there is a collision/equal value in there if so the respective
     * <var>match</var> is then removed and the method is repeated until all the
     * collisions are removed
     *
     * @param checkAgainstList the list to check for collisions in
     * @param toCheckList      the list to remove collisions from
     */
    @ExtraInfo(UnitTested = true)
    public static void removeCollisions(ArrayList<CharacterSceneMatch> checkAgainstList,
                                        ArrayList<CharacterSceneMatch> toCheckList) {
        log.info("Removing collisions from the match lists");
        for (Iterator<CharacterSceneMatch> checkAgainstIterator = checkAgainstList.iterator(); checkAgainstIterator.hasNext(); ) {
            CharacterSceneMatch checkAgainstEntry = checkAgainstIterator.next();
            for (Iterator<CharacterSceneMatch> toCheckIterator = toCheckList.iterator(); toCheckIterator.hasNext(); ) {
                CharacterSceneMatch toCheckEntry = toCheckIterator.next();
                if (!checkAgainstEntry.getPermutationMatches().isEmpty() && !toCheckEntry.getPermutationMatches().isEmpty()) {
                    comparePermutationMatches(checkAgainstEntry, toCheckEntry);
                } else {
                    if (toCheckEntry.getPermutationMatches().isEmpty()) {
                        toCheckIterator.remove();
                        continue;
                    }
                    if (checkAgainstEntry.getPermutationMatches().isEmpty()) {
                        checkAgainstIterator.remove();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Compares two sets of permutation matches by iterating through them and removes any duplicates within.
     * Afterwords if the CharacterSceneMatch toCheck still has contents within it they are added to
     * the checkAgainst object
     *
     * @param checkAgainst The scene match to check against and add to
     * @param toCheck      The scene object to check and remove from
     */
    @ExtraInfo(UnitTested = true)
    protected static void comparePermutationMatches(CharacterSceneMatch checkAgainst, CharacterSceneMatch toCheck) {
        for (PermutationMatch match : checkAgainst.getPermutationMatches()) {
            toCheck.getPermutationMatches().removeIf(permutationMatchToCheck -> removeLineMatch(match, permutationMatchToCheck));
        }
        if (checkAgainst.getTrigger() == toCheck.getTrigger() &&
                checkAgainst.getRow() == toCheck.getRow()) {
            checkAgainst.getPermutationMatches().addAll(toCheck.getPermutationMatches());
        }
    }

    /**
     * Compares the line matches from the text dumps within two permutation matches
     *
     * @param pm1 The first permutation match to check
     * @param pm2 The second permutation match to check
     * @return True if their is a collision and the permutation match needs to be removed
     */
    @ExtraInfo(UnitTested = true)
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
        for (CharacterScene scene : scenes) {
            sceneMatchList.add(getMatchingLines(scene, textDump, exactMatch));
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
        CharacterSceneMatch sceneMatch = new CharacterSceneMatch(scene);
        ArrayList<PermutationMatch> permutationsList = PermutationMatch.combinations(scene,
                new ArrayList<>(), 0);
        for (PermutationMatch match : permutationsList) {
            addMatchingLines(match, textDump, exactMatch);
            if (match.hasLineMatches()) {
                sceneMatch.addPermutationMatch(match);
            }
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
