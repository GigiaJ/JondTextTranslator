package TextTranslator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CommandMakerTest implements MockSceneAndTexts {

    @Test
    public void testCreateCommands() {
        CharacterSceneMatch scene = generateSceneMatchList(true, true).get(5);
        CommandMaker commandMaker = new CommandMaker(scene, mockEnglishText, Language.ENG);
        CharacterScene commands = commandMaker.createCommands(0);
        CharacterScene expectedCommands = new CharacterScene(Arrays.asList(new Dialogue("Mom", "To test for any ", null, 1, 5, 0, -1),
                new Dialogue("Mom", "potential bugs.", null, 1, 5, 2, -1)));
        for (int i = 0; i < expectedCommands.size(); i++) {
            Assert.assertEquals(expectedCommands.get(i), commands.get(i));
        }
    }
}
