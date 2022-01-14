package TextTranslator.scene.command.dialogue;

import TextTranslator.scene.command.Command;
import TextTranslator.scene.command.CommandType;
import TextTranslator.scene.command.Pair;
import TextTranslator.scene.command.TargetSelector;
import TextTranslator.utils.Language;
import TextTranslator.utils.Library.ExtraInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * A class representing a minecraft command
 */
@Slf4j
public class TellRaw extends Command {

    @Getter
    private final String speaker, text, color;
    @Getter
    @Setter
    private int[] matchingLines;

    /**
     * Makes a new tellraw object with the values of the parent class as its own
     * @param tellRawText            the text associated with the tellraw
     * @param command                the object that this one will get its values from
     */
    public TellRaw(Command command,  TellRawText tellRawText) {
        super(command.getMainTargetSelector(), command.getRow(), command.getOriginalLine(), CommandType.TELLRAW);
        this.speaker = tellRawText.speaker();
        this.text = tellRawText.text();
        this.color = tellRawText.color();
    }

    /**
     * Generates a new dialogue object with all of the fields in this class as parameters.
     *
     * @param mainTargetSelector     the target selector for this tell raw
     * @param tellRawText            the text associated with the tellraw
     * @param row                    the row from the source of this command
     * @param originalLine           the original text for this command from the source
     */
    public TellRaw(TellRawText tellRawText, TargetSelector mainTargetSelector, int row, String originalLine) {
        super(mainTargetSelector, row, originalLine, CommandType.TELLRAW);
        this.speaker = tellRawText.speaker();
        this.text = tellRawText.text();
        this.color = tellRawText.color();
    }

    /**
     * Generates a new dialogue object with most of the fields in this class as parameters.
     *
     * @param tellRawText            the text associated with the tellraw
     * @param mainTargetSelector     the target selector for this tell raw
     * @param row                    the row from the source of this command
     */
    public TellRaw(TargetSelector mainTargetSelector, int row, TellRawText tellRawText) {
        super(mainTargetSelector, row, null, CommandType.TELLRAW);
        this.speaker = tellRawText.speaker();
        this.text = tellRawText.text();
        this.color = tellRawText.color();
    }

    /**
     * Converts the command to its minecraft command text based form
     *
     * @return the text based command form of this command
     */
    @ExtraInfo(UnitTested = true)
    @Override
    public String toCommandForm(Language language) {
        Pair<String, Integer> linePair = this.getLanguageData().getAndRemovePair(language);
        return TellrawOutputBuilder.getInstance().generateCommandStart(
                new TargetSelector(this.getMainTargetSelector().dialogueTag(),
                        this.getMainTargetSelector().dialogueTrigger(),
                        this.getMainTargetSelector().dialogueTriggerMin(),
                        linePair.b(), linePair.b())
        )
                + TellrawOutputBuilder.wrapWithBlockChars(
                TellrawOutputBuilder.getInstance().getEntries(new TellRawText(this.getSpeaker(), linePair.a(), this.getColor()),
                        this.getMainTargetSelector()));
    }

    public String toString() {
        return getText();
    }
}
