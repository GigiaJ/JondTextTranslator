package TextTranslator.scene.character;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class PermutationMatchHandlerTest extends PermutationMatchHandler {

    @Test
    public void testHasMatchingEndingPositions() {
        PermutationMatch first = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        PermutationMatch second = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        Assert.assertTrue(hasMatchingEndingPositions(first, second));
    }

    @Test
    public void testHasMatchingStartingPositions() {
        PermutationMatch first = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        PermutationMatch second = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        Assert.assertTrue(hasMatchingStartingPositions(first, second));
    }

    @Test
    public void testIsGreaterThanLongestSize() {
        PermutationMatch first = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        PermutationMatch second = new PermutationMatch("This is a filler text", 0, 4, new CharacterScene());
        Assert.assertTrue(isGreaterThanLongestSize(first, second));
    }

    @Test
    public void testHasLongest() {
        Assert.assertTrue(hasLongest(4));
    }

    @Test
    public void testRemoveFilteredMatches() {
        CharacterScene blankScene = new CharacterScene();
        CharacterSceneMatch mockSceneMatch = new CharacterSceneMatch(blankScene);
        ArrayList<Integer> listToSuccessfullyRemove = new ArrayList<>(Arrays.asList(3, 1));
        PermutationMatch firstRemoval = new PermutationMatch("Random", 1, 2, blankScene);
        PermutationMatch secondRemoval = new PermutationMatch("Random", 3, 4, blankScene);


        mockSceneMatch.setPermutationMatches(new ArrayList<>(Arrays.asList(
                new PermutationMatch("Random", 0, 2, blankScene),
                firstRemoval,
                new PermutationMatch("Random", 2, 2, blankScene),
                secondRemoval,
                new PermutationMatch("Random", 5, 6, blankScene)))
        );
        int initialSize = mockSceneMatch.getPermutationMatches().size();

        removeFilteredMatches(mockSceneMatch, listToSuccessfullyRemove);

        Assert.assertTrue(initialSize > mockSceneMatch.getPermutationMatches().size());
    }

    @Test
    public void testRemoveEmptyMatches() {
        ArrayList<CharacterSceneMatch> mockSceneMatchList = new ArrayList<>(Arrays.asList(
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()))
        );
        final int REMOVED_SUCCESSFULLY = -1;
        int indexToRemove = 3;

        Assert.assertEquals(removeEmptyMatches(mockSceneMatchList, indexToRemove), indexToRemove + REMOVED_SUCCESSFULLY);
    }

    @Test
    public void testFilterPermutations() {
        final int NUMBER_OF_SCENE_MATCHES = 5;
        final int NUMBER_OF_PERMUTATION_MATCHES = 5;

        ArrayList<CharacterSceneMatch> mockSceneMatchList = new ArrayList<>();
        CharacterScene blankScene = new CharacterScene();

        for (int i = 0; i < NUMBER_OF_SCENE_MATCHES - 1; i++) {
            mockSceneMatchList.add(new CharacterSceneMatch(blankScene));
            mockSceneMatchList.get(i).setPermutationMatches(new ArrayList<>(Arrays.asList(
                    new PermutationMatch("Random", 0, 1, blankScene),
                    new PermutationMatch("Random", 1, 1, blankScene),
                    new PermutationMatch("Random", 2, 3, blankScene),
                    new PermutationMatch("Random", 2, 4, blankScene),
                    new PermutationMatch("Random", 5, 6, blankScene))
            ));
            for (PermutationMatch permutationMatch : mockSceneMatchList.get(i).getPermutationMatches()) {
                permutationMatch.addLineMatch(0);
            }
        }
        mockSceneMatchList.add(new CharacterSceneMatch(blankScene));

        filterPermutations(mockSceneMatchList);

        Assert.assertTrue(NUMBER_OF_PERMUTATION_MATCHES > mockSceneMatchList.get(2).getPermutationMatches().size());
        Assert.assertTrue(NUMBER_OF_SCENE_MATCHES > mockSceneMatchList.size());
    }

    @Test
    public void testCombinations() {
        CharacterScene mockScene = new CharacterScene();
        mockScene.addAll(Arrays.asList(
                new Dialogue("Mom", "This is a test.", "red", null, 1, 1, 1, 1, 1),
                new Dialogue("Mom", "This is also a test.", "red", null, 1, 1, 1, 10, 2),
                new Dialogue("Dad", "Not mom speaking now", "red", null, 1, 1, 1, 30, 3)
        ));

        ArrayList<PermutationMatch> expectedList = new ArrayList<>(Arrays.asList(
                new PermutationMatch("This is a test.", 0, 0, mockScene),
                new PermutationMatch("This is a test. This is also a test.", 0, 1, mockScene),
                new PermutationMatch("This is a test. This is also a test. Not mom speaking now", 0, 2, mockScene),
                new PermutationMatch("This is also a test.", 1, 1, mockScene),
                new PermutationMatch("This is also a test. Not mom speaking now", 1, 2, mockScene),
                new PermutationMatch("Not mom speaking now", 2, 2, mockScene)

        ));

        ArrayList<PermutationMatch> combinations = combinations(mockScene, new ArrayList<>(), 0);

        for (int i = 0; i < combinations.size(); i++) {
            Assert.assertEquals(combinations.get(i), (expectedList.get(i)));
        }

    }


}
