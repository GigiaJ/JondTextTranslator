package TextTranslator.scene.command;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandSceneTest {

    @Test
    public void testGetText() {
        String command1 = "tellraw @a[score_DialogueTrigger_min=1,score_DialogueTrigger=1,tag=!Dialogue1,score_TalkTime_min=1,score_TalkTime=1]  [\"\",{\"text\":\"<\"},{\"text\":\"Test\",\"color\":\"red\"},{\"text\":\">}, {\"text\":\"This\"}]";
        String command2 = "tellraw @a[score_DialogueTrigger_min=1,score_DialogueTrigger=1,tag=!Dialogue1,score_TalkTime_min=2,score_TalkTime=2]  [\"\",{\"text\":\"<\"},{\"text\":\"Test\",\"color\":\"red\"},{\"text\":\">}, {\"text\":\"test\"}]";

        CommandScene scene = new CommandScene();
        scene.add(new Command("Test", "This", "red", "!Dialogue1", 1, 1, 1, 1, 1, command1));
        scene.add(new Command("Test", "test", "red", "!Dialogue1", 1, 1, 2, 2, 2, command2));

        ArrayList<String> expectedStrings = new ArrayList<>(Arrays.asList(
                command1,
                command2));
        for (int i = 0; i < expectedStrings.size(); i++) {
            Assert.assertEquals(expectedStrings.get(i), scene.get(i).toCommandForm());
        }
    }
}
