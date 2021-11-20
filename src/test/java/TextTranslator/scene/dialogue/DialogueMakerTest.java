package TextTranslator.scene.dialogue;

import TextTranslator.scene.MockSceneAndTexts;
import TextTranslator.scene.character.CharacterSceneMatch;
import TextTranslator.utils.Language;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class DialogueMakerTest implements MockSceneAndTexts {

    @Test
    public void testCreateCommands() {
        CharacterSceneMatch scene = generateSceneMatchList(true, true).get(5);
        DialogueMaker commandMaker = new DialogueMaker(scene, mockEnglishText, Language.ENG);
        DialogueScene commands = commandMaker.createCommands(0);
        DialogueScene expectedCommands = new DialogueScene(Arrays.asList(new Dialogue("Mom", "To test for any ", null, "!Dialogue1", 1, 1, 1, 1, 1, null),
                new Dialogue("Mom", "potential bugs.", null, "!Dialogue1", 1, 1, 2, 2, 2, null)));
        for (int i = 0; i < expectedCommands.size(); i++) {
            Assert.assertEquals(expectedCommands.get(i), commands.get(i));
        }
    }
}
