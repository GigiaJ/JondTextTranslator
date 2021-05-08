package TextTranslator;

import org.junit.Assert;
import org.junit.Test;

public class DialogueLoaderTest extends DialogueLoader {
    //Double the number of quotations. Due to how the command lines are parsed the quotation marks are repeated
    //This means "text" becomes ""text""
    final String commandLine = "tellraw @a[score_DialogueTrigger_min=97,score_DialogueTrigger=97,tag=!Dialogue97,score_TalkTime_min=5,score_TalkTime=5] [\"\"\"\",{\"\"text\"\":\"\"<\"\"},{\"\"text\"\":\"\"Team Flare Grunt\"\",\"\"color\"\":\"\"red\"\"},{\"\"text\"\":\"\"> I may have lost... I may have lost, but... Isn\\u2019t this winter wonderland beautiful?\"\"}]";

    @Test
    public void testFindText() {
        String expected = "<Team Flare Grunt> I may have lost... I may have lost, but... Isn\\u2019t this winter wonderland beautiful?";
        Assert.assertEquals(expected, findText(commandLine));
    }

    @Test
    public void testFindTriggerTag() {
        Assert.assertTrue(findTriggerTag(commandLine) != -1);
    }

    @Test
    public void testFindTalkTime() {
        Assert.assertTrue(findTalkTime(commandLine) != -1);
    }

    @Test
    public void testFindSpeaker() {
        final String speakerLine = "<CharacterName> Hi this is a test phrase";
        final String expected = "CharacterName";
        Assert.assertEquals(expected, findSpeaker(speakerLine));
    }

    @Test
    public void testRemoveSpeaker() {
        final String speakerLine = "<CharacterName> Hi this is a test phrase";
        final String expected = "Hi this is a test phrase";
        Assert.assertEquals(expected, removeSpeaker(speakerLine));
    }

    @Test
    public void testFindColor() {
        final String expected = "red";
        Assert.assertEquals(expected, findColor(commandLine));
    }
}
