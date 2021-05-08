package TextTranslator;

import java.util.ArrayList;

import static TextTranslator.Library.ExtraInfo;

/**
 * A class to convert scenes back into commands
 */
@SuppressWarnings("unused")
public class CommandMaker {
    final String RETURN = "\\r";
    final String LINE_BREAK = "\\n";
    final String DIALOGUE_BREAK = "\\\\c";
    final String INCORRECT_APOSTROPHE = "’";

    private final CharacterSceneMatch scene;
    private final ArrayList<String> dump;
    private final Language language;

    /**
     * Creates a command maker object to use for generating commands based on the passed scene, array list, and
     * language
     *
     * @param scene    The scene to get the matching line number from to replace this scene
     * @param dump     The text dump unedited by our searching method
     * @param language The language of the text dump
     */
    public CommandMaker(CharacterSceneMatch scene, ArrayList<String> dump, Language language) {
        this.scene = scene;
        this.dump = dump;
        this.language = language;
    }

    /**
     * Creates a command using the permutation match's line match to find that line in the text dump array list
     * and then build a new dialogue object (which is simply a command more or less in code form) using the
     * data from the first entry in that scene and some extrapolated data from the language class.
     *
     * @param index The index of the permutation match to use
     * @return A list of dialogues representing a command for use in Minecraft
     */
    @ExtraInfo(UnitTested = true)
    public CharacterScene createCommands(int index) {
        PermutationMatch match = scene.getPermutationMatches().get(index);
        CharacterScene commands = new CharacterScene();
        String[] lines = dump.get(match.getLineMatches().get(0)).split(DIALOGUE_BREAK);
        int talkTime = 0;
        for (String line : lines) {
            commands.add(new Dialogue(
                    scene.get(match.getStart()).getSpeaker(),
                    line,
                    scene.get(match.getStart()).getColor(),
                    scene.get(match.getStart()).getMinimum(),
                    scene.get(match.getStart()).getTrigger(),
                    (int) (talkTime / (language.getLanguageInformationRate() * language.getLanguageSyllabicRate())),
                    -1
            ));
            talkTime += line.toCharArray().length;
        }
        return commands;
    }


}
