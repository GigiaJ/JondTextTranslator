package TextTranslator;

import org.junit.Assert;
import org.junit.Test;

public class DialogueLoaderTest extends DialogueLoader {
    //Double the number of quotations. Due to how the command lines are parsed the quotation marks are repeated
    //This means "text" becomes ""text""
    final String commandLine = "tellraw @a[score_DialogueTrigger_min=10,score_DialogueTrigger=10,tag=!Dialogue10,score_TalkTime_min=12,score_TalkTime=12] {\"\"text\"\":\"\"<...> That PokÃ©mon you\\u2019ve got there looks pretty happy. You must be a good Trainer.\"\"}";

    @Test
    public void testFindText() {
        String expected = "<...> That PokÃ©mon you\\u2019ve got there looks pretty happy. You must be a good Trainer.";
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
}
