package TextTranslator;

import java.util.ArrayList;
import java.util.Arrays;

public interface MockSceneAndTexts {
    String[][] map = new String[][]{
            {"This", String.valueOf(1), "", "", ""},
            {"is", String.valueOf(2), "", "", ""},
            {"a", String.valueOf(3), "", "", ""},
            {"bunch", String.valueOf(4), "", "", ""},
            {"of", String.valueOf(5), "", "", ""},
            {"strings", String.valueOf(6), "", "", ""},
            {"for testing purposes.", String.valueOf(7), "", "", ""},
            {"To test for any \\cpotential bugs.", String.valueOf(8), "", "", ""}
    };

    ArrayList<String> mockEnglishText = new ArrayList<>(Arrays.asList(
            "This",
            "is",
            "a",
            "bunch of strings",
            "for testing purposes.",
            "To test for any \\cpotential bugs."));

    ArrayList<String> mockAltText = new ArrayList<>(Arrays.asList(
            "This",
            "is",
            "a",
            "bunch of strings",
            "for testing purposes.",
            "To test for any \\cpotential bugs."));

    /**
     * Generates a mock scene match list with entry data included based on the booleans passed
     *
     * @param includePermutationMatch whether to generate permutation matches for the objects
     * @param includeLineMatch        whether to generate line matches for the permutation matches
     * @return a generated mock list to use for testing
     */
    default ArrayList<CharacterSceneMatch> generateSceneMatchList(boolean includePermutationMatch, boolean includeLineMatch) {
        ArrayList<CharacterSceneMatch> sceneMatchArrayList = new ArrayList<>();
        for (int i = 1; i <= mockEnglishText.size(); i++)
            sceneMatchArrayList.add(new CharacterSceneMatch(new CharacterScene()));
        sceneMatchArrayList.get(0).add(new Dialogue("Mom", "This", null, 1, 1, 1, 1));
        if (includePermutationMatch)
            sceneMatchArrayList.get(0).addPermutationMatch(new PermutationMatch("This", 0, 0, new CharacterScene(sceneMatchArrayList.get(0))));
        if (includeLineMatch)
            sceneMatchArrayList.get(0).getPermutationMatches().get(0).addLineMatch(0);

        sceneMatchArrayList.get(1).add(new Dialogue("Mom", "is", null, 1, 2, 1, 2));
        if (includePermutationMatch)
            sceneMatchArrayList.get(1).addPermutationMatch(new PermutationMatch("is", 0, 0, new CharacterScene(sceneMatchArrayList.get(1))));
        if (includeLineMatch)
            sceneMatchArrayList.get(1).getPermutationMatches().get(0).addLineMatch(1);

        sceneMatchArrayList.get(2).add(new Dialogue("Mom", "a", null, 1, 3, 1, 3));
        if (includePermutationMatch)
            sceneMatchArrayList.get(2).addPermutationMatch(new PermutationMatch("a", 0, 0, new CharacterScene(sceneMatchArrayList.get(2))));
        if (includeLineMatch)
            sceneMatchArrayList.get(2).getPermutationMatches().get(0).addLineMatch(2);

        sceneMatchArrayList.get(3).add(new Dialogue("Mom", "bunch", null, 1, 4, 1, 4));
        sceneMatchArrayList.get(3).add(new Dialogue("Mom", "of", null, 1, 4, 10, 5));
        sceneMatchArrayList.get(3).add(new Dialogue("Mom", "strings", null, 1, 4, 20, 6));
        if (includePermutationMatch)
            sceneMatchArrayList.get(3).addPermutationMatch(new PermutationMatch("bunch of strings", 0, 2, new CharacterScene(sceneMatchArrayList.get(3))));
        if (includeLineMatch)
            sceneMatchArrayList.get(3).getPermutationMatches().get(0).addLineMatch(3);

        sceneMatchArrayList.get(4).add(new Dialogue("Mom", "for testing purposes.", null, 1, 5, 1, 7));
        if (includePermutationMatch)
            sceneMatchArrayList.get(4).addPermutationMatch(new PermutationMatch("for testing purposes.", 0, 0, new CharacterScene(sceneMatchArrayList.get(4))));
        if (includeLineMatch)
            sceneMatchArrayList.get(4).getPermutationMatches().get(0).addLineMatch(4);

        sceneMatchArrayList.get(5).add(new Dialogue("Mom", "To test for any \\cpotential bugs.", null, 1, 5, 20, 8));
        if (includePermutationMatch)
            sceneMatchArrayList.get(5).addPermutationMatch(new PermutationMatch("To test for any \\cpotential bugs.", 0, 0, new CharacterScene(sceneMatchArrayList.get(5))));
        if (includeLineMatch)
            sceneMatchArrayList.get(5).getPermutationMatches().get(0).addLineMatch(5);

        return sceneMatchArrayList;
    }

    /**
     * Generates a copy of the mockMap
     *
     * @return a copy of the mockMap
     */
    default String[][] generateMap() {
        String[][] mockMap = new String[map.length][map[0].length];
        System.arraycopy(map, 0, mockMap, 0, map.length);
        return mockMap;
    }
}
