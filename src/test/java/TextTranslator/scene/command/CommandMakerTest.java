package TextTranslator.scene.command;

import TextTranslator.scene.MockSceneAndTexts;
import TextTranslator.scene.character.CharacterSceneMatch;
import TextTranslator.utils.Language;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CommandMakerTest implements MockSceneAndTexts {

    @Test
    public void testCreateCommands() {
        CharacterSceneMatch scene = generateSceneMatchList(true, true).get(5);
        CommandMaker commandMaker = new CommandMaker(scene, mockEnglishText, Language.ENG);
        CommandScene commands = commandMaker.createCommands(0);
        CommandScene expectedCommands = new CommandScene(Arrays.asList(new Command("Mom", "To test for any ", null, 1, 5, 0, -1),
                new Command("Mom", "potential bugs.", null, 1, 5, 2, -1)));
        for (int i = 0; i < expectedCommands.size(); i++) {
            Assert.assertEquals(expectedCommands.get(i), commands.get(i));
        }
    }
}
