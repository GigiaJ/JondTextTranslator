package TextTranslator.scene.command;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandSceneTest {
    @Test
    public void testGetText() {
        ArrayList<String> expectedStrings = new ArrayList<String>(Arrays.asList(
                "tellraw @a[score_DialogueTrigger_min=1,score_DialogueTrigger=1,tag=!Dialogue1,score_TalkTime_min=1,score_TalkTime=1]  [\"\",{\"text\":\"<\"},{\"text\":\"Test\",\"color\":\"red\"},{\"text\":\">}, {\"text\":\"This\"}]",
                "tellraw @a[score_DialogueTrigger_min=1,score_DialogueTrigger=1,tag=!Dialogue1,score_TalkTime_min=2,score_TalkTime=2]  [\"\",{\"text\":\"<\"},{\"text\":\"Test\",\"color\":\"red\"},{\"text\":\">}, {\"text\":\"test\"}]"));
        for (int i = 0; i < expectedStrings.size(); i++) {
            Assert.assertEquals(expectedStrings.get(i), //CommandArrayEntry//);
        }
    }
}
