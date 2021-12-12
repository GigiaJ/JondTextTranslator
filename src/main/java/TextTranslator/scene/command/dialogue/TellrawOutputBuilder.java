package TextTranslator.scene.command.dialogue;

import TextTranslator.scene.command.*;
import TextTranslator.utils.Library;

import java.util.ArrayList;
import java.util.Arrays;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class to convert scenes back into commands
 */
public class TellrawOutputBuilder extends CommandOutputBuilder {

    /**
     * The singleton of this object
     */
    static TellrawOutputBuilder instance = new TellrawOutputBuilder();

    TellrawOutputBuilder(){}

    /**
     * Returns this instance of the dialogue output builder
     * @return      the singleton
     */
    @ExtraInfo(UnitTested = false)
    public static TellrawOutputBuilder getInstance() {
        return instance;
    }

    /**
     * Generates the start of the tellraw command
     *
     * @param targetSelector    the target selector for this command
     * @return the start of the tellraw command
     */
    @ExtraInfo(UnitTested = true)
    public String generateCommandStart(TargetSelector targetSelector) {
        return CommandType.TELLRAW.getCommandWord() + " " + TargetSelectorType.ALL_PLAYERS.getValue() + generateTagPortion(targetSelector);
    }


    /**
     * Gets the results of the speaker entry and text entry methods and returns them as a list to wrap with blocks
     *
     * @return a list containing the speaker and text entry method results
     */
    @ExtraInfo(UnitTested = true)
    public ArrayList<String> getEntries(Object text, TargetSelector targetSelector) {
        ArrayList<String> list = new ArrayList<>();
        TellRawText tellRawText = (TellRawText) text;
        list.addAll(Arrays.asList(generateSpeakerEntries(tellRawText.speaker(), tellRawText.color())));
        list.addAll(Arrays.asList(generateTextEntries(tellRawText.text(), targetSelector)));
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
            if (strings.length > 1 && y < entries.length)
                entries[y] = generatePlayerSelectorPortion(targetSelector);
        }
        return entries;
    }

    /**
     * Generates a color field wrapped properly with field identifiers
     *
     * @return a color field
     */
    @ExtraInfo(UnitTested = true)
    static String generateColorField(String color) {
        return wrapWithFieldIdentifier("color") + KEY_VALUE_SEPARATOR + CommandOutputBuilder.wrapWithFieldIdentifier(color);
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
     * Generates a text entry with the appropriate wrapper tags
     *
     * @param text the text to wrap and create an entry for
     * @return the wrapped text as an entry
     */
    @Library.ExtraInfo(UnitTested = true)
    static String generateTextEntry(String text) {
        return getTextFieldKey() + getTextFieldValue(text);
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
}
