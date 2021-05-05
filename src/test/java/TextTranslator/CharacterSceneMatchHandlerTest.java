package TextTranslator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CharacterSceneMatchHandlerTest extends CharacterSceneMatchHandler {


    private final String[][] map = new String[][]{
            {"This", String.valueOf(1), "", "", ""},
            {"is", String.valueOf(2), "", "", ""},
            {"a", String.valueOf(3), "", "", ""},
            {"bunch", String.valueOf(4), "", "", ""},
            {"of", String.valueOf(5), "", "", ""},
            {"strings", String.valueOf(6), "", "", ""},
            {"for testing purposes.", String.valueOf(7), "", "", ""},
            {"To test for any potential bugs.", String.valueOf(8), "", "", ""}
    };

    private final ArrayList<String> mockEnglishText = new ArrayList<>(Arrays.asList(
            "This",
            "is",
            "a",
            "bunch of strings",
            "for testing purposes.",
            "To test for any potential bugs."));

    private final ArrayList<String> mockAltText = new ArrayList<>(Arrays.asList(
            "This",
            "is",
            "a",
            "bunch of strings",
            "for testing purposes.",
            "To test for any potential bugs."));


    @Test
    public void testTranslate() {
        String[][] mockMap = generateMap();
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, true);

        translate(mockMap, sceneMatchArrayList, mockEnglishText, new ArrayList[]{mockAltText}, true);
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
        setLineMatchText(mockEnglish, extraLanguages, mockEnglishText, new ArrayList[]{mockAltText}, mockPermutationMatch);

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
        mockScene.add(new Dialogue("Mom", "This", 1, 1, 1, 1));
        mockScene.add(new Dialogue("Mom", "is", 1, 1, 15, 2));
        mockScene.add(new Dialogue("Mom", "a", 1, 1, 27, 3));
        Assert.assertFalse(getMatchingLines(mockScene, mockEnglishText, true).getPermutationMatches().isEmpty());
    }

    @Test
    public void testAddMatchingLine() {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = generateSceneMatchList(true, false);
        PermutationMatch mockPermutationMatch = sceneMatchArrayList.get(5).getPermutationMatches().get(0);
        addMatchingLines(mockPermutationMatch, mockEnglishText, true);
        Assert.assertTrue(mockPermutationMatch.hasLineMatches());
    }

    /**
     * Generates a mock scene match list with entry data included based on the booleans passed
     *
     * @param includePermutationMatch whether to generate permutation matches for the objects
     * @param includeLineMatch        whether to generate line matches for the permutation matches
     * @return a generated mock list to use for testing
     */
    private ArrayList<CharacterSceneMatch> generateSceneMatchList(boolean includePermutationMatch, boolean includeLineMatch) {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = new ArrayList<>();
        for (int i = 1; i <= mockEnglishText.size(); i++)
            sceneMatchArrayList.add(new CharacterSceneMatch(new CharacterScene()));
        sceneMatchArrayList.get(0).add(new Dialogue("Mom", "This", 1, 1, 1, 1));
        if (includePermutationMatch)
            sceneMatchArrayList.get(0).addPermutationMatch(new PermutationMatch("This", 0, 0, new CharacterScene(sceneMatchArrayList.get(0))));
        if (includeLineMatch)
            sceneMatchArrayList.get(0).getPermutationMatches().get(0).addLineMatch(0);

        sceneMatchArrayList.get(1).add(new Dialogue("Mom", "is", 1, 2, 1, 2));
        if (includePermutationMatch)
            sceneMatchArrayList.get(1).addPermutationMatch(new PermutationMatch("is", 0, 0, new CharacterScene(sceneMatchArrayList.get(1))));
        if (includeLineMatch)
            sceneMatchArrayList.get(1).getPermutationMatches().get(0).addLineMatch(1);

        sceneMatchArrayList.get(2).add(new Dialogue("Mom", "a", 1, 3, 1, 3));
        if (includePermutationMatch)
            sceneMatchArrayList.get(2).addPermutationMatch(new PermutationMatch("a", 0, 0, new CharacterScene(sceneMatchArrayList.get(2))));
        if (includeLineMatch)
            sceneMatchArrayList.get(2).getPermutationMatches().get(0).addLineMatch(2);

        sceneMatchArrayList.get(3).add(new Dialogue("Mom", "bunch", 1, 4, 1, 4));
        sceneMatchArrayList.get(3).add(new Dialogue("Mom", "of", 1, 4, 10, 5));
        sceneMatchArrayList.get(3).add(new Dialogue("Mom", "strings", 1, 4, 20, 6));
        if (includePermutationMatch)
            sceneMatchArrayList.get(3).addPermutationMatch(new PermutationMatch("bunch of strings", 0, 2, new CharacterScene(sceneMatchArrayList.get(3))));
        if (includeLineMatch)
            sceneMatchArrayList.get(3).getPermutationMatches().get(0).addLineMatch(3);

        sceneMatchArrayList.get(4).add(new Dialogue("Mom", "for testing purposes.", 1, 5, 1, 7));
        if (includePermutationMatch)
            sceneMatchArrayList.get(4).addPermutationMatch(new PermutationMatch("for testing purposes.", 0, 0, new CharacterScene(sceneMatchArrayList.get(4))));
        if (includeLineMatch)
            sceneMatchArrayList.get(4).getPermutationMatches().get(0).addLineMatch(4);

        sceneMatchArrayList.get(5).add(new Dialogue("Mom", "To test for any potential bugs.", 1, 5, 20, 8));
        if (includePermutationMatch)
            sceneMatchArrayList.get(5).addPermutationMatch(new PermutationMatch("To test for any potential bugs.", 0, 0, new CharacterScene(sceneMatchArrayList.get(5))));
        if (includeLineMatch)
            sceneMatchArrayList.get(5).getPermutationMatches().get(0).addLineMatch(5);

        return sceneMatchArrayList;
    }

    /**
     * Generates a copy of the mockMap
     *
     * @return a copy of the mockMap
     */
    private String[][] generateMap() {
        String[][] mockMap = new String[map.length][map[0].length];
        System.arraycopy(map, 0, mockMap, 0, map.length);
        return mockMap;
    }
}
