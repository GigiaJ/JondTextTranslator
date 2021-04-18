package TextTranslator;

import java.util.ArrayList;
import static TextTranslator.Library.ExtraInfo;

/**
 * A class to handle the permutation matches with a character scene match
 */
public class PermutationMatchHandler {

    /**
     * Compares the first parameter's end position to the second parameter's
     * @param longestMatch      The longest match in the current cycle
     * @param compareAgainst    The current match in the cycle to check
     * @return                  true if the ending positions match
     */
    @ExtraInfo(UnitTested = true)
    protected static boolean hasMatchingEndingPositions(PermutationMatch longestMatch, PermutationMatch compareAgainst) {
        return longestMatch.getEnd() == compareAgainst.getEnd();
    }

    /**
     * Compares the first parameter's start position to the second parameter's
     * @param longestMatch      The longest match in the current cycle
     * @param compareAgainst    The current match in the cycle to check
     * @return                  true if the starting positions match
     */
    @ExtraInfo(UnitTested = true)
    protected static boolean hasMatchingStartingPositions(PermutationMatch longestMatch, PermutationMatch compareAgainst) {
        return longestMatch.getStart() == compareAgainst.getStart();
    }

    /**
     * Compares the first parameter's size to the second parameter's
     * @param longestMatch      The longest match in the current cycle
     * @param compareAgainst    The current match in the cycle to check
     * @return                  true if compareAgainst's size is greater than longestMatch's size
     */
    @ExtraInfo(UnitTested = true)
    protected static boolean isGreaterThanLongestSize(PermutationMatch longestMatch, PermutationMatch compareAgainst) {
        return longestMatch.getSize() < compareAgainst.getSize();
    }

    /**
     * Checks the integer to see if it is -1. If so then there is no longest value.
     * @param i     The integer value for the longest index
     * @return      true if the longest index value is not -1
     */
    @ExtraInfo(UnitTested = true)
    protected static boolean hasLongest(int i) {
        return i != -1;
    }
    /**
     * Removes the indexes in the array list from the scene match passed in reverse order so that
     * the index doesn't have to be adjusted during removal.
     * A new int object is made to avoid having the remove method from trying to remove an Integer
     * object from the list and not the object at the index of that Integer object.
     * @param sceneMatch           The scene match being operated on
     * @param indexesToRemove       The list of indexes to remove from the scene matches permutation match list
     */
    @ExtraInfo(UnitTested = true)
    protected static void removeFilteredMatches(CharacterSceneMatch sceneMatch, ArrayList<Integer> indexesToRemove) {
        for (int x = indexesToRemove.size() - 1; x > 0; x--) {
            int index = indexesToRemove.get(x);
            sceneMatch.getPermutationMatches().remove(index);
        }
    }

    /**
     * Removes empty matches from the sceneMatches arraylist if the permutation match list is empty
     * and returns the index adjusted down by one. Otherwise just returns the index.
     * @param sceneMatches      The arraylist containing all of the scene matches
     * @param index             The index in the loop that is iterating through the scene matches
     * @return                  The index of the loop being used to iterate over the scene matches
     */
    @ExtraInfo(UnitTested = true)
    protected static int removeEmptyMatches(ArrayList<CharacterSceneMatch> sceneMatches, int index) {
        if (sceneMatches.get(index).getPermutationMatches().isEmpty()) {
            sceneMatches.remove(index);
            return index - 1;
        }
        return index;
    }

    /**
     * Filters the permutations to remove the shorter line matches and leave only
     * the longest possible match as well as removing duplicates.
     *
     * @param sceneMatches the list of scenes match objects
     * @return the filtered list
     */
    @ExtraInfo(UnitTested = true)
    public static ArrayList<CharacterSceneMatch> filterPermutations(ArrayList<CharacterSceneMatch> sceneMatches) {
        for (int i = 0; i < sceneMatches.size(); i++) {
            int longestIndex = -1;
            ArrayList<Integer> indexesToRemove = new ArrayList<>();
            for (int x = 0; x < sceneMatches.get(i).getPermutationMatches().size(); x++) {
                PermutationMatch longestPermutation = sceneMatches.get(i).getPermutationMatches().get(longestIndex);
                PermutationMatch currentPermutation = sceneMatches.get(i).getPermutationMatches().get(x);
                if (!currentPermutation.getLineMatches().isEmpty()) {
                    boolean hasEndMatch = true;
                    if (hasLongest(longestIndex)) {
                        if ((hasMatchingStartingPositions(longestPermutation, currentPermutation)) ?
                                (hasMatchingStartingPositions(longestPermutation, currentPermutation)) :
                                (hasEndMatch = hasMatchingEndingPositions(longestPermutation, currentPermutation)) ?
                                        true : true)
                        {
                            if (hasEndMatch) {
                                if (isGreaterThanLongestSize(longestPermutation, currentPermutation)) {
                                    indexesToRemove.add(longestIndex);
                                    longestIndex = x;
                                } else {
                                    indexesToRemove.add(x);
                                }
                            }
                            else {
                                longestIndex = -1;
                                x = x - 1;
                            }
                        }
                    }
                    else {
                        longestIndex = x;
                    }
                } else {
                    indexesToRemove.add(x);
                }
            }

            removeFilteredMatches(sceneMatches.get(i), indexesToRemove);
            i = removeEmptyMatches(sceneMatches, i);
        }
        return sceneMatches;
    }


}
