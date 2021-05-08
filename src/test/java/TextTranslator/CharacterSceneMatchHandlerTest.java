package TextTranslator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterSceneMatchHandlerTest extends CharacterSceneMatchHandler implements MockSceneAndTexts {


    @Test
    public void testTranslate() {
        String[][] mockMap = generateMap();
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, true);

        translate(mockMap, sceneMatchArrayList, mockEnglishText, new ArrayList<>(Collections.singleton(mockAltText)));
        Assert.assertEquals(mockMap[0][2], mockEnglishText.get(0));
        Assert.assertEquals(mockMap[1][2], mockEnglishText.get(1));
        Assert.assertEquals(mockMap[2][2], mockEnglishText.get(2));
        Assert.assertEquals(mockMap[3][2], mockEnglishText.get(3));
        Assert.assertEquals(mockMap[4][2], mockEnglishText.get(3));
        Assert.assertEquals(mockMap[5][2], mockEnglishText.get(3));
        Assert.assertEquals(mockMap[6][2], mockEnglishText.get(4));
        Assert.assertEquals(mockMap[7][2], mockEnglishText.get(5));
    }

    @Test
    public void testSetLineMatch() {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, true);

        StringBuilder mockEnglish = new StringBuilder();
        String[] extraLanguages = new String[]{""};
        PermutationMatch mockPermutationMatch = sceneMatchArrayList.get(0).getPermutationMatches().get(0);
        setLineMatchText(mockEnglish, extraLanguages, mockEnglishText, new ArrayList<>(Collections.singleton(mockAltText)), mockPermutationMatch);

        Assert.assertEquals(mockEnglish.toString(), "This");
        Assert.assertEquals(extraLanguages[0], "This");
    }

    @Test
    public void testAssignRowEntry() {
        String[][] mockMap = generateMap();
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, true);

        PermutationMatch mockPermutationMatch = sceneMatchArrayList.get(0).getPermutationMatches().get(0);
        assignRowEntry(mockMap, 0, new StringBuilder("This"), new String[]{"This"}, mockPermutationMatch);
        Assert.assertEquals(mockMap[0][4], "This");
    }

    @Test
    public void testRemoveCollisions() {
        ArrayList<CharacterSceneMatch> mockListToCheck = generateSceneMatchList(true, true);
        ArrayList<CharacterSceneMatch> mockListToEmpty = generateSceneMatchList(true, true);

        removeCollisions(mockListToCheck, mockListToEmpty, true);

        Assert.assertTrue(mockListToEmpty.isEmpty());
    }

    @Test
    public void testComparePermutationMatches() {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, true);

        CharacterSceneMatch scene1 = sceneMatchArrayList.get(0);
        CharacterSceneMatch scene2 = sceneMatchArrayList.get(1);
        CharacterSceneMatch scene3 = generateSceneMatchList(true, true).get(0);

        comparePermutationMatches(scene1, scene2);
        Assert.assertNotEquals(scene1.getTrigger(), scene2.getTrigger());
        Assert.assertNotEquals(scene1.getRow(), scene2.getRow());
        Assert.assertFalse(scene2.getPermutationMatches().isEmpty());

        comparePermutationMatches(scene1, scene3);
        Assert.assertEquals(scene1.getTrigger(), scene3.getTrigger());
        Assert.assertEquals(scene1.getRow(), scene3.getRow());
        Assert.assertTrue(scene3.getPermutationMatches().isEmpty());
    }

    @Test
    public void testRemoveLineMatch() {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, true);
        PermutationMatch pm1 = sceneMatchArrayList.get(3).getPermutationMatches().get(0);
        PermutationMatch pm2 = sceneMatchArrayList.get(4).getPermutationMatches().get(0);
        PermutationMatch pm3 = sceneMatchArrayList.get(3).getPermutationMatches().get(0);

        Assert.assertTrue(removeLineMatch(pm1, pm3));
        Assert.assertFalse(removeLineMatch(pm1, pm2));
    }

    @Test
    public void testGetAllMatchingLines() {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(false, false);
        sceneMatchArrayList = getAllMatchingLines(sceneMatchArrayList, mockEnglishText, true);
        Assert.assertTrue(sceneMatchArrayList.get(3).getPermutationMatches().get(0).hasLineMatches());
    }

    @Test
    public void testGetMatchingLines() {
        CharacterScene mockScene = new CharacterScene();
        mockScene.add(new Dialogue("Mom", "This", null, 1, 1, 1, 1));
        mockScene.add(new Dialogue("Mom", "is", null, 1, 1, 15, 2));
        mockScene.add(new Dialogue("Mom", "a", null, 1, 1, 27, 3));
        Assert.assertFalse(getMatchingLines(mockScene, mockEnglishText, true).getPermutationMatches().isEmpty());
    }

    @Test
    public void testAddMatchingLine() {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, false);
        PermutationMatch mockPermutationMatch = sceneMatchArrayList.get(5).getPermutationMatches().get(0);
        addMatchingLines(mockPermutationMatch, mockEnglishText, true);
        Assert.assertTrue(mockPermutationMatch.hasLineMatches());
    }


}
