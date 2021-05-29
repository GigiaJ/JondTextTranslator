package TextTranslator.scene.command;

import org.junit.Assert;
import org.junit.Test;

public class CommandTest extends Command {

    public CommandTest() {
        super("", "", "", 0, 0, 0, 0);
    }

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
        //String entry = "{\"text\":\"<\"},{\"text\":\"Sina\",\"color\":\"red\"},{\"text\":\"> You there!\"}";
        //Generate command here?
        String[] expected = {"{\"text\":\"<\"}", "{\"text\":\"Sina\",\"color\":\"red\"}", "{\"text\":\"> You there!\"}"};
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], generateSpeakerEntries()[i]);
        }
    }

    @Test
    public void testGenerateTextEntries() {
        Assert.fail();
    }

    @Test
    public void testGenerateTextEntry() {
        String expected = "\"text\":\"test\"";
        Assert.assertEquals(expected, generateTextEntry("test"));
    }

    @Test
    public void testGenerateColorField() {
        String expected = "\"color:" + this.getColor() + "\"";
        Assert.assertEquals(expected, generateColorField());
    }

    @Test
    public void testGetTextFieldKey() {
        String expected = "\"text\"";
        Assert.assertEquals(expected, getTextFieldKey());
    }

    @Test
    public void testGetTextFieldValue() {
        String expected = "\"test\"";
        Assert.assertEquals(expected, getTextFieldValue("test"));
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
        String expected = "\"test\"";
        Assert.assertEquals(expected, wrapWithFieldIdentifier("test"));
    }


}
