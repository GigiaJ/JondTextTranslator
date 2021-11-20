package TextTranslator.scene.commands;

import org.junit.Assert;
import org.junit.Test;

public class CommandFactoryTest extends CommandFactory {

    @Test
    public void testCreate() {
        Assert.fail();
    }

    @Test
    public void testIdentifyCommandType() {
        String mockLine = "tellraw @a[x=493,y=107,z=1549,dx=18,dy=5,dz=19,score_DialogueTrigger_min=1,score_DialogueTrigger=1,tag=!Dialogue1,score_TalkTime_min=20,score_TalkTime=20] {\"text\":\"<Mom> Why don't you step out and say hello to the neighbors?\"}\n";
        CommandType expected = CommandType.TELLRAW;
        Assert.assertEquals(expected, identifyCommandType(mockLine));
    }
}
