package TextTranslator.scene.dialogue;

import TextTranslator.scene.LanguagesScene;
import TextTranslator.scene.character.CharacterScene;
import TextTranslator.scene.character.CharacterSceneMatch;
import TextTranslator.scene.character.PermutationMatch;
import TextTranslator.scene.command.Command;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.TellRawText;
import TextTranslator.utils.Language;

import java.util.ArrayList;
import java.util.Arrays;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class to convert scenes back into commands
 */
@SuppressWarnings("unused")
public class DialogueMaker {
    private static final String RETURN = "\\r", LINE_BREAK = "\\n", DIALOGUE_BREAK = "\\\\c", INCORRECT_APOSTROPHE = "’",
    BLOCK_OPENING = "[", BLOCK_CLOSING = "]", SEPARATOR = ",", KEY_VALUE_SEPARATOR = ":";
    /**
     * Creates a command using the permutation match's line match to find that line in the text dump array list
     * and then build a new dialogue object (which is simply a command more or less in code form) using the
     * data from the first entry in that scene and some extrapolated data from the language class.
     *
     * @param index The index of the permutation match to use
     * @return A list of dialogues representing a command for use in Minecraft
     */
    @ExtraInfo(UnitTested = true)
    public static CharacterScene createCommands(ArrayList<Command> commands, CharacterSceneMatch characterSceneMatch, ArrayList<String> dump, Language language, int index) {
        PermutationMatch match = characterSceneMatch.getPermutationMatches().get(index);
        CharacterScene characterScene = new CharacterScene();
        String[] lines = dump.get(match.getLineMatches().get(0)).split(DIALOGUE_BREAK);
        int count = 0;
        int calculatedTalkTime = 0;
        TargetSelector currentTargetSelector = characterSceneMatch.get(index).getMainTargetSelector();
        for (String line : lines) {
            Dialogue dialogue = new Dialogue(
                    new TellRawText(characterSceneMatch.get(index).getSpeaker(), line, characterSceneMatch.get(index).getColor()),
                    adjustTargetSelector(currentTargetSelector, line, language, calculatedTalkTime),
                    //characterSceneMatch.get(index).getMainTargetSelector(), //Remake the target selector with updated values
                    //Set an offset to determine future target selector values
                    characterSceneMatch.getRow() + count,
                    characterSceneMatch.get(index).getOriginalLine());
            dialogue.setOriginalLine(dialogue.toCommandForm());
            characterScene.add(dialogue);
            count++;
            calculatedTalkTime += line.toCharArray().length;
        }

        return characterScene;
    }

    private static TargetSelector adjustTargetSelector(TargetSelector targetSelector, String line, Language language, int calculatedTalkTime) {
        return new TargetSelector(targetSelector.getDialogueTag(), targetSelector.getDialogueTrigger(),
                targetSelector.getDialogueTriggerMin(), targetSelector.getTalkTimeMin(),
                (int) (calculatedTalkTime / (language.getLanguageInformationRate() * language.getLanguageSyllabicRate())));
    }

    private static void adjustRowNumber(ArrayList<Command> commands, int row, int rowOffset) {
        for (Command command : commands) {
            if (command.getRow() >= row)
                command.setRow(command.getRow() + rowOffset);
        }
    }


    public static CharacterScene generateLanguageTellRaws(ArrayList<Command> commands, CharacterSceneMatch characterSceneMatch, ArrayList<ArrayList<String>> dumps) {
        CharacterScene characterScene = new CharacterScene();
        for (int i = 1; i < dumps.size(); i++) {
            for (int t = 0; t < characterSceneMatch.getPermutationMatches().size(); t++)
                characterScene.addAll(DialogueMaker.createCommands(commands, characterSceneMatch, dumps.get(i), Language.values()[i], t));
        }
        return characterScene;
    }

    /**
     * Generates the start of the tellraw command
     *
     * @return the start of the tellraw command
     */
    @ExtraInfo(UnitTested = true)
    static String generateCommandStart(TargetSelector targetSelector) {
        return "tellraw @a" + generateTagPortion(targetSelector);
    }

    /**
     * Generates the player selected portion which is simply a means for Minecraft to identify the local player
     * and place his/her name in the text
     *
     * @return the player selector portion
     */
    @ExtraInfo(UnitTested = true)
    private static String generatePlayerSelectorPortion(TargetSelector targetSelector) {
        return wrapWithEntryChars(wrapWithFieldIdentifier("selector") + wrapWithFieldIdentifier("@p" + generateTagPortion(targetSelector)));
    }

    /**
     * Generates the tag portion of the command which is basically the identifiers used for the scoreboard in Minecraft
     *
     * @return the tag portion
     */
    @ExtraInfo(UnitTested = true)
    private static String generateTagPortion(TargetSelector targetSelector) {
        return BLOCK_OPENING +
                "score_DialogueTrigger_min=" + targetSelector.getDialogueTriggerMin() + SEPARATOR +
                "score_DialogueTrigger=" + targetSelector.getDialogueTrigger() + SEPARATOR +
                "tag=!Dialgoue" + targetSelector.getDialogueTag() + SEPARATOR +
                "score_TalkTime_min=" + targetSelector.getTalkTimeMin() + SEPARATOR +
                "score_TalkTime=" + targetSelector.getTalkTime() +
                BLOCK_CLOSING;
    }

    /**
     * Gets the results of the speaker entry and text entry methods and returns them as a list to wrap with blocks
     *
     * @return a list containing the speaker and text entry method results
     */
    @ExtraInfo(UnitTested = true)
    protected static ArrayList<String> getEntries(TellRawText tellRawText, TargetSelector targetSelector) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(generateSpeakerEntries(tellRawText.getSpeaker(), tellRawText.getColor())));
        list.addAll(Arrays.asList(generateTextEntries(tellRawText.getText(), targetSelector)));
        return list;
    }

    /**
     * Generates a speaker entry to be used in the command to denote who is speaking currently in the text.
     * It ensures color is added if it exists and if not leaves it out of the resulting entry
     *
     * @return the speaker entry for the speaker of the dialogue
     */
    @ExtraInfo(UnitTested = true)
    static String[] generateSpeakerEntries(String speaker, String color) {
        return (color != null) ?
                new String[]{
                        wrapWithEntryChars(getTextFieldKey() + getTextFieldValue("<")),
                        wrapWithEntryChars(getTextFieldKey() + getTextFieldValue(speaker) + SEPARATOR + generateColorField(color)),
                        wrapWithEntryChars(getTextFieldKey() + getTextFieldValue(">"))
                }
                :
                new String[]{
                        wrapWithEntryChars(getTextFieldKey() + getTextFieldValue("<")),
                        wrapWithEntryChars(getTextFieldKey() + getTextFieldValue(speaker)),
                        wrapWithEntryChars(getTextFieldKey() + getTextFieldValue(">"))
                };

    }

    /**
     * The text entries of the dialogue. Will usually be one entry unless there is a player selector found in which case
     * the size can vary based on that.
     *
     * @return the text entries of the dialogue
     */
    @ExtraInfo(UnitTested = true)
    private static String[] generateTextEntries(String text, TargetSelector targetSelector) {
        String[] strings = text.split("@s");
        String[] entries = new String[strings.length + (strings.length / 2)];
        for (int x = 0, y = 1; x < strings.length + (strings.length / 2); x = x + 2, y = x + 1) {
            entries[x] = generateTextEntry(strings[x / 2]);
            if (strings.length > 1)
                entries[y] = generatePlayerSelectorPortion(targetSelector);
        }
        return entries;
    }

    /**
     * Generates a text entry with the appropriate wrapper tags
     *
     * @param text the text to wrap and create an entry for
     * @return the wrapped text as an entry
     */
    @ExtraInfo(UnitTested = true)
    static String generateTextEntry(String text) {
        return getTextFieldKey() + getTextFieldValue(text);
    }

    /**
     * Generates a color field wrapped properly with field identifiers
     *
     * @return a color field
     */
    @ExtraInfo(UnitTested = true)
    static String generateColorField(String color) {
        return wrapWithFieldIdentifier("color") + KEY_VALUE_SEPARATOR + wrapWithFieldIdentifier(color);
    }

    /**
     * Creates the key portion of a text field
     *
     * @return the key portion of a text field
     */
    @ExtraInfo(UnitTested = true)
    static String getTextFieldKey() {
        return wrapWithFieldIdentifier("text") + KEY_VALUE_SEPARATOR;
    }

    /**
     * Creates the value portion of a text field
     *
     * @param text the actual text of this section of the text in the dialogue
     * @return the text of this wrapped as a field
     */
    static String getTextFieldValue(String text) {
        return wrapWithFieldIdentifier(text);
    }

    /**
     * Wraps the entries passed in block tags to denote a block in the tellraw command
     *
     * @param entries the speaker and text entries
     * @return a block in the tellraw command
     */
    @ExtraInfo(UnitTested = true)
    static String wrapWithBlockChars(ArrayList<String> entries) {
        StringBuilder sb = new StringBuilder(BLOCK_OPENING);
        for (int i = 0; i < entries.size(); i++) {
            sb.append(entries.get(i));
            if (i == entries.size() - 1) {
                sb.append(BLOCK_CLOSING);
            } else {
                sb.append(SEPARATOR);
            }
        }
        return sb.toString();
    }

    /**
     * Wraps the entry with the entry tags
     *
     * @param entry the entry to be wrapped
     * @return the entry as a proper entry
     */
    @ExtraInfo(UnitTested = true)
    static String wrapWithEntryChars(String entry) {
        final String ENTRY_OPENING = "{", ENTRY_CLOSING = "}";
        return ENTRY_OPENING + entry + ENTRY_CLOSING;
    }

    /**
     * Wraps the field with the field identifiers
     *
     * @param field the field to be wrapped
     * @return the wrapped field
     */
    @ExtraInfo(UnitTested = true)
    static String wrapWithFieldIdentifier(String field) {
        final String FIELD_IDENTIFIER = "\"";
        return FIELD_IDENTIFIER + field + FIELD_IDENTIFIER;
    }


}
