package TextTranslator.scene.dialogue;

import TextTranslator.scene.MockSceneAndTexts;
import TextTranslator.scene.character.CharacterSceneMatch;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.TellRawText;
import TextTranslator.utils.Language;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class DialogueMakerTest implements MockSceneAndTexts {

    @Test
    public void testCreateCommands() {
        Assert.fail();
        /*
        CharacterSceneMatch scene = generateSceneMatchList(true, true).get(5);
        DialogueMaker commandMaker = new DialogueMaker(scene, mockEnglishText, Language.ENG);
        DialogueScene commands = commandMaker.createCommands(0);
        DialogueScene expectedCommands = new DialogueScene(Arrays.asList(new Dialogue(
                new TellRawText("Mom", "To test for any ", null),
                        new TargetSelector("!Dialogue1", 1, 1, 1, 1),
                        1, null),
                new Dialogue(new TellRawText("Mom", "potential bugs.", null),
                        new TargetSelector("!Dialogue1", 1, 1, 2, 2),
                        2, null)));
        for (int i = 0; i < expectedCommands.size(); i++) {
            Assert.assertEquals(expectedCommands.get(i), commands.get(i));
        }

         */
    }



    @Test
    public void testGenerateCommandStart() {
        Assert.fail();
    }

    @Test
    public void testGeneratePlayerSelectorPortion() {
        Assert.fail();
    }

    @Test
    public void testGenerateTagPortion() {
        Assert.fail();
    }

    @Test
    public void testGetEntries() {
        Assert.fail();
    }

    @Test
    public void testGenerateSpeakerEntries() {
        Dialogue command = generateCommand();
        String[] expected = {"{\"text\":\"<\"}", "{\"text\":\"Mom\",\"color\":\"red\"}", "{\"text\":\"> This is a string.\"}"};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], DialogueMaker.generateSpeakerEntries(command.getSpeaker(), command.getColor())[i]);
        }
    }

    @Test
    public void testGenerateTextEntries() {
        Assert.fail();
    }

    @Test
    public void testGenerateTextEntry() {
        Dialogue command = generateCommand();
        String expected = "\"text\":\"test\"";
        Assert.assertEquals(expected, DialogueMaker.generateTextEntry("test"));
    }

    @Test
    public void testGenerateColorField() {
        Dialogue command = generateCommand();
        String expected = "\"color:" + command.getColor() + "\"";
        Assert.assertEquals(expected, DialogueMaker.generateColorField(command.getColor()));
    }

    @Test
    public void testGetTextFieldKey() {
        Dialogue command = generateCommand();
        String expected = "\"text\"";
        Assert.assertEquals(expected, DialogueMaker.getTextFieldKey());
    }

    @Test
    public void testGetTextFieldValue() {
        Dialogue command = generateCommand();
        String expected = "\"test\"";
        Assert.assertEquals(expected, DialogueMaker.getTextFieldValue("test"));
    }

    @Test
    public void testWrapWithBlockChars() {
        Assert.fail();
    }

    @Test
    public void testWrapWithEntryChars() {
        String expected = "{\"text\":\"test\"}";
        Assert.assertEquals(expected, "\"text\":\"test\"");
    }

    @Test
    public void testWrapWithFieldIdentifier() {
        Dialogue command = generateCommand();
        String expected = "\"test\"";
        Assert.assertEquals(expected, DialogueMaker.wrapWithFieldIdentifier("test"));
    }


    private Dialogue generateCommand() {
        return new Dialogue(
                new TellRawText("Mom", "This is a string.", "red"),
                new TargetSelector("dialogue1", 1, 1, 1, 1),
                1,
                "tellraw @a[score_DialogueTrigger_min=1,score_DialogueTrigger=1,tag=!Dialogue1,score_TalkTime_min=1,score_TalkTime=1] " +
                        "[\"\",{\"text\":\"<\"},{\"text\":\"Mom\",\"color\":\"red\"},{\"text\":\"> This is a string.\"}]");
    }
}
