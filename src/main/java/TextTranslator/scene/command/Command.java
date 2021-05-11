package TextTranslator.scene.command;

import TextTranslator.scene.character.Dialogue;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

import static TextTranslator.utils.Library.ExtraInfo;

public class Command extends Dialogue {

    private final String BLOCK_OPENING = "[", BLOCK_CLOSING = "]", SEPARATOR = ",", KEY_VALUE_SEPARATOR = ":";

    /**
     * Generates a new command object with all of the fields in this class as parameters.
     */
    public Command(@NonNull String speaker, String text, String color, int minimum, int trigger, int talkTime, int row) {
        super(speaker, text, color, minimum, trigger, talkTime, row);
    }

    /**
     * Converts the dialogue to its minecraft command form
     *
     * @return the command form of this dialogue
     */
    @ExtraInfo(UnitTested = true)
    public String toCommandForm() {
        return generateCommandStart() + wrapWithBlockChars(getEntries());
    }


    /**
     * Generates the start of the tellraw command
     *
     * @return the start of the tellraw command
     */
    @ExtraInfo(UnitTested = true)
    private String generateCommandStart() {
        return "tellraw @a" + generateTagPortion();
    }

    /**
     * Generates the player selected portion which is simply a means for Minecraft to identify the local player
     * and place his/her name in the text
     *
     * @return the player selector portion
     */
    @ExtraInfo(UnitTested = true)
    private String generatePlayerSelectorPortion() {
        return wrapWithEntryChars(wrapWithFieldIdentifier("selector") + wrapWithFieldIdentifier("@p" + generateTagPortion()));
    }

    /**
     * Generates the tag portion of the command which is basically the identifiers used for the scoreboard in Minecraft
     *
     * @return the tag portion
     */
    @ExtraInfo(UnitTested = true)
    private String generateTagPortion() {
        return BLOCK_OPENING +
                "score_DialogueTrigger_min=" + getMinimum() + SEPARATOR +
                "score_DialogueTrigger=" + getTrigger() + SEPARATOR +
                "tag=!Dialgoue" + getTrigger() + SEPARATOR +
                "score_TalkTime_min=" + getTalkTime() + SEPARATOR +
                "score_TalkTime=" + getTalkTime() +
                BLOCK_CLOSING;
    }

    /**
     * Gets the results of the speaker entry and text entry methods and returns them as a list to wrap with blocks
     *
     * @return a list containing the speaker and text entry method results
     */
    @ExtraInfo(UnitTested = true)
    private ArrayList<String> getEntries() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(generateSpeakerEntries()));
        list.addAll(Arrays.asList(generateTextEntries()));
        return list;
    }

    /**
     * Generates a speaker entry to be used in the command to denote who is speaking currently in the text.
     * It ensures color is added if it exists and if not leaves it out of the resulting entry
     *
     * @return the speaker entry for the speaker of the dialogue
     */
    @ExtraInfo(UnitTested = true)
    private String[] generateSpeakerEntries() {
        return (getColor() != null) ?
                new String[]{
                        wrapWithEntryChars(textFieldKey() + textFieldValue("<")),
                        wrapWithEntryChars(textFieldKey() + textFieldValue(getSpeaker()) + SEPARATOR + generateColorField()),
                        wrapWithEntryChars(textFieldKey() + textFieldValue(">"))
                }
                :
                new String[]{
                        wrapWithEntryChars(textFieldKey() + textFieldValue("<")),
                        wrapWithEntryChars(textFieldKey() + textFieldValue(getSpeaker())),
                        wrapWithEntryChars(textFieldKey() + textFieldValue(">"))
                };

    }

    /**
     * The text entries of the dialogue. Will usually be one entry unless there is a player selector found in which case
     * the size can vary based on that.
     *
     * @return the text entries of the dialogue
     */
    @ExtraInfo(UnitTested = true)
    private String[] generateTextEntries() {
        String[] strings = getText().split("@s");
        String[] entries = new String[strings.length + (strings.length / 2)];
        for (int x = 0, y = 0; x < strings.length + (strings.length / 2); x = x + 2, y = x + 1) {
            entries[x] = generateTextEntry(strings[x / 2]);
            entries[y] = generatePlayerSelectorPortion();
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
    private String generateTextEntry(String text) {
        return textFieldKey() + textFieldValue(text);
    }

    /**
     * Generates a color field wrapped properly with field identifiers
     *
     * @return a color field
     */
    @ExtraInfo(UnitTested = true)
    private String generateColorField() {
        return wrapWithFieldIdentifier("color") + KEY_VALUE_SEPARATOR + wrapWithFieldIdentifier(getColor());
    }

    /**
     * Creates the key portion of a text field
     *
     * @return the key portion of a text field
     */
    @ExtraInfo(UnitTested = true)
    private String textFieldKey() {
        return wrapWithFieldIdentifier("text") + KEY_VALUE_SEPARATOR;
    }

    /**
     * Creates the value portion of a text field
     *
     * @param text the actual text of this section of the text in the dialogue
     * @return the text of this wrapped as a field
     */
    private String textFieldValue(String text) {
        return wrapWithFieldIdentifier(text);
    }

    /**
     * Wraps the entries passed in block tags to denote a block in the tellraw command
     *
     * @param entries the speaker and text entries
     * @return a block in the tellraw command
     */
    @ExtraInfo(UnitTested = true)
    private String wrapWithBlockChars(ArrayList<String> entries) {
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
    private String wrapWithEntryChars(String entry) {
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
    private String wrapWithFieldIdentifier(String field) {
        final String FIELD_IDENTIFIER = "\"";
        return FIELD_IDENTIFIER + field + FIELD_IDENTIFIER;
    }
}
