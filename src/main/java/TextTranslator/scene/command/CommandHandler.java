package TextTranslator.scene.command;

import TextTranslator.scene.character.CharacterScene;
import TextTranslator.scene.character.PermutationMatch;
import TextTranslator.scene.command.dialogue.TellRaw;
import TextTranslator.scene.command.dialogue.TellRawText;
import TextTranslator.utils.Language;
import static TextTranslator.utils.Library.ExtraInfo;

import java.util.ArrayList;
import java.util.Objects;

import static TextTranslator.scene.command.CommandOutputBuilder.DIALOGUE_BREAK;

public class CommandHandler {
    /**
     * Creates a command using the permutation match's line match to find that line in the text dump array list
     * and then build a new dialogue object (which is simply a command more or less in code form) using the
     * data from the first entry in that scene and some extrapolated data from the language class.
     *
     * @param set       The set of commands to iterate over to return a character scene with translated text
     * @param dump      The list of dumped text associated with this scene
     * @param language  The language associated with this scene
     * @param index     The index of the permutation match to use
     * @return A list of dialogues representing a command for use in Minecraft
     */
    @ExtraInfo(UnitTested = true)
    public static CharacterScene createCommands(CommandTriggerSet set, ArrayList<String> dump, Language language, int index) {
        PermutationMatch match = set.getCharacterScene().getPermutationMatches().get(index);
        CharacterScene characterScene = new CharacterScene();
        String[] lines = dump.get(match.getLineMatches().get(0)).split(DIALOGUE_BREAK);
        TargetSelector currentTargetSelector = set.getCharacterScene().get(index).getMainTargetSelector();

        for (String line : lines) {
            TellRaw tellRaw = new TellRaw(new TellRawText(set.getCharacterScene().get(index).getSpeaker(),
                            line,
                            set.getCharacterScene().get(index).getColor()),
                    currentTargetSelector, 0,
                    set.getCharacterScene().get(index).getOriginalLine());
            characterScene.add(tellRaw);
        }

        return characterScene;
    }

    public static String bindLanguageLines(CommandTriggerSet set, ArrayList<String> dump, Language language, int index, String previousLine) {
        PermutationMatch match = set.getCharacterScene().getPermutationMatches().get(index);
        String line = dump.get(match.getLineMatches().get(0));
        if (!line.equals(previousLine)) {
            set.getCharacterScene().get(index).getLanguageData().addLine(line, language);
        }
        return line;
    }

    /*
    private static void adjustTargetSelector(Command command, Language language, int calculatedTalkTime) {
        int languageSpecificTalkTime = (int) (calculatedTalkTime / (language.getLanguageInformationRate() * language.getLanguageSyllabicRate()));
        TargetSelector targetSelector = command.getMainTargetSelector();
        command.setMainTargetSelector(new TargetSelector(targetSelector.dialogueTag(), targetSelector.dialogueTrigger(),
                targetSelector.dialogueTriggerMin(), languageSpecificTalkTime, languageSpecificTalkTime));
    }
    */


    public static void generateLanguageCommands(CommandTriggerSet set, ArrayList<ArrayList<String>> dumps) {
        for (int i = 0; i < dumps.size(); i++) {
            String previousLine = "";
            for (int t = 0; t < set.getCharacterScene().getPermutationMatches().size(); t++) {
                previousLine = bindLanguageLines(set, dumps.get(i), Language.values()[i], t, previousLine);
                appendNewScenes(set, Language.values()[i]);
            }
        }
    }

    public static void appendNewScenes(CommandTriggerSet set, Language language) {
        int talkTimeAccumulator = -1;
        for (int x = 0; x < set.getCommands().size(); x++) {
            if (set.getCommands().get(x) instanceof TellRaw) {
                int languageAdjustedTalkTime = (int) (set.getCommands().get(x).getLanguageData().getCharacterCount(language)
                        / (language.getLanguageInformationRate() * language.getLanguageSyllabicRate()));
                talkTimeAccumulator += languageAdjustedTalkTime;
            }
            else {
                int talkTime = set.getCommands().get(x).getMainTargetSelector().talkTime();
                talkTimeAccumulator += (talkTime == -1) ? 0 : talkTime;
            }
            set.getCommands().get(x).getLanguageData().setTalkTime(language,
                    talkTimeAccumulator);
            /*
            if (set.getCommands().get(x) instanceof TellRaw) {
                if (((TellRaw) set.getCommands().get(x)).getCharacterCount() != -1) {
                    calculatedTalkTime += ((TellRaw) set.getCommands().get(x)).getCharacterCount();
                }
                else {
                    if (set.getCommands().get(x).getMainTargetSelector().talkTime() == characterScene.get(0).getMainTargetSelector().talkTime()) {
                            for (int y = 0; y < characterScene.size(); y++) {
                                set.getCommands().remove(x + y);
                                calculatedTalkTime += characterScene.get(y).getCharacterCount();
                                set.getCommands().add(x + y, characterScene.get(y));
                                adjustTargetSelector(characterScene.get(y), language, calculatedTalkTime);
                            }
                            x = x + (characterScene.size() - 1);
                    }
                }
            }
            else {
                if (set.getCommands().get(x).getMainTargetSelector().talkTime() <= characterScene.get(0).getMainTargetSelector().talkTime() ||
                        set.getCommands().get(x).getMainTargetSelector().talkTimeMin() <= characterScene.get(0).getMainTargetSelector().talkTimeMin()) {
                    if (x > 0) {
                        if (set.getCommands().get(x).getMainTargetSelector().talkTimeMin() > 1) {
                            calculatedTalkTime += 7;
                            adjustTargetSelector(set.getCommands().get(x), language, calculatedTalkTime);
                        }
                    }
                }
            }

             */
        }
    }
}
