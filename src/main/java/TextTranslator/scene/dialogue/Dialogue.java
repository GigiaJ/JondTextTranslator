package TextTranslator.scene.dialogue;

import TextTranslator.scene.command.TargetSelector;
import TextTranslator.scene.command.TellRaw;
import TextTranslator.scene.command.TellRawText;
import TextTranslator.utils.Language;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

import static TextTranslator.utils.Library.ExtraInfo;

/**
 * A class to store tellraw's with extra data associated with them
 * @author Jaggar
 */
public class Dialogue extends TellRaw {

    /**
     * Generates a new command object with all of the fields in this class as parameters.
     */
    public Dialogue(TellRawText tellRawText, TargetSelector mainTargetSelector, int row, String originalLine) {
        super(tellRawText, mainTargetSelector, row, originalLine);
    }

    /**
     * Converts the dialogue to its minecraft command form
     *
     * @return the command form of this dialogue
     */
    @ExtraInfo(UnitTested = true)
    public String toCommandForm() {
        return DialogueMaker.generateCommandStart(this.getMainTargetSelector())
                + DialogueMaker.wrapWithBlockChars(
                        DialogueMaker.getEntries(new TellRawText(this.getSpeaker(), this.getText(), this.getColor()),
                                this.getMainTargetSelector()));
    }
}
