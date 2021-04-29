package TextTranslator;

import org.junit.Assert;

public class PermutationMatchHandlerTest extends PermutationMatchHandler {

    public void testHasMatchingEndingPositions() {
        PermutationMatch first = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        PermutationMatch second = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        Assert.assertTrue(hasMatchingEndingPositions(first, second));
    }

    public void testHasMatchingStartingPositions() {
        PermutationMatch first = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        PermutationMatch second = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        Assert.assertTrue(hasMatchingStartingPositions(first, second));
    }

    public void testIsGreaterThanLongestSize() {
        PermutationMatch first = new PermutationMatch("This is a filler text", 0, 2, new CharacterScene());
        PermutationMatch second = new PermutationMatch("This is a filler text", 0, 4, new CharacterScene());
        Assert.assertTrue(isGreaterThanLongestSize(first, second));
    }

    public void testHasLongest() {
        Assert.assertTrue(hasLongest(4));
    }

    public void testRemoveFilteredMatches() {

    }

    public void testRemoveEmptyMatches() {

    }

    public void testFilterPermutations() {

    }

    public void testCombinations() {

    }


}
