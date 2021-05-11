package TextTranslator.scene;

import TextTranslator.scene.character.CharacterScene;
import TextTranslator.scene.character.Dialogue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SceneTest {
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
                Scene.checkContains(
                        new Dialogue("Mom", "This is a text", null, 3, 3, 3, 35),
                        mockList));

        Assert.assertFalse(Scene.checkContains(
                new Dialogue("Grandma", "This is a text", null, 3, 3, 3, 35),
                mockList));
    }
}
