package TextTranslator.scene;

import TextTranslator.scene.character.CharacterScene;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.TellRaw;
import TextTranslator.scene.command.TellRawText;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SceneTest {
    private static final CharacterScene mockList = new CharacterScene(Arrays.asList(
            new TellRaw( new TellRawText("Mom", "This is a text", "red"), new TargetSelector( null, 3, 3, 3, 3), 35),
            new TellRaw(new TellRawText("Mom", "This is a text also", "red"), new TargetSelector(null, 3, 3, 3, 3), 35),
            new TellRaw( new TellRawText("Mom", "This is a text also", "red"), new TargetSelector( null, 3, 3, 3, 3), 33),
            new TellRaw( new TellRawText("Mom", "This is a text also", "red"), new TargetSelector(null, 3, 3, 10, 3), 36),
            new TellRaw( new TellRawText( "Mom", "This is a text also", "red"), new TargetSelector(null, 3, 3, 10, 3), 35),
            new TellRaw( new TellRawText( "Dad", "This is a text also", "red"), new TargetSelector(null, 3, 3, 3, 3), 35)));

    @Test
    public void testCheckContains() {
        Assert.assertTrue(
                Scene.checkContains(
                        new TellRaw(new TellRawText("Mom", "This is a text", "red"), new TargetSelector(null, 3, 3, 3, 3), 35),
                        mockList));

        Assert.assertFalse(Scene.checkContains(
                new TellRaw(new TellRawText("Grandma", "This is a text", "red"), new TargetSelector( null, 3, 3, 3, 3), 35),
                mockList));
    }
}
