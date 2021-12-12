package TextTranslator.scene.character;

import TextTranslator.scene.command.dialogue.TellRaw;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CharacterSceneHandlerTest extends CharacterSceneHandler {

    @Test
    public void testPlaceCommands() {
        ArrayList<CharacterSceneMatch> mockScenes = new ArrayList<>(Arrays.asList(
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene())));

        for (int i = 1; i < mockScenes.size() + 1; i++)
            mockScenes.get(i - 1).add(new TellRaw("Mom", "Sample Text", "red", null, i, i, i, i, i));

        String[][] expectedCommandOutput = new String[4][5];
        for (int i = 1; i <= expectedCommandOutput.length; i++)
            expectedCommandOutput[i - 1] = new String[]{"Sample Text", String.valueOf(i), "", "", ""};
        String[][] testOutput = placeCommands(new String[4][5], mockScenes);
        Assert.assertArrayEquals(expectedCommandOutput, testOutput);
    }

    @Test
    public void testIterateThroughAllScenesAndDialogueUntilMatch() {
        final int EXPECTED_ROW = 3;
        ArrayList<CharacterSceneMatch> mockScenes = new ArrayList<>(Arrays.asList(
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene())));

        for (CharacterSceneMatch scene : mockScenes) {
            for (int i = 1; i <= EXPECTED_ROW + 1; i++) {
                scene.add(new TellRaw("Mom", "Sample Text", "red", null, i, i, i, i, i));
            }
        }

        TellRaw toCheck = iterateThroughAllScenesAndDialogueUntilMatch(mockScenes, row -> (row == EXPECTED_ROW), TellRaw::getRow);

        Assert.assertEquals(EXPECTED_ROW, toCheck.getRow());
    }

    @Test
    public void testIterateThroughScene() {
        final int EXPECTED_ROW = 3;
        CharacterSceneMatch mockScene = new CharacterSceneMatch(new CharacterScene());

        for (int i = 1; i <= EXPECTED_ROW + 1; i++) {
            mockScene.add(new TellRaw("Mom", "Sample Text", "red", null, i, i, i, i, i));
        }

        TellRaw toCheck = iterateThroughScene(mockScene, row -> (row == EXPECTED_ROW), TellRaw::getRow);
        if (toCheck != null) {
            Assert.assertEquals(EXPECTED_ROW, toCheck.getRow());
        } else {
            Assert.fail("To check is null. Uncertain how this may occur, but in theory it could be null.");
        }
    }

    @Test
    public void testEnterRowData() {
        final int OUTPUT_SIZE = 4;
        String[][] mockOutput = new String[OUTPUT_SIZE][5];
        TellRaw mockDialogue = new TellRaw("Speaker", "Random Text", "Red", null, 2, 2, 2, 10, OUTPUT_SIZE);
        String[] expectedEntry = {mockDialogue.getText(), String.valueOf(mockDialogue.getRow()), "", "", ""};
        for (int i = 1; i <= mockOutput.length - 1; i++)
            mockOutput[i - 1] = new String[]{"Sample Text", String.valueOf(i), "", "", ""};
        enterRowData(mockOutput, mockOutput.length - 1, mockDialogue);

        for (int i = 0; i < expectedEntry.length; i++) {
            Assert.assertEquals(mockOutput[mockOutput.length - 1][i], expectedEntry[i]);
        }

    }

    @Test
    public void testAssignDialogueToScene() {
        TellRaw dialogue1 = new TellRaw("Mom", "This is a text", "red", null, 3, 3, 3, 3, 31),
                dialogue2 =
                        new TellRaw("Mom", "This is a text also", "red", null, 3, 3, 3, 3, 35),
                dialogue3 =
                        new TellRaw("Mom", "This is a text also", "red", null, 3, 3, 3, 3, 39),
                dialogue4 =
                        new TellRaw("Dad", "This is a text also", "red", null, 3, 3, 3, 3, 50),
                dialogue5 =
                        new TellRaw("Mom", "This is a text also", "red", null, 3, 3, 10, 3, 10),
                dialogue6 =
                        new TellRaw("Mom", "This is a text also", "red", null, 3, 3, 10, 3, 15);

        ArrayList<TellRaw> mockDialogueList = new ArrayList<>(
                Arrays.asList(dialogue1, dialogue2, dialogue3, dialogue4, dialogue5, dialogue6));

        ArrayList<CharacterSceneMatch> expectedList = new ArrayList<>(Arrays.asList(
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene()),
                new CharacterSceneMatch(new CharacterScene())
        ));

        expectedList.get(0).getScene().addAll(Arrays.asList(dialogue1, dialogue2, dialogue3));
        expectedList.get(1).getScene().add(dialogue4);
        expectedList.get(2).getScene().addAll(Arrays.asList(dialogue5, dialogue6));

        ArrayList<CharacterSceneMatch> sceneList = assignDialogueToScene(mockDialogueList);

        for (int x = 0; x < expectedList.size(); x++) {
            for (int y = 0; y < expectedList.get(x).size(); y++) {
                Assert.assertEquals(expectedList.get(x).get(y), sceneList.get(x).get(y));
            }
        }
    }

    @Test
    public void testCurrentSceneIsEmpty() {
        Object[] mockValues = {"CurrentSpeaker", 2, 10};
        Assert.assertFalse(currentSceneIsEmpty(mockValues));
    }

    @Test
    public void testSetSceneToCurrent() {
        Object[] mockValues = {"CurrentSpeaker", 2, 10};
        Object[] expectedValues = {"DifferentSpeaker", 3, 15};
        TellRaw mockDialogue = new TellRaw("DifferentSpeaker", "Random Text", "red", null, 2, 2, 3, 15, 5);
        setSceneToCurrent(mockValues, mockDialogue);
        Assert.assertArrayEquals(mockValues, expectedValues);
    }

    @Test
    public void testIsNotCurrentScene() {
        String mockSpeaker = "Speaker";
        int mockTrigger = 2;
        int mockTalkTime = 10;
        Object[] mockValues = {mockSpeaker, mockTrigger, mockTalkTime};
        TellRaw mockDialogue = new TellRaw(mockSpeaker, "Random Text", "red", null, 2, 2, mockTrigger, mockTalkTime, 5, "");
        boolean checkScene = isNotCurrentScene(mockValues, mockDialogue);
        Assert.assertFalse(checkScene);
    }
}
