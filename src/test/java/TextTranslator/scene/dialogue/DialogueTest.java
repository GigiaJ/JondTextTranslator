package TextTranslator.scene.dialogue;

import org.junit.Assert;
import org.junit.Test;

public class DialogueTest {

    @Test
    public void testToCommandForm() {
        Assert.fail();
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
            Assert.assertEquals(expected[i], command.generateSpeakerEntries()[i]);
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
        Assert.assertEquals(expected, command.generateTextEntry("test"));
    }

    @Test
    public void testGenerateColorField() {
        Dialogue command = generateCommand();
        String expected = "\"color:" + command.getColor() + "\"";
        Assert.assertEquals(expected, command.generateColorField());
    }

    @Test
    public void testGetTextFieldKey() {
        Dialogue command = generateCommand();
        String expected = "\"text\"";
        Assert.assertEquals(expected, command.getTextFieldKey());
    }

    @Test
    public void testGetTextFieldValue() {
        Dialogue command = generateCommand();
        String expected = "\"test\"";
        Assert.assertEquals(expected, command.getTextFieldValue("test"));
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
        Assert.assertEquals(expected, command.wrapWithFieldIdentifier("test"));
    }


    private Dialogue generateCommand() {
        return new Dialogue("Mom", "This is a string.", "red", "dialogue1", 1, 1, 1, 1, 1,
                "tellraw @a[score_DialogueTrigger_min=1,score_DialogueTrigger=1,tag=!Dialogue1,score_TalkTime_min=1,score_TalkTime=1] " +
                        "[\"\",{\"text\":\"<\"},{\"text\":\"Mom\",\"color\":\"red\"},{\"text\":\"> This is a string.\"}]");
    }
}
