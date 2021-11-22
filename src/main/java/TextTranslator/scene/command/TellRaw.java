package TextTranslator.scene.command;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * A class representing a minecraft command
 */
@Slf4j
public class TellRaw extends Command {

    /**
     * Makes a new tellraw object with the values of the parent class as its own
     * @param tellRawText            the text associated with the tellraw
     * @param command                the object that this one will get its values from
     */
    public TellRaw(Command command,  TellRawText tellRawText) {
        super(command.getMainTargetSelector(), command.getRow(), command.getOriginalLine(), CommandType.TELLRAW);
        this.args = new String[]{tellRawText.getSpeaker(), tellRawText.getText(), tellRawText.getColor()};
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
        this.args = new String[]{tellRawText.getSpeaker(), tellRawText.getText(), tellRawText.getColor()};
    }

    /**
     * Generates a new dialogue object with most of the fields in this class as parameters.
     *
     * @param tellRawText            the text associated with the tellraw
     * @param mainTargetSelector     the target selector for this tell raw
     * @param row                    the row from the source of this command
     */
    public TellRaw(TellRawText tellRawText, TargetSelector mainTargetSelector, int row) {
        super(mainTargetSelector, row, null, CommandType.TELLRAW);
        this.args = new String[]{tellRawText.getSpeaker(), tellRawText.getText(), tellRawText.getColor()};
    }

    /**
     * Gets the speaker of the tellraw command by checking the generic args variable
     */
    public String getSpeaker() {
        return args[0];
    }

    /**
     * Sets the speaker of the tellraw command by adjusting the generic args variable
     */
    public void setSpeaker(String s) {
        this.args[0] = s;
    }

    /**
     * Gets the speaker of the tellraw command by checking the generic args variable
     */
    public String getText() {
        return args[1];
    }

    /**
     * Sets the text of the tellraw command by adjusting the generic args variable
     */
    public void setText(String s) {
        this.args[1] = s;
    }

    /**
     * Gets the speaker of the tellraw command by checking the generic args variable
     */
    public String getColor() {
        return args[2];
    }

    /**
     * Sets the color of the tellraw command by adjusting the generic args variable
     */
    public void setColor(String s) {
        this.args[2] = s;
    }
}
