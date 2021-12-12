package TextTranslator.scene.command;

import TextTranslator.scene.command.dialogue.TellRawText;

import java.util.ArrayList;
import java.util.Arrays;

import static TextTranslator.utils.Library.ExtraInfo;

public abstract class CommandOutputBuilder {
    protected static final String RETURN = "\\r", LINE_BREAK = "\\n", DIALOGUE_BREAK = "\\\\c", INCORRECT_APOSTROPHE = "’",
            BLOCK_OPENING = "[", BLOCK_CLOSING = "]", SEPARATOR = ",", KEY_VALUE_SEPARATOR = ":";

    /**
     * Generates the player selected portion which is simply a means for Minecraft to identify the local player
     * and place his/her name in the text
     *
     * @param targetSelector    the target selector for this command
     * @return the player selector portion
     */
    @ExtraInfo(UnitTested = true)
    protected static String generatePlayerSelectorPortion(TargetSelector targetSelector) {
        return wrapWithEntryChars(wrapWithFieldIdentifier("selector") + wrapWithFieldIdentifier(TargetSelectorType.NEAREST_PLAYER.getValue() + generateTagPortion(targetSelector)));
    }

    /**
     * Generates the tag portion of the command which is basically the identifiers used for the scoreboard in Minecraft
     *
     * @param targetSelector    the target selector for this command
     * @return the tag portion
     */
    @ExtraInfo(UnitTested = true)
    protected static String generateTagPortion(TargetSelector targetSelector) {
        return BLOCK_OPENING +
                "score_DialogueTrigger_min=" + targetSelector.dialogueTriggerMin() + SEPARATOR +
                "score_DialogueTrigger=" + targetSelector.dialogueTrigger() + SEPARATOR +
                "tag=" + targetSelector.dialogueTag() + SEPARATOR +
                "score_TalkTime_min=" + targetSelector.talkTimeMin() + SEPARATOR +
                "score_TalkTime=" + targetSelector.talkTime() +
                ((targetSelector.starterPick() != -1) ?
                        (SEPARATOR + "score_StarterPick=" + targetSelector.starterPick() + SEPARATOR) : "") +
                ((targetSelector.starterPickMin() != -1) ?
                        ("score_StarterPick_min=" + targetSelector.starterPickMin()) : "") +
                BLOCK_CLOSING;
    }

    /**
     * Wraps the field with the field identifiers
     *
     * @param field the field to be wrapped
     * @return the wrapped field
     */
    @ExtraInfo(UnitTested = true)
    protected static String wrapWithFieldIdentifier(String field) {
        final String FIELD_IDENTIFIER = "\"";
        return FIELD_IDENTIFIER + field + FIELD_IDENTIFIER;
    }

    /**
     * Wraps the entry with the entry tags
     *
     * @param entry the entry to be wrapped
     * @return the entry as a proper entry
     */
    @ExtraInfo(UnitTested = true)
    protected static String wrapWithEntryChars(String entry) {
        final String ENTRY_OPENING = "{", ENTRY_CLOSING = "}";
        return ENTRY_OPENING + entry + ENTRY_CLOSING;
    }
}
