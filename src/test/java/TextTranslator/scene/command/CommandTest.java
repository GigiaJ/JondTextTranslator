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
        Assert.fail();
    }

    @Test
    public void testGenerateTextEntries() {
        Assert.fail();
    }

    @Test
    public void testGenerateTextEntry() {
        Assert.fail();
    }

    @Test
    public void testGenerateColorField() {
        String expected = "\"color:" + this.getColor() + "\"";
        Assert.assertEquals(expected, generateColorField());
    }

    @Test
    public void testTextFieldKey() {
        Assert.fail();
    }

    @Test
    public void testTextFieldValue() {
        Assert.fail();
    }

    @Test
    public void testWrapWithBlockChars() {
        Assert.fail();
    }

    @Test
    public void testWrapWithEntryChars() {
        Assert.fail();
    }

    @Test
    public void testWrapWithFieldIdentifier() {
        Assert.fail();
    }


}
