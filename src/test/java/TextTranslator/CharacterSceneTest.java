package TextTranslator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CharacterSceneTest extends CharacterScene {
    private static final CharacterScene mockList = new CharacterScene(Arrays.asList(
            new Dialogue("Mom", "This is a text", null, 3, 3, 3, 35),
            new Dialogue("Mom", "This is a text also", null, 3, 3, 3, 35),
            new Dialogue("Mom", "This is a text also", null, 3, 3, 3, 33),
            new Dialogue("Mom", "This is a text also", null, 3, 10, 3, 36),
            new Dialogue("Mom", "This is a text also", null, 3, 10, 3, 35),
            new Dialogue("Dad", "This is a text also", null, 3, 3, 3, 35)));

    @Test
    public void testCheckContains() {
        Assert.assertTrue(
                this.checkContains(
                        new Dialogue("Mom", "This is a text", null, 3, 3, 3, 35),
                        mockList));

        Assert.assertFalse(this.checkContains(
                new Dialogue("Grandma", "This is a text", null, 3, 3, 3, 35),
                mockList));
    }

    @Test
    public void testRemoveCopies() {
        CharacterScene expected = new CharacterScene(Arrays.asList(
                new Dialogue("Mom", "This is a text", null, 3, 3, 3, 35),
                new Dialogue("Mom", "This is a text also", null, 3, 10, 3, 36)));

        mockList.removeCopies();

        for (int x = 0; x < expected.size(); x++) {
            Assert.assertEquals(mockList.get(0), expected.get(0));
        }
    }
}
